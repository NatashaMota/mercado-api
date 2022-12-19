package br.com.newgo.mercado.security;

import br.com.newgo.mercado.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.userDetailsService(email -> usuarioRepository.findByEmail(email));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()//.antMatchers(HttpMethod.GET, "/listaCompra").authenticated()
                //.antMatchers(HttpMethod.GET, "/produto").authenticated()
                .antMatchers("/user/login").permitAll().and().csrf().disable();


        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //http.authorizeRequests()
                //.antMatchers("/produto/**").hasAuthority("ADMIN")
                //.antMatchers("/listaCompra").authenticated()
                //.antMatchers("/user/login")

                //.permitAll().anyRequest().authenticated().and().csrf().disable();
        //http.authorizeRequests()
          //      .antMatchers(HttpMethod.POST, "/user/login").authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

}

