package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@EnableWebSecurity // habilita la configuracion
@Configuration // para configurar y ajustar la clase
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .antMatchers("/web/index.html","/web/pages/login.html","/web/css/**"
                        ,"/web/images/**","/web/script/index.js","/web/script/login.js").permitAll()
                .antMatchers(HttpMethod.POST,"/api/clients","/api/login").permitAll()
                .antMatchers("/web/pages/manager.html","/web/script/manager.js",
                        "/h2-console/**","/rest/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/accounts","/api/clients").hasAuthority("ADMIN")
                .antMatchers("/web/pages/account.html/**","/web/pages/accounts.html",
                        "/web/pages/cards.html","/web/pages/create-cards.html","/web/pages/transfers.html"
                        ,"/api/accounts/{id}","/web/script/accounts.js","/web/script/account.js"
                        ,"/web/script/cards.js","/web/pages/loan-application.html","/web/script/loan-application.js",
                        "/web/script/create-card.js","/web/script/transfers.js").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET,"/api/clients/current","/api/clients/current/cards",
                        "/api/clients/current/accounts","/api/loans").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/clients/current/accounts",
                        "/api/clients/current/cards","/api/transactions","/api/loans",
                        "/web/pages/loan-application.html","api/clients/current/cards/delete").hasAuthority("CLIENT")
                .anyRequest().denyAll();




        http.formLogin() //Define el logeo

                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        http.csrf().disable();

        http.headers().frameOptions().disable();
        //if user is not authenticated send an auth failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        //que limpia los atributos de autenticación.

        http.formLogin().failureHandler( //Send response fallo de inicio de sesión que devuelve un error
                (req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        //Configura un manejador de éxito de cierre de sesión que devuelve un código de
        // estado HTTP adecuado después de una salida exitosa.
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        //es una función que se utiliza para limpiar los atributos de autenticación
        // en la sesión del usuario después de un inicio de sesión exitoso
        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
