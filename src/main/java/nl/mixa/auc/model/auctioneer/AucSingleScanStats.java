package nl.mixa.auc.model.auctioneer;

import lombok.Data;

import java.util.Map;

@Data
public class AucSingleScanStats {
    private Integer filteredCount;
    private Double scanStoreTime;
    private Integer paused;
    private Integer oldCount;
    private Integer newCount;
    private String serverKey;
    private Integer expiredDeleteCount;
    private Integer matchedCount;
    private Double started;
    private String source;
    private Boolean wasGetAll;
    private Double elapsed;
    private Boolean wasIncomplete;
    private Integer earlyDeleteCount;
    private Integer currentCount;
    private Integer missedCount;
    private Integer storeTime;
    private Double ended;
    private String startTime;
    private Integer scanCount;
    private Integer endTime;
    private Integer sameCount;
    private Map<String, Object> query;
    private Integer updateCount;
}
