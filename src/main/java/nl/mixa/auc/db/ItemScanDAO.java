package nl.mixa.auc.db;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.mixa.auc.model.auctioneer.AucScanData;
import nl.mixa.auc.model.domain.Item;
import nl.mixa.auc.model.domain.ItemPriceEntry;
import nl.mixa.auc.model.domain.Realm;
import nl.mixa.auc.model.domain.Scan;
import nl.mixa.auc.model.scan.ItemScanData;
import org.apache.tomcat.util.collections.CaseInsensitiveKeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class ItemScanDAO {

    private ItemPriceEntryRepository itemPriceEntryRepository;
    private ItemRepository itemRepository;
    private RealmRepository realmRepository;
    private ScanRepository scanRepository;
    private static final Logger logger = LoggerFactory.getLogger(ItemScanDAO.class);

    public List<ItemPriceEntry> getItemData(String itemName) {
        return this.itemPriceEntryRepository.findAll(Example.of(new ItemPriceEntry(null, new Item(null, itemName, null, null, null), null, null, null, null, null, null)));
    }

    public List<ItemPriceEntry> getCheapestLatestBuyoutForItemForAllRealms(String itemName) {
        Optional<Item> itemOpt = this.itemRepository.findOne(Example.of(new Item(null, itemName, null, null, null)));
        if (itemOpt.isEmpty()) {
            throw new IllegalArgumentException("NO_ITEM_BY_NAME");
        }
        Item item = itemOpt.get();
        List<Realm> realms = realmRepository.findAll();
        return realms.stream().map(it -> this.itemPriceEntryRepository.find(item.getName(), it.getName(), PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "price"))))
                .flatMap(it -> it.stream())
                .collect(Collectors.toList());
    }

    @Transactional
    public void addAll(List<ItemScanData> scannedItems) {
        Set<String> realmNames = scannedItems.stream().map(ItemScanData::getRealm).collect(Collectors.toSet());
        realmNames.forEach(this::createRealmIfNotExists);
        Set<BlizzardItem> uniqueItems = scannedItems.stream().map(it -> new BlizzardItem(it.getItemName(), it.getItemId())).collect(Collectors.toSet());
        Scan scan = this.scanRepository.save(new Scan());
        uniqueItems.forEach(this::createItemIfNotExists);
        AtomicLong c1 = new AtomicLong();
        Map<String, Realm> realms = this.realmRepository.findAll().stream().collect(Collectors.toMap(Realm::getName, it -> it));
        Map<Long, Item> items = this.itemRepository.findAll().stream().collect(Collectors.toMap(Item::getBlizzardItemId, it -> it));
        List<ItemPriceEntry> all = scannedItems.stream().map(it -> saveOneItem(it, scan, realms, items))
                .peek(it -> logger.info("Creating item: " + c1.getAndAdd(1) + " of " + scannedItems.size()))
                .collect(Collectors.toList());
        AtomicLong c = new AtomicLong();
        batches(all, 2000).peek(it -> logger.info("Saving items: " + c.getAndAdd(2000) + " of " + all.size())).forEach(this.itemPriceEntryRepository::saveAll);
    }

    private ItemPriceEntry saveOneItem(ItemScanData scanData, Scan scan, Map<String, Realm> realms, Map<Long, Item> items) {
        Realm realm = realms.get(scanData.getRealm());
        Item item = items.get(scanData.getItemId());
        return new ItemPriceEntry(null, item, realm, scan, scanData.getPricePerItem(), Instant.ofEpochSecond(scanData.getScanDate()), null, null);
    }

    private static <T> Stream<List<T>> batches(List<T> source, int length) {
        if (length <= 0)
            throw new IllegalArgumentException("length = " + length);
        int size = source.size();
        if (size <= 0)
            return Stream.empty();
        int fullChunks = (size - 1) / length;
        return IntStream.range(0, fullChunks + 1).mapToObj(
                n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length));
    }

    private void createItemIfNotExists(BlizzardItem blizzardItem) {
        Item item = this.itemRepository.getByBlizzardItemIdEquals(blizzardItem.blizzardId);
        if (item == null) {
            try {
                this.itemRepository.save(new Item(null, blizzardItem.itemName, blizzardItem.blizzardId, null, null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createRealmIfNotExists(String realmName) {
        Realm realm = this.realmRepository.getByNameEquals(realmName);
        if (realm == null) {
            this.realmRepository.save(new Realm(null, realmName, null, null));
        }
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    private static class BlizzardItem {

        private String itemName;
        private Long blizzardId;

    }


}
