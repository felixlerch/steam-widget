package de.gamergrotte.steam.widget.controller;

import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.lukaspradel.steamapi.data.json.playersummaries.Player;
import de.gamergrotte.steam.widget.service.SteamWidgetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

/**
 * Controller for handling requests related to Steam widgets.
 * Provides endpoints for generating and retrieving Steam user widgets in both HTML and image formats.
 */
@Controller
public class WidgetController {

    @Autowired
    private SteamWidgetService steamWidgetService;

    /**
     * Handles requests to generate an HTML widget for a Steam user.
     * Retrieves user data from Steam and populates the model for rendering the widget view.
     *
     * @param id      The Steam ID of the user for whom the widget is being generated.
     * @param purpose Optional parameter indicating the purpose of the widget request.
     * @param model   The Spring Model object used for passing data to the view.
     * @param request The HttpServletRequest object, used here to get the client's IP address.
     * @return The name of the HTML view to be rendered as the widget.
     * @throws SteamApiException If there is an issue with accessing the Steam Web API.
     */
    @GetMapping("/widget/html")
    public String widget(@RequestParam(name = "id") String id, @RequestParam(name = "purpose", required = false, defaultValue = "General") String purpose, Model model, HttpServletRequest request) throws SteamApiException {
        Player player = steamWidgetService.getUserBySteamId(id, purpose, request.getRemoteAddr());
        model.addAttribute("profilelink", player.getProfileurl());
        model.addAttribute("profilepic", player.getAvatarmedium());
        model.addAttribute("name", player.getPersonaname());
        model.addAttribute("game", player.getAdditionalProperties().getOrDefault("gameextrainfo", ""));
        model.addAttribute("statecolor", (player.getAdditionalProperties().getOrDefault("gameextrainfo", "") != "" ? "green" : player.getPersonastate() == 3 ? "yellow" : player.getPersonastate() == 2 ? "red" : player.getPersonastate() == 1 ? "#00b7ff" : "#898989"));
        return "widget";
    }

    /**
     * Handles requests to generate an image widget for a Steam user.
     * Generates a BufferedImage for the user and returns it as a byte array in PNG format.
     *
     * @param id      The Steam ID of the user for whom the widget image is being generated.
     * @param purpose Optional parameter indicating the purpose of the widget request.
     * @param request The HttpServletRequest object, used here to get the client's IP address.
     * @return A byte array containing the PNG data of the generated widget image.
     * @throws SteamApiException If there is an issue with accessing the Steam Web API.
     * @throws IOException       If there is an error during writing the image to the byte array output stream.
     */
    @GetMapping(value = "/widget/img", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getWidgetImage(@RequestParam(name = "id") String id, @RequestParam(name = "purpose", required = false, defaultValue = "General") String purpose, HttpServletRequest request, HttpServletResponse response) throws SteamApiException, IOException {
        BufferedImage image = steamWidgetService.generateWidgetImage(id, purpose, request.getRemoteAddr());
        ByteArrayOutputStream imageByteStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", imageByteStream);
        byte[] imageBytes = imageByteStream.toByteArray();

        response.addHeader("Cache-Control", "max-age=60, must-revalidate");

        return imageBytes;
    }

}