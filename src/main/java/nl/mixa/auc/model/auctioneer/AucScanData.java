package nl.mixa.auc.model.auctioneer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.util.LinkedCaseInsensitiveMap;

@Data
public class AucScanData {

    @JsonProperty("Version")
    private String version;
    private LinkedCaseInsensitiveMap<AucScans> scans;

}
