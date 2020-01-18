package nl.mixa.auc.db;

import nl.mixa.auc.model.domain.Realm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealmRepository extends JpaRepository<Realm, Long> {

    Realm getByNameEquals(String name);

    @Query("SELECT r from Realm r where r.name in :names")
    List<Realm> findAllByNameEquals(List<String> names);

    default Realm createByNameIfNotExist(String name) {
        Realm byName = getByNameEquals(name);
        if (byName == null) {
            return save(new Realm(null, name, null, null));
        }
        return byName;
    }

}
