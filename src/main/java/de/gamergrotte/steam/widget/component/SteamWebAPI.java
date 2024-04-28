package de.gamergrotte.steam.widget.component;

import com.lukaspradel.steamapi.webapi.client.SteamWebApiClient;
import de.gamergrotte.steam.widget.config.SteamAPIConfiguration;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class SteamWebAPI {

    private final SteamAPIConfiguration configuration;

    @Getter
    private SteamWebApiClient client;

    public SteamWebAPI(SteamAPIConfiguration configuration) {
        this.configuration = configuration;
        init();
    }

    private void init() {
        client = new SteamWebApiClient.SteamWebApiClientBuilder(configuration.getKey()).build();
    }

}
