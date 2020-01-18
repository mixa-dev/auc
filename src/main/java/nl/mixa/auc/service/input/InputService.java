package nl.mixa.auc.service.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import nl.mixa.auc.db.ItemScanDAO;
import nl.mixa.auc.model.auctioneer.AucScanData;
import nl.mixa.auc.model.auctioneer.AucScanStats;
import nl.mixa.auc.model.auctioneer.AucScans;
import nl.mixa.auc.model.auctioneer.AucSingleScanStats;
import nl.mixa.auc.model.scan.ItemScanData;
import nl.mixa.auc.parsing.IntermediateScanDataDeserializer;
import nl.mixa.auc.parsing.LuaParserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InputService {

    private IntermediateScanDataDeserializer intermediateScanDataDeserializer;
    private ItemScanDAO itemScanDAO;
    private ScanFileService scanFileService;

    public InputResult read(MultipartFile file) {
        AucScanData scanData = scanFileService.convertToAucScanData(file);
        List<ItemScanData> scannedItems = extractItemScans(scanData);
        itemScanDAO.addAll(scannedItems);
        return new InputResult("OK", scannedItems.size());
    }

    private List<ItemScanData> extractItemScans(AucScanData scanData) {
        String uniqueScanId = getUniqueScanId(scanData);
        return scanData.getScans().entrySet().stream()
                .map(it -> mapToItemScanData(it.getKey(), uniqueScanId, it.getValue().getRopes()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private String getUniqueScanId(AucScanData scanData) {
        List<AucScanStats> stats = scanData.getScans().values().stream().map(AucScans::getScanStats).collect(Collectors.toList());
        return stats.stream().map(AucScanStats::getSingleScan).flatMap(it -> it.values().stream())
                .mapToLong(AucSingleScanStats::hashCode)
                .mapToObj(Long::toString)
                .collect(Collectors.joining("-"));
    }

    private List<ItemScanData> mapToItemScanData(String realmName, String uniqueScanId, List<String> ropes) {
        return intermediateScanDataDeserializer.deserializeString(ropes, realmName, uniqueScanId);
    }
}
