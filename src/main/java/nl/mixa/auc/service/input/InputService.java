package nl.mixa.auc.service.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import nl.mixa.auc.db.ItemScanDAO;
import nl.mixa.auc.model.auctioneer.AucScanData;
import nl.mixa.auc.model.scan.ItemScanData;
import nl.mixa.auc.parsing.IntermediateScanDataDeserializer;
import nl.mixa.auc.parsing.LuaParserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InputService {

    private LuaParserService luaParserService;
    private IntermediateScanDataDeserializer intermediateScanDataDeserializer;
    private ObjectMapper objectMapper;
    private ItemScanDAO itemScanDAO;

    public InputResult read(MultipartFile file) {
        try (var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String input = reader.lines().collect(Collectors.joining("\n"));
            Map<String, Object> data = luaParserService.parse(input);
            AucScanData scanData = objectMapper.convertValue(data.get("AucScanData"), AucScanData.class);
            if (!scanData.getVersion().equals("1.4")) {
                throw new IllegalArgumentException("UNSUPPORTED_SCAN_DATA_VERSION");
            }
            //todo inject scan unique id into item scan data
            List<ItemScanData> scannedItems = scanData.getScans().entrySet().stream().flatMap(it -> intermediateScanDataDeserializer.deserializeString(it.getValue().getRopes(), it.getKey(), scanData).stream()).collect(Collectors.toList());
            itemScanDAO.addAll(scannedItems);
            return new InputResult("OK", scannedItems.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
