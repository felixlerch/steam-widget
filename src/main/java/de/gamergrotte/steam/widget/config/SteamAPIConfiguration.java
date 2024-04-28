package de.gamergrotte.steam.widget.config;

import de.gamergrotte.steam.widget.component.SteamWebAPI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "steam.api")
@ComponentScan(basePackageClasses = SteamWebAPI.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SteamAPIConfiguration {

    private String key;

}
