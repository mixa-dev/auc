package nl.mixa.auc.exposed.api.input;

import lombok.AllArgsConstructor;
import nl.mixa.auc.service.input.InputService;
import nl.mixa.auc.service.input.InputResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/input")
public class InputController {

    private InputService inputService;

    @PostMapping("/auc-scan-data")
    public @ResponseBody InputResult inputScanData(@RequestParam("auc-scan-data") MultipartFile file) {
        return inputService.read(file);
    }


}
