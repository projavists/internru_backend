package ru.intern.config;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.intern.repository.UserRepository;
import ru.intern.security.AuthProvider;
import ru.intern.security.UserAuthoritiesService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author Kir
 * Created on 16.07.2021
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    //sha256
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.reset();

                    byte[] buffer = rawPassword.toString().getBytes(StandardCharsets.UTF_8);

                    md.update(buffer);
                    byte[] digest = md.digest();

                    String hexStr = "";
                    for (byte aDigest : digest) {
                        hexStr += Integer.toString((aDigest & 0xff) + 0x100, 16).substring(1);
                    }
                    return hexStr.toUpperCase();
                } catch (Exception e) {
                    LOG.error("can't encode password", e);
                    return "";
                }
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return this.encode(rawPassword).equals(encodedPassword != null ? encodedPassword.toUpperCase() : "");
            }
        };
    }


    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserAuthoritiesService userWithAuthoritiesService(UserRepository userRepository) {
        return new UserAuthoritiesService(userRepository);
    }

    @Bean
    public AuthProvider tcmkAuthProvider(UserAuthoritiesService userAuthoritiesService) {
        return new AuthProvider(userAuthoritiesService, passwordEncoder());
    }

    @Override
    public void configure(
            AuthenticationManagerBuilder auth
    ) throws Exception {
        auth
                .userDetailsService(new UserAuthoritiesService(userRepository))
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
    //TODO логаут убрать
        http.exceptionHandling()
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) ->
                        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"));
        http.addFilterBefore(encodingFilter, CsrfFilter.class)
                .authorizeRequests()
                .antMatchers("/api/login*", "/api/logout").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .formLogin()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").permitAll()
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf()/*.ignoringAntMatchers("/rest/", "/alarm/**", "/dispatcher/**")*/.disable();
    }
}
