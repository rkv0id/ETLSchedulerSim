package com.tnbank.agentui.config;

import com.vaadin.spring.annotation.EnableVaadin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableVaadin
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private String ldapUrls;
    private String ldapBaseDn;
    private String ldapSecurityPrincipal;
    @Value("${ldap.password}")
    private String ldapPrincipalPassword;
    private String ldapUserDnPattern;
    private String ldapEnabled;

    @Autowired
    public SecurityConfig(ApplicationPropertiesConfiguration appProperties) {
        super();
        ldapUrls = appProperties.getUrls();
        ldapBaseDn = appProperties.getBaseDn();
        ldapSecurityPrincipal = appProperties.getUsername();
        ldapUserDnPattern = appProperties.getUserDnPattern();
        ldapEnabled = appProperties.getEnabled();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/VAADIN/**", "/HEARTBEAT/**", "/UIDL/**", "/resources/**", "/login", "/login**", "/login/**").permitAll()
                .anyRequest().authenticated()
                .antMatchers("/").authenticated().and()
                .formLogin().permitAll().defaultSuccessUrl("/", true)
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        if (Boolean.parseBoolean(ldapEnabled)) {
            auth
                    .ldapAuthentication()
                    .contextSource()
                    .url(ldapUrls + ldapBaseDn)
                    .managerDn(ldapSecurityPrincipal)
                    .managerPassword(ldapPrincipalPassword)
                    .and()
                    .userDnPatterns(ldapUserDnPattern)
                    .ldapAuthoritiesPopulator(new CustomLdapAuthoritiesPopulator())
                    .passwordCompare()
                    .passwordEncoder(new LdapShaPasswordEncoder())
                    .passwordAttribute("userPassword");
        }
    }
}
