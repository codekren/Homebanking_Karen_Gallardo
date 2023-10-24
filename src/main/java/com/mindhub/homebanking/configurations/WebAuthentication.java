package com.mindhub.homebanking.configurations;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter { //Global es el
    // obj que utiliza Spring Security para saber como buscar los detalles del usuario//
    // y la anotacion indica a spring que debe crear un objeto de este tipo cuando se está iniciando la //
    // aplicacion //
    @Autowired
    ClientRepository clientRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputEmail-> {

            Client client = clientRepository.findByEmail(inputEmail);

            if (client == null){
                throw new UsernameNotFoundException("Unknown user: " + inputEmail);

            }
            List<GrantedAuthority> authorities; // interfaz utilizada para representar los roles
            // o autoridades de un usuario, esto representa permisos que puede tener dentro en una
            //aplicacion

            if ("admin@admin.com".equals(client.getEmail())) {

                authorities = AuthorityUtils.createAuthorityList("ADMIN");


            } else {
                authorities = AuthorityUtils.createAuthorityList("CLIENT");


            }
            return new User(client.getEmail(), client.getPassword(),authorities);


        });

    }

    @Bean // genera un objeto de tipo PassEncod para luego usar en cualquier parte de la aplicacion
    public PasswordEncoder passwordEncoder() {


        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

}

