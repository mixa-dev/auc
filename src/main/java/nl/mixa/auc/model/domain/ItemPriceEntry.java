package nl.mixa.auc.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.mixa.auc.util.NumberUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPriceEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Item item;
    @ManyToOne(optional = false)
    private Realm realm;
    @ManyToOne(optional = false)
    @JsonBackReference
    private Scan scan;
    private Long price;
    private Instant readDate;
    @UpdateTimestamp
    private Instant updated;
    @CreationTimestamp
    private Instant created;

    public String getPriceAsGold() {
        return NumberUtils.toGold(this.price);
    }
}
