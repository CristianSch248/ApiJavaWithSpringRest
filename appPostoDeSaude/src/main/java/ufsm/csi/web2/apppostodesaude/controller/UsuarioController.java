package ufsm.csi.web2.apppostodesaude.controller;

import org.springframework.web.bind.annotation.*;
import ufsm.csi.web2.apppostodesaude.DAO.UsuarioDAO;
import ufsm.csi.web2.apppostodesaude.model.Usuario;

import java.util.ArrayList;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @GetMapping("/listar")
    public ArrayList<Usuario> listarUsuarios(){
        return new UsuarioDAO().getUsuarios();
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@RequestBody Usuario usuario) {
        return new UsuarioDAO().cadastraUsuario(usuario);
    }

    @PutMapping("/update")
    public void update(@RequestBody Usuario usuario){
        new UsuarioDAO().editarUsuario(usuario);
    }

    @PutMapping("/delete")
    public void deleteUsuario(@RequestBody Usuario usuario){
        new UsuarioDAO().excluirUsuario(usuario);
    }
}
