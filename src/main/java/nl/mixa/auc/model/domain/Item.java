package nl.mixa.auc.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.mixa.auc.db.BlizzardItem;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, nullable = false)
    private Long blizzardItemId;
    @UpdateTimestamp
    private Instant updated;
    @CreationTimestamp
    private Instant created;

    public static Item fromBlizzardItem(BlizzardItem blizzardItem) {
        return new Item(null, blizzardItem.getItemName(), blizzardItem.getBlizzardId(), null, null);
    }

}
