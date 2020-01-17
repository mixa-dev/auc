package nl.mixa.auc.model.scan;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ScanData {

    private Instant scanDate;
    private List<ItemScanData> itemScanData;

}
