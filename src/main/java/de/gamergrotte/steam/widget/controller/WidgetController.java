package de.gamergrotte.steam.widget.controller;

import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.lukaspradel.steamapi.data.json.playersummaries.Player;
import de.gamergrotte.steam.widget.service.SteamWidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class WidgetController {

    @Autowired
    private SteamWidgetService steamWidgetService;

    @GetMapping("/widget/html")
    public String widget(@RequestParam(name = "id") String id, Model model) throws SteamApiException {
        Player player = steamWidgetService.getUserBySteamId(id);
        model.addAttribute("profilelink", player.getProfileurl());
        model.addAttribute("profilepic", player.getAvatarmedium());
        model.addAttribute("name", player.getPersonaname());
        model.addAttribute("game", player.getAdditionalProperties().getOrDefault("gameextrainfo", ""));
        model.addAttribute("statecolor", (player.getAdditionalProperties().getOrDefault("gameextrainfo", "") != "" ? "green" : player.getPersonastate() == 3 ? "yellow" : player.getPersonastate() == 2 ? "red" : player.getPersonastate() == 1 ? "blue" : "grey"));
        return "widget";
    }

    @GetMapping(value = "/widget/img", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getWidgetImage(@RequestParam(name = "id") String id) throws SteamApiException, IOException {
        BufferedImage image = steamWidgetService.generateWidgetImage(id);
        ByteArrayOutputStream imageByteStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", imageByteStream);
        byte[] imageBytes = imageByteStream.toByteArray();
        return imageBytes;
    }

}
