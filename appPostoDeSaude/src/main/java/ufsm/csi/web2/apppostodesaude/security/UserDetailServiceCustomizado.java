package ufsm.csi.web2.apppostodesaude.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ufsm.csi.web2.apppostodesaude.DAO.UsuarioDAO;
import ufsm.csi.web2.apppostodesaude.model.Usuario;

@Service
public class UserDetailServiceCustomizado implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = new UsuarioDAO().getUsuario(email);
        if(usuario == null){
            throw new UsernameNotFoundException("(log) - Usuário ou Senha incorretos");
        } else{
            UserDetails user = User.withUsername(usuario.getEmail())
                    .password(usuario.getSenha())
                    .authorities(usuario.getPermissao()).build();
            return user;
        }
    }
}
