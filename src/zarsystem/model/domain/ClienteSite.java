package zarsystem.model.domain;

/**
 * Classe de dompinio dos clientes cadastrados pelo site
 * Created by joaov on 10/12/2016.
 */
public class ClienteSite {
    private int codCliente;
    private String plano;
    private String nome;
    private String duvida;
    private String email;

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDuvida() {
        return duvida;
    }

    public void setDuvida(String duvida) {
        this.duvida = duvida;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
