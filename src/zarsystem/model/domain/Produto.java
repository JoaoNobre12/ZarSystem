package zarsystem.model.domain;

/**
 * Domínio de produtos
 * Created by joaov on 25/11/2016.
 */
public class Produto {
    private int codProduto;
    private String nome;
    private String tipo;
    private String descricao;
    private double valor;
    private double valorRevenda;
    private int quantidade;

    public int getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValorRevenda() {
        return valorRevenda;
    }

    public void setValorRevenda(double valorRevenda) {
        this.valorRevenda = valorRevenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
