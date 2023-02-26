package ufsm.csi.web2.apppostodesaude.DAO;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ufsm.csi.web2.apppostodesaude.model.Usuario;
import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {
    private String sql;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String status;

    public Usuario getUsuario(String email){
        Usuario usuario = null;

        try(Connection connection = new ConectaDB().getConexao()){
            this.sql = "SELECT * FROM usuario " +
                        " WHERE email_usuario = ?";

            preparedStatement = connection.prepareStatement(this.sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                usuario = new Usuario();
                usuario.setId((resultSet.getInt("id_usuario")));
                usuario.setEmail(resultSet.getString("email_usuario"));
                usuario.setSenha(resultSet.getString("senha"));
                usuario.setPermissao(resultSet.getString("permissao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public String cadastraUsuario(Usuario usuario){
        try(Connection connection = new ConectaDB().getConexao()){
            connection.setAutoCommit(false);

            this.sql = "INSERT INTO usuario (nome_usuario,email_usuario, senha, id_hospital, permissao)" +
                    "values (?, ?, ?, ?, ?); ";

            this.preparedStatement=connection.prepareStatement(this.sql, PreparedStatement.RETURN_GENERATED_KEYS);
            this.preparedStatement.setString(1, usuario.getNome());
            this.preparedStatement.setString(2, usuario.getEmail());
            this.preparedStatement.setString(3, new BCryptPasswordEncoder().encode(usuario.getSenha()));
            this.preparedStatement.setInt(4, usuario.getHospital().getId());
            this.preparedStatement.setString(5, usuario.getPermissao());

            // new BCryptPasswordEncoder().encode(usuario.getSenha());

            System.out.println(usuario.getEmail());
            System.out.println(usuario.getSenha());
            System.out.println(usuario.getId());

            this.preparedStatement.execute();
            this.resultSet = this.preparedStatement.getGeneratedKeys();
            this.resultSet.next();

            if(this.resultSet.getInt(1) > 0){
                this.status = "OK";
                connection.commit();
                System.out.println(this.status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.status = "ERROR";
            System.out.println(this.status);
        }
        return this.status;
    }

    public String editarUsuario(Usuario usuario){
        try(Connection connection = new ConectaDB().getConexao()){
            connection.setAutoCommit(false);

            this.sql = "update USUARIO " +
                    "set nome_usuario = ?, " +
                    "email_usuario = ?, " +
                    "senha = ?, " +
                    "id_hospital = ?, " +
                    "permissao = ? " +
                    "where id_usuario = ?";
            this.preparedStatement = connection.prepareStatement(this.sql);

            this.preparedStatement.setString(1, usuario.getNome());
            this.preparedStatement.setString(2, usuario.getEmail());
            this.preparedStatement.setString(3, new BCryptPasswordEncoder().encode(usuario.getSenha()));
            this.preparedStatement.setInt(4, usuario.getHospital().getId());
            this.preparedStatement.setString(5, usuario.getPermissao());
            this.preparedStatement.setInt(6, usuario.getId());

            this.preparedStatement.executeUpdate();

            if(this.preparedStatement.getUpdateCount() > 0){
                this.status = "OKk";
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.status = "OK";
        }
        return "";
    }

    public String excluirUsuario(Usuario usuario){
        try(Connection connection = new ConectaDB().getConexao()){
            connection.setAutoCommit(false);

            this.sql = "UPDATE usuario SET ativo = false" +
                    " where id_usuario = ?";
            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, usuario.getId());
            this.preparedStatement.executeUpdate();

            System.out.println(usuario.getId());

            if(this.preparedStatement.getUpdateCount() > 0){
                this.status = "OK";
                System.out.println(this.status);
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.status = "OK";
        }
        return "";
    }

    public ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try(Connection connection = new ConectaDB().getConexao()){
            this.sql = "SELECT id_usuario, nome_usuario, email_usuario FROM usuario ORDER BY nome_usuario";

            this.statement = connection.createStatement();
            this.resultSet = this.statement.executeQuery(this.sql);
            while(this.resultSet.next()){
                Usuario usuario = new Usuario();

                usuario.setId(this.resultSet.getInt("id_usuario"));
                usuario.setNome(this.resultSet.getString("nome_usuario"));
                usuario.setEmail(this.resultSet.getString("email_usuario"));

                usuarios.add(usuario);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return usuarios;
    }
}
