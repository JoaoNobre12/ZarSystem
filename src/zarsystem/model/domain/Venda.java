package zarsystem.model.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe de domínio das vendas
 * Created by joaov on 02/12/2016.
 */
public class Venda {
    private int codProduto;
    private String dtVenda;
    private Produto produto;
    private String tipo;
    private String descricao;
    private String nomeProduto;
    private String descricaoProduto;
    private double preco;

    public Venda() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        dtVenda = simpleDateFormat.format(date);

    }

    public Venda(Produto produto) {
        this.produto     = produto;
        codProduto       = produto.getCodProduto();
        nomeProduto      = produto.getNome();
        descricaoProduto = produto.getDescricao();
        preco            = produto.getValorRevenda();
        tipo             = produto.getTipo();
        descricao        = produto.getDescricao();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        dtVenda = simpleDateFormat.format(date);

    }

    public int getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDtVenda() {
        return dtVenda;
    }

    public void setDtVenda(String dtVenda) {
        this.dtVenda = dtVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
