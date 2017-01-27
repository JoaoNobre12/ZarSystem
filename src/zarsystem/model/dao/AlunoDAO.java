package zarsystem.model.dao;

/**
 * Data access object Aluno
 * Created by joao on 04/09/16.
 */

import javafx.scene.control.TextField;
import zarsystem.controller.MenuController;
import zarsystem.model.Helpers;
import zarsystem.model.domain.Aluno;
import zarsystem.view.blur.Blur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AlunoDAO extends Dao{

    private MenuController menuController;
    public AlunoDAO(MenuController menuController) {
        this.menuController = menuController;
    }

    /** cria um aleatório que não exite na tabela
     *  @return um inteiro entre 0 e 99999
     */
    private int generateCodMatricula(){

        String sqlQuery = "SELECT FLOOR(RAND() * 99999) AS random_num FROM alunos "
                + "WHERE \"random_num\" NOT IN (SELECT cod_matricula FROM alunos) LIMIT 1";

        int codMatricula = 0;
        try {
            PreparedStatement query = connection.prepareStatement(sqlQuery);

            ResultSet result = query.executeQuery();

            while (result.next()) {
                System.out.println("cliente id: "+result.getInt(1));
                codMatricula = result.getInt(1);
            }

            Double d = Math.random() * 99999;

            codMatricula = (codMatricula == 0) ? (d.intValue()) : (codMatricula);

            return codMatricula;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void insertDB(Aluno aluno) throws Exception{

            PreparedStatement statement = connection.prepareStatement("INSERT INTO alunos" +
                    "(cod_matricula, nome, nascimento, sexo, rg, cpf, plano, dia_pagamento, pagamento)"
                  + "VALUES(?,?,?,?,?,?,?,?,?)");

            statement.setInt(1, generateCodMatricula());
            statement.setString(2, aluno.getNome());
            statement.setDate(3, Helpers.parseSQLDate(aluno.getDtNasc()));

            statement.setString(4, String.valueOf(aluno.getSexo())); //MUDAR

            statement.setString(5, aluno.getRg());
            statement.setString(6, aluno.getCpf());
            statement.setString(7, aluno.getPlano());
            statement.setInt(8, aluno.getDiaPagamento());
            statement.setBoolean(9, (aluno.getPagamento().equals("Realizado"))); //se sim ele manda true, se não ele manda false

            statement.execute();
            Blur.logLabel(menuController.lblUniversalLogs, "Aluno cadastrado com sucesso!");


            Blur.logLabel(menuController.lblUniversalLogs, "Erro ao cadastrar...");

    }

    /**
     * Consulta geral à tabela de alunos
     * @return List<Aluno> com todos os alunos da tabela
     * */
    public List<Aluno> consultDb(){
        String query = "SELECT * FROM alunos";
        List<Aluno> listAluno = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Aluno aluno = new Aluno();
                aluno.setCodMatricula(result.getInt("cod_matricula"));
                aluno.setPlano(result.getString("plano"));
                aluno.setDiaPagamento(result.getInt("dia_pagamento"));

                String sexo = (result.getString("sexo").equals("M")) ? "Masculino" : "Feminino";

                aluno.setSexo(sexo);
                aluno.setNome(result.getString("nome"));
                aluno.setRg(result.getString("rg"));
                aluno.setCpf(result.getString("cpf"));
                aluno.setDtNasc(Helpers.parseStringDate(result.getDate("nascimento")));
                aluno.setPagamento(result.getBoolean("pagamento") ? "Realizado" : "Pendente");

                listAluno.add(aluno);
            }

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return listAluno;
    }

    public boolean deleteAlunoDB(int codAluno){
        String queryAvFisica = "DELETE FROM av_fisica WHERE cod_matricula = " + codAluno;
        String queryAluno = "DELETE FROM alunos WHERE cod_matricula = " + codAluno;

        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(queryAvFisica);
            statement.execute();

            statement = connection.prepareStatement(queryAluno);
            statement.execute();
            Blur.logLabel(menuController.lblUniversalLogs, "Aluno excluido!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Blur.logLabel(menuController.lblUniversalLogs, "Erro ao excluir aluno!");
            return false;
        }
    }

    public int getNumAlunosCadastrados(){

        String query = "SELECT COUNT(*) AS countAlunos FROM alunos";

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt("countAlunos");

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Busca a idade do aluno no banco de dados
     *
     * @param codAluno código do aluno
     * @return um inteiro com a idade do aluno ou 0 se algo der errado
     * */
    public int colsultIdadeAluno(int codAluno){
        String query = "SELECT EXTRACT(YEAR FROM nascimento) AS ano_nasc_aluno FROM alunos " +
                "WHERE cod_matricula = '" + codAluno +"'";
        int anoNasc = 0;

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                anoNasc = resultSet.getInt("ano_nasc_aluno");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        * Format formatDate	= new SimpleDateFormat("dd/MM/yyyy");
		            String date = formatDate.format(new Date());
        * */

        Format formatYear = new SimpleDateFormat("yyyy");

        return Integer.parseInt(formatYear.format(new Date()))  - anoNasc;
    }

    /**
     * Setar se a avaliação física está cadastrada
     * @param codAluno código do aluno para consulta
     * @return true se tuder ocorrer bem, false se se algo der errado
     * */
    public boolean setAvFisicaCadastrada(int codAluno) throws SQLException{
            String query = "UPDATE alunos SET av_fisica_cadastrada = ? WHERE cod_matricula = '"+codAluno+"';";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setBoolean(1, true);

            statement.execute();
            return true;

    }

    /**
     * Verificar se a avaliação física está cadastrada para alterar o comportamento da tela de cadastro de avaliação física
     * @param codAluno código do aluno para consulta
     * @return true se está cadastrada, false se não está cadastrada
     * */
    public boolean isAvFisicaCadastrada(int codAluno){
        String query = "SELECT av_fisica_cadastrada FROM alunos WHERE cod_matricula = '" + codAluno + "'";
        boolean cad = false;
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                cad = resultSet.getBoolean("av_fisica_cadastrada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cad;
    }

    /**
     * Pesquisar aluno
     * @return um arrayList com os alunos que se encaixam na pesquisa
     * @since 02/11/16
     * */
    public List<Aluno> searchAluno(String column, String dados){

        String query;

        if (column.equals("plano")){
            query  =  ("SELECT * FROM alunos WHERE "+column+" = '"+dados+"'");
        }
        else {
            query  = (dados.equals("")) ? ("SELECT * FROM alunos") : ("SELECT * FROM alunos WHERE "+column+" LIKE '"+dados+"%'");
        }

        List<Aluno> listAluno = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet result = preparedStatement.executeQuery();

            Aluno aluno = new Aluno();
            while (result.next()) {
                aluno.setCodMatricula(result.getInt("cod_matricula"));
                aluno.setPlano(result.getString("plano"));
                aluno.setSexo(result.getString("sexo").equals("M") ? "Masculino" : "Feminino");
                aluno.setNome(result.getString("nome"));
                aluno.setRg(result.getString("rg"));
                aluno.setCpf(result.getString("cpf"));
                aluno.setDtNasc(Helpers.parseStringDate(result.getDate("nascimento")));
                aluno.setPagamento(result.getBoolean("pagamento") ? "Realizado" : "Pendente");

                listAluno.add(aluno);
            }

        } catch (SQLException | ParseException e) {
            Blur.logLabel(menuController.lblUniversalLogs, "Erro ao fazer pesquisa.");
            e.printStackTrace();
        }

        return listAluno;
    }

    /**
     * Altera dados da tabela aluno
     * @param aluno domínio com os dados do aluno
     * @param codAluno com o código do aluno.
     * @exception SQLException com exceção que deve ser manipulada caso aconteça algum erro no update
     * */
    public void updateAluno(Aluno aluno, int codAluno) throws SQLException{
        String query = "UPDATE alunos SET nome = ?, nascimento = ?, sexo = ?, rg = ?, cpf = ?, plano = ?, dia_pagamento = ? " +
                "WHERE cod_matricula = '" + codAluno + "'";


        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, aluno.getNome());
        statement.setDate(2, Helpers.parseSQLDate(aluno.getDtNasc()));

        statement.setString(3, String.valueOf(aluno.getSexo()));

        statement.setString(4, aluno.getRg());
        statement.setString(5, aluno.getCpf());
        statement.setString(6, aluno.getPlano());
        statement.setInt(7, aluno.getDiaPagamento());

        statement.execute();
    }

    /**
     * Compara os dias de pagamento com o dia atual e seta como false os pagamentos atrasados
     * */
    public void updatePagamentos() throws SQLException{
        System.out.print("Mudando pagamentos... ");

        String query = "UPDATE alunos SET pagamento = '0' WHERE (dia_pagamento < day(now()) AND MONTH(NOW()) != MONTH(data_ultimo_pagamento))";
        PreparedStatement statement = connection.prepareStatement(query);

        System.out.println(statement.executeUpdate() + " linhas afetadas.");
    }


    /**
     * Registra o pagamento com base no código do aluno
     * seta pagamento como true
     * */
    public void realizarPagamento(int codMatricula) throws SQLException{
        String query = "UPDATE alunos SET pagamento = '1' WHERE cod_matricula = '"+codMatricula+"'";
        String query2 = "UPDATE alunos SET data_ultimo_pagamento = date(now()) WHERE cod_matricula = '"+codMatricula+"'";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.addBatch(query);
        statement.addBatch(query2);

        statement.executeBatch();

        System.out.println("Pagamento realizado");
    }

    /**
     * @return Retorna um List de Aluno com os alunos com pagamento pendente.
     * */
    public List<Aluno> consultPagamentosPendentes() throws SQLException, ParseException{
        ArrayList<Aluno> listPendentes = new ArrayList<>();

        String query = "SELECT * FROM alunos WHERE pagamento = '0'";

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();

        while (result.next()){
            Aluno alunoPendente = new Aluno();

            alunoPendente.setCodMatricula(result.getInt("cod_matricula"));
            alunoPendente.setPlano(result.getString("plano"));

            String sexo = (result.getString("sexo").equals("M")) ? "Masculino" : "Feminino";

            alunoPendente.setSexo(sexo);
            alunoPendente.setNome(result.getString("nome"));
            alunoPendente.setRg(result.getString("rg"));
            alunoPendente.setCpf(result.getString("cpf"));
            alunoPendente.setDiaPagamento(result.getInt("dia_pagamento"));
            alunoPendente.setDtNasc(Helpers.parseStringDate(result.getDate("nascimento")));
            alunoPendente.setPagamento(result.getBoolean("pagamento") ? "Realizado" : "Pendente");

            listPendentes.add(alunoPendente);
        }

        return listPendentes;
    }

    /**
     * Pesquisar alunos com pagamentos pendentes
     * @return um arrayList com os alunos que se encaixam na pesquisa
     * @since 17/12/16
     * */
    public List<Aluno> searchPagamentosPendentes(TextField textField, String value){

        String query;

        if (textField.getId().equals("txtPagNomeAluno")){
            query  =  (value.isEmpty()) ? "SELECT * FROM alunos WHERE pagamento = '0'" : "SELECT * FROM alunos WHERE pagamento = '0' AND nome LIKE '"+ value +"_%'";
        }
        else {
            query  =  (value.isEmpty()) ? "SELECT * FROM alunos WHERE pagamento = '0'" : "SELECT * FROM alunos WHERE pagamento = '0' AND cod_matricula LIKE '"+ value +"_%'";
        }

        List<Aluno> listPendentes = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()){
                Aluno alunoPendente = new Aluno();

                alunoPendente.setCodMatricula(result.getInt("cod_matricula"));
                alunoPendente.setPlano(result.getString("plano"));

                String sexo = (result.getString("sexo").equals("M")) ? "Masculino" : "Feminino";

                alunoPendente.setSexo(sexo);
                alunoPendente.setNome(result.getString("nome"));
                alunoPendente.setRg(result.getString("rg"));
                alunoPendente.setCpf(result.getString("cpf"));
                alunoPendente.setDiaPagamento(result.getInt("dia_pagamento"));
                alunoPendente.setDtNasc(Helpers.parseStringDate(result.getDate("nascimento")));
                alunoPendente.setPagamento(result.getBoolean("pagamento") ? "Realizado" : "Pendente");

                listPendentes.add(alunoPendente);
            }

        } catch (SQLException | ParseException e) {
            Blur.logLabel(menuController.lblUniversalLogs, "Erro ao fazer pesquisa.");
            e.printStackTrace();
        }

        return listPendentes;
    }

}