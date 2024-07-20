package de.gamergrotte.steam.widget.controller;

import de.gamergrotte.steam.widget.component.SteamOpenID;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class SteamOpenIDController {

    @Autowired
    private SteamOpenID steamOpenID;

    @GetMapping("/steam/login")
    public void loginRedirect(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", steamOpenID.login("https://steam-widget.com/steam/login/callback"));
        httpServletResponse.setStatus(302);
    }

    @GetMapping("/steam/login/callback")
    public void loginRedirect(@RequestParam Map<String,String> allParams, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        String steamId64 = steamOpenID.verify(request.getRequestURL().toString(), request.getParameterMap());

        httpServletResponse.setHeader("Location", steamOpenID.login("https://steam-widget.com/?steamId=" + steamId64));
        httpServletResponse.setStatus(302);
    }

}
