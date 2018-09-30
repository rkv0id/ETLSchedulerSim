package com.tnbank.microauthenticate.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(o -> ((GrantedAuthority) o).toString().equals("ROLE_MANAGER")))
            return "dashboard";
        else if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(o -> ((GrantedAuthority) o).toString().equals("ROLE_AGENT"))) {
            return "agency";
        } else
            return "redirect:/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))
            return "redirect:";
        return "login";
    }

}