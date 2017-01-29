package zarsystem.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import zarsystem.controller.MenuController;
import zarsystem.model.Helpers;
import zarsystem.model.domain.Produto;
import zarsystem.model.domain.Venda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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

        System.out.println("Código do produto: " + generateCodProduto());

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
        List<Produto> produtos = new ArrayList<>();

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
                produto.setVendas(result.getInt("vendas"));
                produto.setValorRevenda(result.getDouble("valor_revenda"));

                produtos.add(produto);
            }

        return produtos;
    }

    public void deleteProduto(int codProduto) throws SQLException{
        String query = "DELETE FROM produtos WHERE cod_produto = " + codProduto;

        PreparedStatement statement =  connection.prepareStatement(query);
        statement.execute();
    }

    public void insertHistorico(Venda venda) throws SQLException{

        PreparedStatement statement = connection.prepareStatement("INSERT INTO vendas" +
                "(cod_produto, nome, tipo, descricao, valor, data_venda)"
                + "VALUES(?,?,?,?,?,?)");

        statement.setInt(1, venda.getCodProduto());
        statement.setString(2, venda.getNomeProduto());
        statement.setString(3, venda.getTipo());

        statement.setString(4, venda.getDescricao());
        statement.setDouble(5, venda.getPreco());
        statement.setDate(6, Helpers.parseSQLDate(String.valueOf(venda.getDtVenda())));

        statement.execute();
    }

    public ObservableList<Venda> consultHistorico() throws SQLException, ParseException{

        String queryHistorico = "SELECT * FROM vendas";

        ObservableList<Venda> produtosHistorico = FXCollections.observableArrayList();

        PreparedStatement statement = connection.prepareStatement(queryHistorico);

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            Venda venda = new Venda();
            venda.setCodProduto(result.getInt("cod_produto"));
            venda.setNomeProduto(result.getString("nome"));
            venda.setTipo(result.getString("tipo"));
            venda.setDescricao(result.getString("descricao"));
            venda.setPreco(result.getDouble("valor"));
            venda.setDtVenda(Helpers.parseStringDate(result.getDate("data_venda")));

            produtosHistorico.add(venda);
        }

        return produtosHistorico;
    }

    /**
     * Adiciona mais 1 ao campo venda
     * Remove 1 da quantidade do produto
     */
    public void updateVendas(Produto produto) throws SQLException{

        String query  = "UPDATE produtos SET vendas = vendas + 1 WHERE cod_produto = '" +produto.getCodProduto()+ "'";
        String query2 = "UPDATE produtos SET quantidade = quantidade - 1 WHERE cod_produto = '" +produto.getCodProduto()+ "'";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.addBatch(query2);

        statement.executeBatch();

        System.out.println("Venda do produto " + produto.getNome() + " atualizada: "+statement.executeUpdate());

    }

    /**
     *Apaga o histórico de vendas
     */
    public void dropHistorico() throws SQLException{
        String query   = "TRUNCATE TABLE vendas";
        String query2  = "UPDATE produtos SET vendas = 0";



        PreparedStatement statement = connection.prepareStatement(query);

        statement.addBatch(query);
        statement.addBatch(query2);

        statement.executeBatch();
    }

    /**
     * @return quantidade de vendas somadas de todos os produtos
     */
    public int produtosVendidos() throws SQLException{
        String query = "SELECT SUM(vendas) FROM produtos";


        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();

        result.next();

        return result.getInt(1);

    }

    /**
     * @return  valor de todos os produtos somados
     */
    public double valorGasto() throws SQLException{

        //a soma dos valores vezes o estoque registrado
        String query = "SELECT SUM(valor * quantidade) FROM produtos";

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();

        result.next();

        return result.getDouble(1);
    }

    /**
     * @return soma dos produtos em estoque
     */
    public int estoqueTotalRegistrado() throws SQLException{
        String query = "SELECT SUM(quantidade) FROM produtos";

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();

        result.next();

        return result.getInt(1);
    }

    /**Lucro previsto com base no estoque
     * @return a soma das diferenças entre os valores de revenda dos produtos em estoque e o valor de compra deles.
     */
    public double lucroTotalPrevistoProdutos() throws SQLException{
        String query = "SELECT SUM((valor_revenda * quantidade)  - (valor * quantidade))  FROM produtos";

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();

        result.next();

        return result.getInt(1);
    }

    /** Lucro até o momento baseado nos produtos já vendidos
     *  @return a diferença entre o valor de revenda e o valor de compra multiplicado pelas vendas.
     */
    public double lucroAteAgoraProduto() throws SQLException{
        String query = "SELECT SUM((valor_revenda - valor) * vendas)  FROM produtos";

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();

        result.next();

        return result.getInt(1);
    }

    /**Valor arreacadado com base no estoque
     * @return soma dos valores de revenda dos produtos em estoque
     */
    public double valorArrecadado() throws SQLException{
        String query = "SELECT SUM((valor_revenda * vendas))  FROM produtos";

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();

        result.next();

        return result.getInt(1);
    }

}




