package ufsm.csi.web2.apppostodesaude.DAO;

import java.sql.*;
import java.util.ArrayList;
import ufsm.csi.web2.apppostodesaude.model.Medico;

public class MedicoDAO {
    private String sql;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String status;

    public ArrayList<Medico> getMedicos(){
        ArrayList<Medico> medicos = new ArrayList<>();
        try(Connection connection = new ConectaDB().getConexao()){

            this.sql = "SELECT id_medico, nome_medico, especialidade FROM medico WHERE ativo = true";

            this.statement = connection.createStatement();
            this.resultSet = this.statement.executeQuery(this.sql);

            while(this.resultSet.next()){
                Medico medico = new Medico();
                medico.setId(this.resultSet.getInt("id_medico"));
                medico.setNome(this.resultSet.getString("nome_medico"));
                medico.setEspecialidade(this.resultSet.getString("especialidade"));

                medicos.add(medico);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return medicos;
    }

    public Medico getMedico(int id){
        Medico medico = new Medico();

        try(Connection connection = new ConectaDB().getConexao()){
            this.sql="SELECT id_medico, nome_medico, especialidade " +
                    "FROM medico " +
                    "WHERE id_medico = ?";
            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, id);
            this.resultSet = this.preparedStatement.executeQuery();

            while (this.resultSet.next()){
                medico.setId(resultSet.getInt("id_medico"));
                medico.setNome(resultSet.getString("nome_medico"));
                medico.setEspecialidade(resultSet.getString("especialidade"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return medico;
    }

    public Medico getMedicoAtivo(int id){
        Medico medico = new Medico();

        try(Connection connection = new ConectaDB().getConexao()){
            this.sql="SELECT id_medico, nome_medico, especialidade " +
                    "FROM medico " +
                    "WHERE id_medico = ? " +
                    "AND ativo = true";
            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, id);
            this.resultSet = this.preparedStatement.executeQuery();

            while (this.resultSet.next()){
                medico.setId(resultSet.getInt("id_medico"));
                medico.setNome(resultSet.getString("nome_medico"));
                medico.setEspecialidade(resultSet.getString("especialidade"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return medico;
    }

    public String cadastraMedico(Medico medico){
        System.out.println("estou cadastrando um medico");
        try(Connection connection = new ConectaDB().getConexao()){
            connection.setAutoCommit(false);

            this.sql = "INSERT INTO medico (nome_medico, especialidade, id_hospital, ativo)" +
                    "VALUES(?,?, 1, true)";

            this.preparedStatement = connection.prepareStatement(this.sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            this.preparedStatement.setString(1, medico.getNome());
            this.preparedStatement.setString(2, medico.getEspecialidade());

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

    public String editaMedico(Medico medico){
        try (Connection connection = new ConectaDB().getConexao()) {
            System.out.println(medico.getId());
            System.out.println(medico.getNome());
            System.out.println(medico.getEspecialidade());

            connection.setAutoCommit(false);



            this.sql = "update medico set " +
                    "nome_medico = ?, " +
                    "especialidade = ? " +
                    "where id_medico = ? ";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setString(1, medico.getNome());
            this.preparedStatement.setString(2, medico.getEspecialidade());
            this.preparedStatement.setInt(3, medico.getId());
            this.preparedStatement.executeUpdate();

            if (this.preparedStatement.getUpdateCount() > 0) {
                this.status = "OK";
                System.out.println(this.status);
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.status = "Erro";
        }
        return this.status;
    }

    public String deletaMedico(Medico medico){
        try (Connection connection = new ConectaDB().getConexao()) {
            connection.setAutoCommit(false);

            this.sql = "UPDATE medico SET ativo = false WHERE id_medico = ?";
            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, medico.getId());
            this.preparedStatement.executeUpdate();

            if (this.preparedStatement.getUpdateCount() > 0) {
                this.status = "OK";
                System.out.println(this.status);
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.status = "ERROR";
        }
        return "";
    }
}
