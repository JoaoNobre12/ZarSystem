package zarsystem.model.dao;

import zarsystem.controller.MenuController;
import zarsystem.model.domain.Produto;
import zarsystem.view.blur.Blur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object do domínio de produtos
 * Created by joaov on 25/11/2016.
 */
public class ProdutoDao extends Dao{
    private MenuController menuController;

    public ProdutoDao(MenuController menuController) {
        this.menuController = menuController;
    }

    private int generateCodProduto(){

        String sqlQuery = "SELECT FLOOR(RAND() * 99999) AS random_num FROM produtos "
                + "WHERE \"random_num\" NOT IN (SELECT cod_produto FROM produtos) LIMIT 1";

        int codMatricula = 0;
        try {
            PreparedStatement query = connection.prepareStatement(sqlQuery);

            ResultSet result = query.executeQuery();

            while (result.next()) {
                System.out.println("Produto id: "+result.getInt(1));
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

    public void insertDB(Produto produto) throws SQLException{

        System.out.println("Is always by my side: " + generateCodProduto());

        PreparedStatement statement = connection.prepareStatement("INSERT INTO produtos" +
                "(cod_produto, nome, tipo, descricao, valor, quantidade, valor_revenda)"
                + "VALUES(?,?,?,?,?,?,?)");

        statement.setInt(1, generateCodProduto());
        statement.setString(2, produto.getNome());
        statement.setString(3, produto.getTipo());

        statement.setString(4, produto.getDescricao());

        statement.setDouble(5, produto.getValor());
        statement.setInt(6, produto.getQuantidade());
        statement.setDouble(7, produto.getValorRevenda());

        statement.execute();
    }

    /**
     * Consulta geral à tabela de alunos
     * @return List<Aluno> com todos os alunos da tabela
     * */
    public List<Produto> consultDb() throws SQLException{

        String query = "SELECT * FROM produtos";
        List<Produto> listAluno = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Produto produto = new Produto();
                produto.setCodProduto(result.getInt("cod_produto"));
                produto.setNome(result.getString("nome"));
                produto.setTipo(result.getString("tipo"));
                produto.setDescricao(result.getString("descricao"));
                produto.setValor(result.getDouble("valor"));
                produto.setQuantidade(result.getInt("quantidade"));
                produto.setValorRevenda(result.getDouble("valor_revenda"));

                listAluno.add(produto);
            }

        return listAluno;
    }

    public void deleteProduto(int codProduto) throws SQLException{
        String query = "DELETE FROM produtos WHERE cod_produto = " + codProduto;

        PreparedStatement statement =  connection.prepareStatement(query);
        statement.execute();
    }
}
