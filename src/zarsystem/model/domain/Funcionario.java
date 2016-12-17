package zarsystem.model.domain;

/**
 * Domínio de funcionários
 * Created by joao on 14/11/16.
 */
public class Funcionario {
    private int codFuncionario;
    private String objetivo;
    private double salario;
    private String nome;
    private String formacao;
    private String sexo;
    private String rg;
    private String cpf;

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getCodFuncionario() {
        return codFuncionario;
    }

    public void setCodFuncionario(int codFuncionario) {
        this.codFuncionario = codFuncionario;
    }
}
