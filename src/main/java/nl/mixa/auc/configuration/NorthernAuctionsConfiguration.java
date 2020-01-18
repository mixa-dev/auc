package nl.mixa.auc.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ConfigurationProperties("northern-auctions")
public class NorthernAuctionsConfiguration {

    @NestedConfigurationProperty
    private RealmConfiguration realms;

}
