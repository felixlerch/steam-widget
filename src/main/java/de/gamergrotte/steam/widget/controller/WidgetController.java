package de.gamergrotte.steam.widget.controller;

import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.lukaspradel.steamapi.data.json.playersummaries.Player;
import de.gamergrotte.steam.widget.service.SteamWidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WidgetController {

    @Autowired
    private SteamWidgetService steamWidgetService;

    @GetMapping("/widget")
    public String widget(@RequestParam(name = "id") String id, Model model) throws SteamApiException {
        Player player = steamWidgetService.getUserBySteamId(id);
        model.addAttribute("profilelink", player.getProfileurl());
        model.addAttribute("profilepic", player.getAvatarmedium());
        model.addAttribute("name", player.getPersonaname());
        model.addAttribute("game", player.getAdditionalProperties().getOrDefault("gameextrainfo", ""));
        return "widget";
    }

}
