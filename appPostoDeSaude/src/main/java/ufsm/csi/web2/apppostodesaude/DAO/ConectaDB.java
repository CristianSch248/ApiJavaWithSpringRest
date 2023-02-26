package ufsm.csi.web2.apppostodesaude.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConectaDB {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/PostoDeSaude";
    private static final String USER = "postgres";
    private static final String SENHA = "1234";

    public Connection getConexao(){
        Connection con = null;
        try{
            Class.forName(this.DRIVER);
            con = DriverManager.getConnection(this.URL, this.USER, this.SENHA);
        }catch(ClassNotFoundException e){
            throw new RuntimeException("Erro ao instanciar driver", e);
        }catch(Exception e){
            throw new RuntimeException("Erro ao processar Banco de dados", e);
        }
        return con;
    }
}
