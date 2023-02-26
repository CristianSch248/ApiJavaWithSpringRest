package ufsm.csi.web2.apppostodesaude.service;
import ufsm.csi.web2.apppostodesaude.DAO.UsuarioDAO;
import ufsm.csi.web2.apppostodesaude.model.Usuario;

public class UsuarioService {
    private UsuarioDAO dao;

    public Usuario autenticado(String email, String senha){

        dao = new UsuarioDAO();

        Usuario usuario = dao.getUsuario(email);

        try{
            if(usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)){
                return usuario;
            }
        } catch (Exception e){
            e.printStackTrace();
            //throw new RuntimeException("Usuário não encontrado!", e);
        }
        return null;
    }
}
