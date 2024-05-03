package de.gamergrotte.steam.widget.service;

import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.lukaspradel.steamapi.data.json.playersummaries.GetPlayerSummaries;
import com.lukaspradel.steamapi.data.json.playersummaries.Player;
import com.lukaspradel.steamapi.data.json.resolvevanityurl.ResolveVanityURL;
import com.lukaspradel.steamapi.webapi.request.GetPlayerSummariesRequest;
import com.lukaspradel.steamapi.webapi.request.ResolveVanityUrlRequest;
import de.gamergrotte.steam.widget.component.SteamWebAPI;
import de.gamergrotte.steam.widget.entity.Profile;
import de.gamergrotte.steam.widget.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class SteamWidgetService {

    @Autowired
    private SteamWebAPI api;

    @Autowired
    private ProfileRepository repository;

    public Player getUserBySteamId(String steamId) throws SteamApiException {
        String id = steamId;
        if (!(id.matches("[0-9]+")) && id.length() != 17) {
            if (id.contains("https://steamcommunity.com/id/")) {
                id = id.replaceAll("https://steamcommunity.com/id/", "").replaceAll("/", "");
            }
            ResolveVanityUrlRequest request = new ResolveVanityUrlRequest.ResolveVanityUrlRequestBuilder(id).buildRequest();
            ResolveVanityURL vanityURL = api.getClient().processRequest(request);
            id = vanityURL.getResponse().getSteamid();
        }
        GetPlayerSummariesRequest request = new GetPlayerSummariesRequest.GetPlayerSummariesRequestBuilder(List.of(id == null ? steamId : id)).buildRequest();
        GetPlayerSummaries playerSummaries = api.getClient().<GetPlayerSummaries> processRequest(request);
        List<Player> players = playerSummaries.getResponse().getPlayers();
        if (!players.isEmpty()) {
            addHitToProfile(id, players.getFirst().getPersonaname());
            return players.getFirst();
        }
        return new Player();
    }

    public BufferedImage generateWidgetImage(String steamId) throws SteamApiException {
        Player player = getUserBySteamId(steamId);

        BufferedImage bufferedImage = new BufferedImage(3500, 750, BufferedImage.TYPE_INT_ARGB);
        this.drawBaseWidget(bufferedImage);
        if (player.getSteamid() != null) {
            drawProfileImage(bufferedImage, player);
            drawUserInformation(bufferedImage, player);
        }

        return bufferedImage;
    }

    private void drawUserInformation(BufferedImage image, Player player) {
        if (!player.getAdditionalProperties().getOrDefault("gameextrainfo", "").toString().isEmpty()) {
            this.drawString(image, player.getPersonaname(), "ARIAL", Font.BOLD, "#ffffff", 200, 725, 350);
            this.drawString(image, player.getAdditionalProperties().getOrDefault("gameextrainfo", "").toString(), "ARIAL", Font.PLAIN, "#c7d5e0", 150, 725, 550);
        } else {
            this.drawString(image, player.getPersonaname(), "ARIAL", Font.BOLD, "#ffffff", 200, 725, 450);
        }
    }

    private void drawString(BufferedImage image, String display, String font, int style, String hexColor, Integer size, Integer x, Integer y) {
        Graphics2D g = (Graphics2D) image.getGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setFont(new Font(font, style, size));
        g.setColor(Color.decode(hexColor));
        g.drawString(display, x, y);

        g.dispose();
    }

    private void drawProfileImage(BufferedImage image, Player player) {
        BufferedImage profileImage = this.loadImageFromURL(player.getAvatarfull());

        Graphics2D g = image.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        BufferedImage roundedProfileImage = this.makeRoundedCorner(profileImage, 500);

        g.drawImage(roundedProfileImage, 125, 125, 500, 500, null);

        g.dispose();
    }

    public BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }

    private void drawBaseWidget(BufferedImage image) {
        Graphics2D g = image.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0,0, image.getWidth(), image.getHeight());
        g.setComposite(AlphaComposite.Src);
        g.setColor(Color.decode("#171a21"));
        g.fillRoundRect(0,0, image.getWidth(), image.getHeight(), 100, 100);
        g.setColor(Color.decode("#1b2838"));
        g.drawRoundRect(0,0, image.getWidth(), image.getHeight(), 100, 100);

        BufferedImage logo = this.loadImageFromResources("/static/img/steam_logo.png");
        g.drawImage(logo, image.getWidth() - 500, 100, 400, 120, Color.decode("#171a21"), null);

        g.dispose();
    }

    private BufferedImage loadImageFromResources(String path) {
        if (path != null && path.length() > 5) {
            try {
                return ImageIO.read(getClass().getResource(path));
            } catch (Exception ignored) {

            }
        }

        return new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);
    }

    private BufferedImage loadImageFromURL(String url) {
        if (url != null && url.length() > 5) {
            try {
                return ImageIO.read(new URI(url).toURL());
            } catch (Exception ignored) {

            }
        }

        return new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);
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
