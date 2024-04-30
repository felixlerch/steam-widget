package de.gamergrotte.steam.widget.service;

import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.lukaspradel.steamapi.data.json.playersummaries.GetPlayerSummaries;
import com.lukaspradel.steamapi.data.json.playersummaries.Player;
import com.lukaspradel.steamapi.webapi.request.GetPlayerSummariesRequest;
import de.gamergrotte.steam.widget.component.SteamWebAPI;
import de.gamergrotte.steam.widget.entity.Profile;
import de.gamergrotte.steam.widget.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SteamWidgetService {

    @Autowired
    private SteamWebAPI api;

    @Autowired
    private ProfileRepository repository;

    public Player getUserBySteamId(String steamId) throws SteamApiException {
        GetPlayerSummariesRequest request = new GetPlayerSummariesRequest.GetPlayerSummariesRequestBuilder(List.of(steamId)).buildRequest();
        GetPlayerSummaries playerSummaries = api.getClient().<GetPlayerSummaries> processRequest(request);
        List<Player> players = playerSummaries.getResponse().getPlayers();
        if (!players.isEmpty()) {
            addHitToProfile(steamId, players.getFirst().getPersonaname());
            return players.getFirst();
        }
        return new Player();
    }

    public void addHitToProfile(String steamId, String name) {
        if (!repository.existsById(steamId)) {
            Profile profile = new Profile(steamId, name, 1L);
            repository.save(profile);
        } else {
            repository.incrementHits(steamId);
        }
    }

    public long getHitByProfile(String steamId) {
        Optional<Profile> profileOptional = repository.findById(steamId);
        if (profileOptional.isPresent()) {
            return profileOptional.get().getHits();
        } else {
            return 0;
        }
    }


    public Profile getProfile(String steamId) {
        Optional<Profile> profileOptional = repository.findById(steamId);
        return profileOptional.orElseGet(Profile::new);
    }
}
