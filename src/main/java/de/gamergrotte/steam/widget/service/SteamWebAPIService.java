package de.gamergrotte.steam.widget.service;

import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.lukaspradel.steamapi.data.json.playersummaries.GetPlayerSummaries;
import com.lukaspradel.steamapi.data.json.playersummaries.Player;
import com.lukaspradel.steamapi.webapi.request.GetPlayerSummariesRequest;
import de.gamergrotte.steam.widget.component.SteamWebAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SteamWebAPIService {

    @Autowired
    private SteamWebAPI api;

    public Player getUserBySteamId(String steamId) throws SteamApiException {
        GetPlayerSummariesRequest request = new GetPlayerSummariesRequest.GetPlayerSummariesRequestBuilder(List.of(steamId)).buildRequest();
        GetPlayerSummaries playerSummaries = api.getClient().<GetPlayerSummaries> processRequest(request);
        List<Player> players = playerSummaries.getResponse().getPlayers();
        if (!players.isEmpty()) {
            return players.getFirst();
        }
        return new Player();
    }

}
