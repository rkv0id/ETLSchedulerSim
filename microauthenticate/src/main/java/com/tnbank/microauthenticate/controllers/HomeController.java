package com.tnbank.microauthenticate.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:";
        }
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "agency", method = RequestMethod.GET)
    public String agency() {
        return "agency";
    }

    @RequestMapping(value = "dashboard", method = RequestMethod.GET)
    public String dashboard() {
        return "dashboard";
    }

}