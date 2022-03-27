package com.renting_boat.demo.security.config;

import com.renting_boat.demo.security.exception.HttpUnauthorizedEntryPoint;
import com.renting_boat.demo.security.jwt.JwtFilter;
import com.renting_boat.demo.security.jwt.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)   //dozvoljavamo @PreAuthorize, za role(ne treba nam)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final HttpUnauthorizedEntryPoint httpUnauthorizedEntryPoint;    //da ne bi stalno koristili new injektujemo exception hendler
    private final UserDetailsServiceImpl userDetailsService;                //provjerava dobijene podatke
    private final JwtFilter jwtFilter;                                      //filter koji se prvi aktivira

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth
            .userDetailsService(userDetailsService)     //za provjeru dobijenih podataka
            .passwordEncoder(passwordEncoder());        //uz pomoc ovog algoritma dekodiramo pasvort iz baze
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
            .exceptionHandling().authenticationEntryPoint(httpUnauthorizedEntryPoint)   //kako hendlujemo exception
            .and()
            .headers().frameOptions().disable() //gasimo i frame
            .and()
            .csrf().disable()   //csrf gasim jer cemo koristiti jason veb token
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //necemo cuvati podatke u sesiji nego jason veb token
            .and()
            .authorizeRequests()    //autorizovani zahtjrvi su sledeci
            .antMatchers("/api/authenticate/**").permitAll()
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .antMatchers("/api/**").authenticated()
            .anyRequest().authenticated();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);    //poziva se filter prilikom svakog http zahtjeva
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //Koristim bCript za kodiranje passworda
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();   //njega koristimo za autentifikaciju, poziv njegovih metoda
    }
}
