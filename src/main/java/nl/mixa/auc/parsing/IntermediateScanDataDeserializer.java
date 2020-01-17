package nl.mixa.auc.parsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import nl.mixa.auc.model.auctioneer.AucScanData;
import nl.mixa.auc.model.auctioneer.AucScanStats;
import nl.mixa.auc.model.auctioneer.AucScans;
import nl.mixa.auc.model.auctioneer.AucSingleScanStats;
import nl.mixa.auc.model.intermediate.IntermediateScanItem;
import nl.mixa.auc.model.scan.ItemScanData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static nl.mixa.auc.model.intermediate.IntermediateScanItem.ROPE_MAPPING;

@Service
@AllArgsConstructor
public class IntermediateScanDataDeserializer {

    private static final Pattern EXTRACTION_PATTERN = Pattern.compile("return \\{(\\{.*}),}");
    private static final Logger logger = LoggerFactory.getLogger(IntermediateScanDataDeserializer.class);

    private ObjectMapper objectMapper;


    public List<ItemScanData> deserializeString(List<String> data, String realm, AucScanData aucScanData) {
        return data.stream().flatMap(it -> deserializeString(it, realm, aucScanData).stream()).collect(Collectors.toList());
    }

    public List<ItemScanData> deserializeString(String data, String realm, AucScanData aucScanData) {
        String cleanedData = cleanInputdata(data);
        String jsonArray = "[" + cleanedData + "]";
        try {
            List<List<Object>> list = objectMapper.readValue(jsonArray, objectMapper.getTypeFactory().constructCollectionLikeType(ArrayList.class, ArrayList.class));
            return list.stream().map(scanData -> convertObjectListIntoScanData(aucScanData, scanData, realm)).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private ItemScanData convertObjectListIntoScanData(AucScanData aucScanData, List<Object> scanData, String realm) {
        if (scanData.size() != ROPE_MAPPING.size()) {
            logger.warn("ILLEGAL_ROPE: " + String.join(", " + scanData));
            throw new IllegalArgumentException("ILLEGAL_ROPE_DATA_SIZE_" + scanData.size());
        }
        IntermediateScanItem intermediateScanItem = new IntermediateScanItem();
        for (int i = 0; i < ROPE_MAPPING.size(); i++) {
            ROPE_MAPPING.get(i).i(i).item(intermediateScanItem).data(scanData).build();
        }
        return ItemScanData.builder()
                .bidPrice(intermediateScanItem.getBid())
                .buyOut(intermediateScanItem.getBuyOut())
                .itemId(intermediateScanItem.getItemId())
                .itemName(intermediateScanItem.getItemName())
                .stacks(intermediateScanItem.getStackSize())
                .realm(realm)
                .scanDate(intermediateScanItem.getSeenDate())
                .uniqueScanId(getUniqueId(aucScanData))
                .build();
    }

    private String getUniqueId(AucScanData aucScanData) {
        List<AucScanStats> stats = aucScanData.getScans().values().stream().map(AucScans::getScanStats).collect(Collectors.toList());
        return stats.stream().map(AucScanStats::getSingleScan).flatMap(it -> it.values().stream())
                .mapToLong(AucSingleScanStats::hashCode)
                .mapToObj(Long::toString)
                .collect(Collectors.joining("-"));
    }

    private String cleanInputdata(String data) {
        Matcher matcher = EXTRACTION_PATTERN.matcher(data);
        if (matcher.find()) {
            String cleaned = matcher.group(1);
            String cleanedNil = cleaned.replace("nil,", "null,");
            return cleanedNil.replace(",}", "}").replace("{","[").replace("}","]");
        }
        throw new IllegalArgumentException("ILLEGAL_ROPE_DATA_FORMAT");
    }

}
