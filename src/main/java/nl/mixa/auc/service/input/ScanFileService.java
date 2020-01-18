package nl.mixa.auc.service.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import nl.mixa.auc.model.auctioneer.AucScanData;
import nl.mixa.auc.parsing.LuaParserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScanFileService {

    private static final String DATA_KEY_FOR_SCAN_DATA = "AucScanData";
    private static final String SUPPORTED_SCAN_DATA_VERSION = "1.4";
    private ObjectMapper objectMapper;
    private LuaParserService luaParserService;

    public AucScanData convertToAucScanData(MultipartFile file) {
        AucScanData scanData;
        try (var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String input = reader.lines().collect(Collectors.joining("\n"));
            Map<String, Object> data = luaParserService.parse(input);
            scanData = objectMapper.convertValue(data.get(DATA_KEY_FOR_SCAN_DATA), AucScanData.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throwIfUnsupportedVersion(scanData);
        return scanData;
    }

    private void throwIfUnsupportedVersion(AucScanData scanData) {
        if (!SUPPORTED_SCAN_DATA_VERSION.equals(scanData.getVersion())) {
            throw new IllegalArgumentException("UNSUPPORTED_SCAN_DATA_VERSION");
        }
    }

}
