/*
Date: 04/10,2019, 21:23
*/
package com.fq.controller;

import com.fq.model.SessionHolder;
import com.fq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LogController {
    @Autowired
    private UserService userService;

    @Autowired
    private SessionHolder sessionHolder;

    @RequestMapping(value = "/reglogin", method = RequestMethod.GET)
    public String regloginPage() {
        return "login";
    }

    @RequestMapping(value = "/login/", method = {RequestMethod.POST})
    public String login(Model model, @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam("rememberme") boolean rememberme,
                        HttpServletResponse response) {
        Map<String, Object> map = userService.login(username, password);

        if (map.containsKey("msg")) {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }

        if (map.containsKey("ticket")) {
            addCookie(rememberme, response, map.get("ticket").toString());
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "next", required = false) String next,
                      @RequestParam("rememberme") boolean rememberme,
                      HttpServletResponse response) {
        Map<String, Object> map = userService.register(username, password);

        if (map.containsKey("msg")) {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }

        if (map.containsKey("ticket")) {
            addCookie(rememberme, response, map.get("ticket").toString());
        }

        return "redirect:/";

    }

    private void addCookie(boolean rememberme, HttpServletResponse response, String ticket) {
        Cookie cookie = new Cookie("ticket", ticket);
        cookie.setPath("/");
        if (rememberme) {
            cookie.setMaxAge(7 * 24 * 3600);
        }
        response.addCookie(cookie);
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket) {
        userService.loginout(ticket);
        return "redirect:/";
    }
}
