package nl.mixa.auc.configuration;

import lombok.AllArgsConstructor;
import nl.mixa.auc.db.RealmRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@AllArgsConstructor
public class OnStartPostHook {

    private RealmRepository realmRepository;
    private NorthernAuctionsConfiguration configuration;

    @PostConstruct
    public void onPostConstruct() {
        List<String> supportedRealms = configuration.getRealms().getSupportedRealms();
        supportedRealms.forEach(it -> this.realmRepository.createByNameIfNotExist(it));
    }

}
