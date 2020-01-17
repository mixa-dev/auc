package nl.mixa.auc.db;

import nl.mixa.auc.model.domain.Realm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealmRepository extends JpaRepository<Realm, Long> {

    Realm getByNameEquals(String name);
}
