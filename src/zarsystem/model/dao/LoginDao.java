package zarsystem.model.dao;

import zarsystem.model.domain.Login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Data access object de Login
 * Created by joaov on 11/12/2016.
 */
public class LoginDao extends Dao{
    /**
     * Altera dados da tabela aluno
     * @param login domínio com os dados de login
     * @exception SQLException com exceção que deve ser manipulada caso aconteça algum erro no update
     * */
    public void updateLoginUser(Login login) throws SQLException{
        String query = "UPDATE login_user SET senha = ?" +
                "WHERE usuario = '"+ login.getUser() +"'";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, login.getPass());

        statement.execute();
    }

    /**
     * Consulta geral à tabela de logins
     * @return List<Login> com todos os logins cadastrados
     * */
    public List<Login> consultDb(){
        String query = "SELECT * FROM login_user";
        List<Login> listLogins = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Login login = new Login();

                login.setUser(result.getString("usuario"));
                login.setPass(result.getString("senha"));
                login.setSystemKey(result.getString("system_key"));

                listLogins.add(login);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listLogins;
    }
}