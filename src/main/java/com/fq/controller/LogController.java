/*
Date: 04/10,2019, 21:23
*/
package com.fq.controller;

import com.fq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class LogController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/relogin", method = RequestMethod.GET)
    public String regloginPage() {
        return "login";
    }

    @RequestMapping(value = "/login/", method = {RequestMethod.POST})
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam("rememberme") boolean rememberme) {
        return "";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "next", required = false) String next,
                      @RequestParam("rememberme") boolean rememberme) {
        Map<String, Object> map = userService.register(username, password);
        model.addAttribute("msg", map);

        return "login";

    }

}
