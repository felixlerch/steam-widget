package de.gamergrotte.steam.widget.controller;

import com.lukaspradel.steamapi.core.exception.SteamApiException;
import de.gamergrotte.steam.widget.entity.Profile;
import de.gamergrotte.steam.widget.service.SteamWidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MetricController {

    @Autowired
    private SteamWidgetService steamWidgetService;

    @GetMapping(value = "/metric", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<Profile> getProfile(@RequestParam(name = "id") String id) throws SteamApiException {
        String resolvedId = steamWidgetService.resolveSteamId(id);
        Profile profile = steamWidgetService.getProfile(resolvedId);
        return new ResponseEntity<>(profile, profile.getSteam64id() == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping(value = "/metric")
    public @ResponseBody ResponseEntity<Long> getProfileHit(@RequestParam(name = "id") String id) throws SteamApiException {
        String resolvedId = steamWidgetService.resolveSteamId(id);
        long hits = steamWidgetService.getProfileHitByProfile(resolvedId);
        return new ResponseEntity<>(hits, hits == 0 ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping(value = "/metric/hits")
    public @ResponseBody ResponseEntity<Long> getHits(@RequestParam(name = "id") String id, @RequestParam(name = "purpose", required = false, defaultValue = "General") String purpose) throws SteamApiException {
        String resolvedId = steamWidgetService.resolveSteamId(id);
        long hits = steamWidgetService.getHitByProfileAndPurpose(resolvedId, purpose);
        return new ResponseEntity<>(hits, hits == 0 ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }


}
