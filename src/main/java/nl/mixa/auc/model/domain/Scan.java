package nl.mixa.auc.model.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Scan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant scanDate;
    @OneToMany(mappedBy = "scan")
    @JsonManagedReference
    private List<ItemPriceEntry> itemPrices;
    @UpdateTimestamp
    private Instant updated;
    @CreationTimestamp
    private Instant created;

}
