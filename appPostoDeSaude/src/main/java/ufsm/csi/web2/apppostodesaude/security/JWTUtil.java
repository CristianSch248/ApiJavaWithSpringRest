package ufsm.csi.web2.apppostodesaude.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import ufsm.csi.web2.apppostodesaude.model.Usuario;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Jwts;

public class JWTUtil {
    private static final long TEMPO_VIDA = Duration.ofSeconds(1200).toMillis();

    public String geraToken(Usuario usuario){

        final Map<String, Object> claims = new HashMap<>();
        claims.put("sub", usuario.getEmail());//normalmente utilizado para identificar de quem é o token
        claims.put("permissoes: ", usuario.getPermissao());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+this.TEMPO_VIDA))
                .signWith(SignatureAlgorithm.HS256, "POOW2")
                .compact();
    }

    public String getEmailToken(String token){
        if(token != null){
            return this.parseToken(token).getSubject();
        }else {
            return null;
        }
    }

    public boolean isTokenExpirado(String token){

        if(token != null){
            return this.parseToken(token).getExpiration().before(new Date());
        } else {
            return false;
        }
    }
    private Claims parseToken(String token){
        return Jwts.parser()
                .setSigningKey("POOW2")
                .parseClaimsJws(token.replace("Bearer", ""))
                .getBody();
    }
}
