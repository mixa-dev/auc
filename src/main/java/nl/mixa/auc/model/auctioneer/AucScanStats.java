package nl.mixa.auc.model.auctioneer;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AucScanStats {

    private final Map<String, AucSingleScanStats> singleScan = new HashMap<>();

    @JsonProperty("LastFullScan")
    private Integer lastFullScan;
    @JsonProperty("ImageUpdated")
    private Integer imageUpdated;
    @JsonProperty("LastScan")
    private Integer lastScan;

    @JsonAnySetter()
    private void anySetter(String key, Object value){
        if (key.matches("\\d+")) {
            this.singleScan.put(key, new ObjectMapper().convertValue(value, AucSingleScanStats.class));
        }
    }

}
