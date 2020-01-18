package nl.mixa.auc.exposed.api.input;

import lombok.AllArgsConstructor;
import nl.mixa.auc.db.ItemScanDAO;
import nl.mixa.auc.exposed.representation.PriceEntry;
import nl.mixa.auc.model.domain.ItemPriceEntry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private ItemScanDAO itemScanDAO;

    @GetMapping()
    public List<ItemPriceEntry> getItems(
            @RequestParam(name = "name") String itemName
    ) {
        return this.itemScanDAO.getItemData(itemName);
    }

    @GetMapping("/latest-buyout-for-item")
    public List<ItemPriceEntry> getBuyoutForEachRealm(
            @RequestParam(name = "name") String itemName
    ) {
        return this.itemScanDAO.getCheapestLatestBuyoutForItemForAllRealms(itemName);
    }

    @GetMapping("/price-history")
    public Map<Instant, Optional<PriceEntry>> getBuyoutForEachRealm(
            @RequestParam(name = "name") String itemName,
            @RequestParam(name = "realm") String realmName,
            @RequestParam(name = "days") Integer days
    ) {
        return this.itemScanDAO.getXDayPriceHistoryForItemAtRealm(days, itemName, realmName);
    }



}
