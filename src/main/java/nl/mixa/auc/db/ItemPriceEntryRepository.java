package nl.mixa.auc.db;

import nl.mixa.auc.model.domain.Item;
import nl.mixa.auc.model.domain.ItemPriceEntry;
import nl.mixa.auc.model.domain.Realm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.Instant;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "itemsPrices", path = "itemPrice")
public interface ItemPriceEntryRepository extends JpaRepository<ItemPriceEntry, Long> {

    @Query(value = "SELECT i from ItemPriceEntry i where i.item.name = :itemName and i.realm.name = :realmName")
    Page<ItemPriceEntry> find(@Param("itemName") String itemName, @Param("realmName") String realmName, Pageable pageable);

    @Query("SELECT i.price from ItemPriceEntry i where i.item = :item and i.realm = :realm and (i.readDate < :seenDateEnd and i.readDate > :seenDateStart)")
    List<Long> findPriceAtDateForItemAndRealm(Item item, Realm realm, Instant seenDateStart, Instant seenDateEnd);

}
