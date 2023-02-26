package ufsm.csi.web2.apppostodesaude.DAO;

import ufsm.csi.web2.apppostodesaude.model.Paciente;

import java.sql.*;
import java.util.ArrayList;

public class PacienteDAO {
    private String sql;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String status;

    public ArrayList<Paciente> getPacientes(){
        ArrayList<Paciente> pacientes = new ArrayList<>();
        try(Connection connection = new ConectaDB().getConexao()){

            this.sql = "SELECT * FROM paciente WHERE ativo = true ORDER BY nome_paciente";

            this.statement = connection.createStatement();
            this.resultSet = this.statement.executeQuery(this.sql);

            while(this.resultSet.next()){
                Paciente paciente = new Paciente();

                paciente.setId(this.resultSet.getInt("id_paciente"));
                paciente.setNome(this.resultSet.getString("nome_paciente"));
                paciente.setIdade(this.resultSet.getInt("idade"));
                paciente.setSexo(this.resultSet.getString("sexo"));
                paciente.setCpf(this.resultSet.getString("cpf"));
                paciente.setTelefone(this.resultSet.getString("telefone"));
                pacientes.add(paciente);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pacientes;
    }

    public Paciente getPaciente(String nomePaciente, String CPF){
        Paciente paciente = null;

        try(Connection connection = new ConectaDB().getConexao()){

            this.sql = "SELECT " +
                    "id_paciente," +
                    "FROM paciente WHERE nome_paciente = ?" +
                    "AND cpf = ?";

            this.statement = connection.prepareStatement(this.sql);
            preparedStatement.setString(1, nomePaciente);
            preparedStatement.setString(2, CPF);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                paciente = new Paciente();
                paciente.setId((resultSet.getInt("id_paciente")));
                paciente.setNome(resultSet.getString("nome_paciente"));
                paciente.setIdade(resultSet.getInt("idade"));
                paciente.setSexo(resultSet.getString("sexo"));
                paciente.setTelefone(resultSet.getString("telefone"));
                paciente.setCpf(resultSet.getString("cpf"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return paciente;
    }

    public Paciente getPacienteUnico(int id){

        Paciente paciente = new Paciente();

        try(Connection connection = new ConectaDB().getConexao()){
            this.sql="SELECT * FROM paciente " +
                    "WHERE id_paciente = ?";

            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, id);
            this.resultSet = this.preparedStatement.executeQuery();

            while(this.resultSet.next()){
                paciente.setId((resultSet.getInt("id_paciente")));
                paciente.setNome(resultSet.getString("nome_paciente"));
                paciente.setIdade(resultSet.getInt("idade"));
                paciente.setSexo(resultSet.getString("sexo"));
                paciente.setTelefone(resultSet.getString("telefone"));
                paciente.setCpf(resultSet.getString("cpf"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return paciente;
    }

    public String cadastrarPaciente(Paciente paciente){
        System.out.println("Cadastrando novo usuario");

        try(Connection connection = new ConectaDB().getConexao()){
            connection.setAutoCommit(false);

            this.sql = "INSERT INTO paciente (nome_paciente, idade, sexo, cpf, telefone, ativo)" +
                    "VALUES (?, ?, ?, ?, ?, true); ";

            this.preparedStatement = connection.prepareStatement(this.sql, PreparedStatement.RETURN_GENERATED_KEYS);
            this.preparedStatement.setString(1, paciente.getNome());
            this.preparedStatement.setInt(2, paciente.getIdade());
            this.preparedStatement.setString(3, paciente.getSexo());
            this.preparedStatement.setString(4, paciente.getCpf());
            this.preparedStatement.setString(5, paciente.getTelefone());

            this.preparedStatement.execute();
            this.resultSet = this.preparedStatement.getGeneratedKeys();
            this.resultSet.next();

            if(this.resultSet.getInt(1) > 0){
                this.status = "OKK";
                connection.commit();
            }
        }catch(SQLException e){
            e.printStackTrace();
            this.status = "ERROR";
        }
        return this.status;
    }

    public String editarPaciente(Paciente paciente){
        try(Connection connection = new ConectaDB().getConexao()){
            connection.setAutoCommit(false);

            this.sql = "UPDATE paciente SET " +
                    "nome_paciente = ?," +
                    "idade = ?, " +
                    "sexo = ?, " +
                    "telefone = ?," +
                    "cpf = ? " +
                    "WHERE id_paciente = ?";
            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setString(1, paciente.getNome());
            this.preparedStatement.setInt(2, paciente.getIdade());
            this.preparedStatement.setString(3, paciente.getSexo());
            this.preparedStatement.setString(4, paciente.getTelefone());
            this.preparedStatement.setString(5, paciente.getCpf());
            this.preparedStatement.setInt(6, paciente.getId());
            this.preparedStatement.executeUpdate();

            if(this.preparedStatement.getUpdateCount() > 0){
                this.status = "OK";
                connection.commit();
            }
        }catch(SQLException e){
            this.status = "ERRO";
            e.printStackTrace();
        }
        return this.status;
    }

    public String excluirPaciente(Paciente paciente){
        try(Connection connection = new ConectaDB().getConexao()){
            connection.setAutoCommit(false);

            this.sql = "update paciente set ativo = false WHERE id_paciente = ?";
            this.preparedStatement = connection.prepareStatement(this.sql);
            this.preparedStatement.setInt(1, paciente.getId());
            this.preparedStatement.executeUpdate();

            if(this.preparedStatement.getUpdateCount() > 0){
                this.status = "OK";
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.status = "ERRO";
        }
        return this.status;
    }
}