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


@EnableWebSecurity // habilita la seguridad web en la aplic y dice a Spring que debe cargar la configuracion de seguridad definida en WebAuthorization
@Configuration // indica q esta clase es un clase de configuracion de Spring
public class WebAuthorization extends WebSecurityConfigurerAdapter {
    //Spring security usa el objeto WebSecurityConfigurerAdapter para configurar
    // la autorizacion, es por ello q la clase WebAuthorization hereda del mismo para
    // sobrescribir el método configure indicando la nueva configuración de autorizacion

    @Override // Se modifica el métod configure de WSCA para personalizar la config de seguridad Este método se encarga de configurar cómo se autorizan y autentican las solicitudes HTTP en la aplicación
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() //Comienza la configuración de autorización de las solicitudes HTTP,se definen las reglas de autorización para rutas específicas.

                .antMatchers(HttpMethod.POST,"/web/index.html","/web/pages/accounts.html").permitAll()
                .antMatchers(HttpMethod.POST,"/api/clients").permitAll()
                .antMatchers(HttpMethod.POST,"/web/pages/login.html").permitAll()
                .antMatchers("/h2-console").hasAuthority("ADMIN") //Permite definir reglas de autorización basadas en patrones de URL.
                .antMatchers("/api/clients/current/cards").authenticated()
                .antMatchers(HttpMethod.POST,"/web/pages/cards.html","/api/clients/current/accounts").hasAuthority("CLIENT"); // aqui se especifican rutas y roles



        http.formLogin() //Configura la autenticación basada en form define los parámet de entrada de nombre de usuario y contraseña,
                // así como la página de inicio de sesión personalizada en /api/login.

                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout"); //Configura la funcionalidad de cierre de sesión.
            // Define la URL de cierre de sesión en /api/logout.

        http.csrf().disable();//Desactiva la protección contra ataques CSRF (Cross-Site Request Forgery)
        // Esto es común en aplicaciones que utilizan autenticación basada en tokens, como OAuth.

        http.headers().frameOptions().disable();//Desactiva las opciones de seguridad marco para
        // permitir la visualización de la consola H2 si se está utilizando una base de datos H2 en la aplicación.

        http.exceptionHandling().authenticationEntryPoint(
                //Configura un controlador de excepciones personalizado para manejar la autenticación no exitosa.

                (req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req)); //Configura un controlador de éxito de inicio de sesión
        // que limpia los atributos de autenticación.

        http.formLogin().failureHandler( //Configura un controlador de fallo de inicio de sesión que devuelve un error
                // no autorizado en caso de un inicio de sesión fallido.
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
