package nl.mixa.auc.db;

import nl.mixa.auc.model.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item getByBlizzardItemIdEquals(Long blizzardId);

}
