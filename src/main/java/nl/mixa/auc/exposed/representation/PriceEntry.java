package nl.mixa.auc.exposed.representation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.mixa.auc.model.domain.Item;
import nl.mixa.auc.util.NumberUtils;

@AllArgsConstructor
@Getter
public class PriceEntry {

    private Item item;
    private Long avgPrice;
    private Long maxPrice;
    private Long minPrice;

    public String getAverageGoldPrice() {
        return NumberUtils.toGold(this.avgPrice);
    }

    public String getMinGoldPrice() {
        return NumberUtils.toGold(this.minPrice);
    }

    public String getMaxGoldPrice() {
        return NumberUtils.toGold(this.maxPrice);
    }

}
