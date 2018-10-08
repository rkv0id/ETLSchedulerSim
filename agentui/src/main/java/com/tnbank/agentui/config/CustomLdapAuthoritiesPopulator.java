package com.tnbank.agentui.config;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

import java.util.Collection;
import java.util.HashSet;

public class CustomLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(
            DirContextOperations userData, String username) {
        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        if ("manager".equals(userData.getStringAttribute("title"))) {
            authorities.add(new SimpleGrantedAuthority("MANAGER"));
        } else {
            authorities.add(new SimpleGrantedAuthority("AGENT"));
        }
        return authorities;
    }

}
