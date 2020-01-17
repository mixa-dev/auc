package nl.mixa.auc.model.auctioneer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
public class AucScans {

    private String image;
    private List<String> ropes;
    @JsonProperty("scanstats")
    private AucScanStats scanStats;

}
