package ufsm.csi.web2.apppostodesaude.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public class FiltroAutenticacao extends OncePerRequestFilter {
    @Autowired private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String url = request.getRequestURI();
        System.out.println("(log) Filtro requisição: " + url);

        try{
            if(!url.contains("login")){
                String token = request.getHeader("Authorization");

                String Email = new JWTUtil().getEmailToken(token);
                System.out.println("(log) Token: " + token);
                System.out.println("(log) Email: " + Email);
                System.out.println("(log) Token expirado? " + new JWTUtil().isTokenExpirado(token));

                if(Email != null && SecurityContextHolder.getContext().getAuthentication() == null){

                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(Email);

                    if(!new JWTUtil().isTokenExpirado(token)){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        usernamePasswordAuthenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }

            filterChain.doFilter(request, response);
        }catch (ExpiredJwtException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expirado");
        }
//        catch (AccessDeniedException e ){
//            System.out.println("(log) Acesso nagado");
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acesso nagado ou Token Expirado");
//        }
        //filterChain.doFilter(request, response);
    }
}
