package nl.mixa.auc.db;

import lombok.AllArgsConstructor;
import nl.mixa.auc.exposed.representation.PriceEntry;
import nl.mixa.auc.model.domain.Item;
import nl.mixa.auc.model.domain.ItemPriceEntry;
import nl.mixa.auc.model.domain.Realm;
import nl.mixa.auc.model.domain.Scan;
import nl.mixa.auc.model.scan.ItemScanData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static nl.mixa.auc.util.CollectionUtils.batchList;

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
                .flatMap(Streamable::stream)
                .collect(Collectors.toList());
    }

    public Map<Instant, Optional<PriceEntry>> getXDayPriceHistoryForItemAtRealm(int days, String itemName, String realmName) {
        Item item = this.itemRepository.getByNameEquals(itemName);
        if (item == null) {
            throw new IllegalArgumentException("NO_ITEM_BY_NAME");
        }
        Realm realm = this.realmRepository.getByNameEquals(realmName);
        if (realm == null) {
            throw new IllegalArgumentException("NO_REALM_BY_NAME");
        }
        Instant now = Instant.now().truncatedTo(ChronoUnit.DAYS);
        return IntStream.range(0, days + 1).mapToObj(it -> now.minus(it, ChronoUnit.DAYS))
                .map(it -> Pair.of(it, this.getItemPriceForRealmAtDay(it, item, realm)))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }

    private Optional<PriceEntry> getItemPriceForRealmAtDay(Instant day, Item item, Realm realm) {
        List<Long> averagePrice = this.itemPriceEntryRepository.findPriceAtDateForItemAndRealm(item, realm, day.truncatedTo(ChronoUnit.DAYS), day.truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS).minus(1, ChronoUnit.SECONDS));
        if (averagePrice.isEmpty()) {
            return Optional.empty();
        }
        long avg = averagePrice.stream().map(BigInteger::valueOf).reduce(BigInteger.ZERO, BigInteger::add).divide(BigInteger.valueOf(averagePrice.size())).longValue();
        long max = averagePrice.stream().mapToLong(value -> value).max().getAsLong();
        long min = averagePrice.stream().mapToLong(value -> value).min().getAsLong();
        return Optional.of(new PriceEntry(item, avg, max, min));
    }

    private ItemPriceEntry fromAveragePrice(Double averagePrice, Item item, Realm realm) {
        return new ItemPriceEntry(null, item, realm, null, averagePrice.longValue(), null, null, null);
    }

    @Transactional
    public void addAll(List<ItemScanData> scannedItems) {
        Map<String, Realm> realms = this.realmRepository.findAll().stream().collect(Collectors.toMap(Realm::getName, it -> it));
        scannedItems = scannedItems.stream().filter(it -> realms.containsKey(it.getRealm())).collect(Collectors.toList());
        Set<BlizzardItem> uniqueItems = scannedItems.stream().map(it -> new BlizzardItem(it.getItemName(), it.getItemId())).collect(Collectors.toSet());
        Scan scan = this.scanRepository.save(new Scan());
        uniqueItems.forEach(this.itemRepository::createItemIfNotExistByBlizzardId);
        Map<Long, Item> items = this.itemRepository.findAll().stream().collect(Collectors.toMap(Item::getBlizzardItemId, it -> it));
        List<ItemPriceEntry> all = scannedItems.stream().map(it -> createItemPriceEntry(it, scan, realms.get(it.getRealm()), items.get(it.getItemId()))).collect(Collectors.toList());
        batchList(all, 2000).forEach(this.itemPriceEntryRepository::saveAll);
    }

    private ItemPriceEntry createItemPriceEntry(ItemScanData scanData, Scan scan, Realm realm, Item item) {
        Instant instant = Instant.ofEpochSecond(scanData.getScanDate());
        return new ItemPriceEntry(null, item, realm, scan, scanData.getPricePerItem(), instant, null, null);
    }

}
