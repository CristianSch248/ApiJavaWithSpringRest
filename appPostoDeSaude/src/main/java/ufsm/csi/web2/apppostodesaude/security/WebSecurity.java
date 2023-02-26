package ufsm.csi.web2.apppostodesaude.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAutenticacao(AuthenticationManagerBuilder auth) throws Exception{
        System.out.println("(log) WebSecurity - configureAutenticacao carregado");
        auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public FiltroAutenticacao filtroAutenticacao() throws Exception{
        return new FiltroAutenticacao();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //.authenticationProvider(this.authProvider())
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/usuario/cadastrar").hasAuthority("admin")
                .antMatchers(HttpMethod.PUT, "/usuario/update").hasAuthority("admin")
                .antMatchers(HttpMethod.PUT, "/usuario/delete").hasAuthority("admin")
                .antMatchers(HttpMethod.GET, "/usuario/listar").hasAuthority("admin")

                .antMatchers(HttpMethod.POST, "/paciente/cadastrar").hasAuthority("user")
                .antMatchers(HttpMethod.GET, "/paciente/{id}").hasAuthority("user")
                .antMatchers(HttpMethod.PUT, "/paciente/update").hasAuthority("user")
                .antMatchers(HttpMethod.PUT, "/paciente/delete").hasAuthority("user")
                .antMatchers(HttpMethod.GET, "/paciente/listar").hasAuthority("user")

                .antMatchers(HttpMethod.GET, "/medico/listar").hasAuthority("user")
                .antMatchers(HttpMethod.GET, "/medico/{id}").hasAuthority("user")
                .antMatchers(HttpMethod.POST, "/medico/cadastrar").hasAuthority("user")
                .antMatchers(HttpMethod.PUT, "/medico/update").hasAuthority("user")
                .antMatchers(HttpMethod.PUT, "/medico/delete").hasAuthority("user")

                .antMatchers(HttpMethod.GET, "/consulta/listar").hasAuthority("user")
                .antMatchers(HttpMethod.POST, "/consulta/cadastrar").hasAuthority("user")
                .antMatchers(HttpMethod.PUT, "/consulta/update").hasAuthority("user")
                .antMatchers(HttpMethod.PUT, "/consulta/delete").hasAuthority("user")
                .antMatchers(HttpMethod.POST, "/consulta/{id}").hasAuthority("user")
                .antMatchers(HttpMethod.POST, "/consulta/todo/historico/atendimento/Medico").hasAuthority("user")
                .antMatchers(HttpMethod.POST, "/consulta/todos-historico/Atendimantos-Paciente").hasAuthority("user");

                //.and().formLogin();
        http.addFilterBefore(this.filtroAutenticacao(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsFilter corsFilter(){
        System.out.println("(log) configurando cors");

        final var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        final var source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
