package nl.mixa.auc.db;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class BlizzardItem {

    private String itemName;
    private Long blizzardId;

}
