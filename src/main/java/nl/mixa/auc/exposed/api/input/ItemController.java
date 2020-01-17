package nl.mixa.auc.exposed.api.input;

import lombok.AllArgsConstructor;
import nl.mixa.auc.db.ItemScanDAO;
import nl.mixa.auc.model.domain.ItemPriceEntry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
