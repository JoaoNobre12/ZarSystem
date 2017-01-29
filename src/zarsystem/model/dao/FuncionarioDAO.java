package zarsystem.model.dao;

import zarsystem.controller.MenuController;
import zarsystem.model.domain.Funcionario;
import zarsystem.view.blur.Blur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object do cadastro e consulta de funcionários
 * Created by joao on 14/11/16.
 */
public class FuncionarioDAO extends Dao{

    private MenuController menuController;

    public FuncionarioDAO(MenuController menuController) {
        this.menuController = menuController;
    }

    private int generateCodMatricula(){

        String sqlQuery = "SELECT FLOOR(RAND() * 99999) AS random_num FROM funcionarios "
                + "WHERE \"random_num\" NOT IN (SELECT cod_funcionario FROM funcionarios) LIMIT 1";

        int codMatricula = 0;
        try {
            PreparedStatement query = connection.prepareStatement(sqlQuery);

            ResultSet result = query.executeQuery();

            while (result.next()) {
                System.out.println("funcionário id: "+result.getInt(1));
                codMatricula = result.getInt(1);
            }

            Double d = Math.random() * 99999;

            codMatricula = (codMatricula == 0) ? (d.intValue()) : (codMatricula);

            return codMatricula;
        } catch (Exception e) {
            e.printStackTrace();
            return 4;
        }
    }

    public void insertDb(Funcionario funcionario) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO funcionarios" +
                "(cod_funcionario, nome, sexo, rg, cpf, objetivo, formacao, salario)"
                + "VALUES(?,?,?,?,?,?,?,?)");

        statement.setInt(1, generateCodMatricula());
        statement.setString(2, funcionario.getNome());
        statement.setString(3, funcionario.getSexo());
        statement.setString(4, funcionario.getRg());
        statement.setString(5, funcionario.getCpf());
        statement.setString(6, funcionario.getObjetivo());
        statement.setString(7, funcionario.getFormacao());
        statement.setDouble(8, funcionario.getSalario());

        statement.execute();
    }

    /**
     * Consulta geral à tabela de funcionarios
     * @return List<Aluno> com todos os funcionarios da tabela
     * */
    public List<Funcionario> consultDb(){
        String query = "SELECT * FROM funcionarios";
        List<Funcionario> listFuncionario = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Funcionario funcionario = new Funcionario();

                funcionario.setCodFuncionario(result.getInt("cod_funcionario"));

                funcionario.setNome(result.getString("nome"));
                funcionario.setSexo((result.getString("sexo").equals("M")) ? "Masculino" : "Feminino");
                funcionario.setRg(result.getString("rg"));
                funcionario.setCpf(result.getString("cpf"));
                funcionario.setSalario(result.getDouble("salario"));
                funcionario.setObjetivo(result.getString("objetivo"));

                funcionario.setFormacao(result.getString("formacao"));

                listFuncionario.add(funcionario);

            }

        } catch (SQLException e) {
            Blur.logLabel(menuController.lblUniversalLogs, "Não é possível consultar os funcionários");
            e.printStackTrace();
        }

        return listFuncionario;
    }

    public boolean deleteFuncionario(int codFuncionario){
        String query = "DELETE FROM funcionarios WHERE cod_funcionario = " + codFuncionario;

        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(query);
            statement.execute();
            Blur.logLabel(menuController.lblUniversalLogs, "Funcionário excluido!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Blur.logLabel(menuController.lblUniversalLogs, "Não foi possível excluir o funcionário.");
            return false;
        }
    }

    /**
     * Altera dados da tabela aluno
     * @param funcionario domínio com os dados do funcionário
     * @param codFuncionario código do funcionário que será atualizado
     * @exception SQLException com exceção que deve ser manipulada caso aconteça algum erro no update
     * */
    public void updateFuncionario(Funcionario funcionario, int codFuncionario) throws SQLException{
        String query = "UPDATE funcionarios SET nome = ?, sexo = ?, rg = ?,  cpf = ?, objetivo = ?, formacao = ?, salario = ? " +
                "WHERE cod_funcionario = '"+ codFuncionario +"'";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, funcionario.getNome());
        statement.setString(2, String.valueOf(funcionario.getSexo()));
        statement.setString(3, funcionario.getRg());
        statement.setString(4, funcionario.getCpf());
        statement.setString(5, funcionario.getObjetivo());
        statement.setString(6, funcionario.getFormacao());
        statement.setDouble(7, funcionario.getSalario());

        statement.execute();
    }

    /**
     * soma dos salarios de todos os funcionarios
     */
    public double somaSalarios() throws SQLException{
        String query = "SELECT SUM(salario) FROM funcionarios";

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();

        result.next();

        return result.getInt(1);
    }
}
