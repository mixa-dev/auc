package nl.mixa.auc.model.scan;

import lombok.Builder;
import lombok.Data;
import nl.mixa.auc.util.NumberUtils;

@Data
@Builder
public class ItemScanData {

    private String itemName;
    private Long itemId;
    private Long bidPrice;
    private Long buyOut;
    private int stacks;
    private long scanDate;
    private String realm;
    private String uniqueScanId;

    public Long getPricePerItem() {
        return buyOut / stacks;
    }

    public String getPricePerItemAsGold() {
        return NumberUtils.toGold(buyOut / stacks);
    }



}
