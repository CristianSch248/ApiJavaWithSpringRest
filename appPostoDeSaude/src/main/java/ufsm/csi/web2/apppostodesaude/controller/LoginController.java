package ufsm.csi.web2.apppostodesaude.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ufsm.csi.web2.apppostodesaude.DAO.UsuarioDAO;
import ufsm.csi.web2.apppostodesaude.model.Usuario;
import ufsm.csi.web2.apppostodesaude.security.JWTUtil;

@CrossOrigin()
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping()
    public ResponseEntity<Object> autenticacao(@RequestBody Usuario usuario){
        System.out.println("(log) tentativa de login");
        try{
            final Authentication authentication = this.authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha()
                            ));
            if(authentication.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("(log) Autenticado");
                System.out.println("(log) Gerando token de autorização");

                String token = new JWTUtil().geraToken(usuario);

                usuario.setToken(token);

                usuario.setSenha("*****");

                System.out.println("(log) permissôes: " + authentication.getAuthorities().toString());

                return new ResponseEntity<>(usuario, HttpStatus.OK);
            }
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("(log) Usuario ou senha incorretos",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("(log) ERRO ao tentar logar no sistema",
                HttpStatus.BAD_REQUEST);
    }
}
