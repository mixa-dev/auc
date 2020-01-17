package nl.mixa.auc.service.input;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputResult {

    private String result;
    private Integer itemsRead;

}
