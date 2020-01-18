package nl.mixa.auc.db;

import nl.mixa.auc.model.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item getByBlizzardItemIdEquals(Long blizzardId);

    default Item createItemIfNotExistByBlizzardId(Item item) {
        Item itemByBlizzardId = getByBlizzardItemIdEquals(item.getBlizzardItemId());
        if (itemByBlizzardId == null) {
            return save(item);
        }
        return itemByBlizzardId;
    }

    default Item createItemIfNotExistByBlizzardId(BlizzardItem blizzardItem) {
        return createItemIfNotExistByBlizzardId(Item.fromBlizzardItem(blizzardItem));
    }

    Item getByNameEquals(String name);
}
