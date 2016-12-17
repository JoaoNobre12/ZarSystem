package zarsystem.model.domain;

public class Aluno {
	private int codMatricula;
	private String plano;
    private int diaPagamento;
	private String nome;
	private String rg;
	private String cpf;
	private String dtNasc;
	private String pagamento;
	private String sexo;
	private int codAvFisica;

	public int getCodMatricula() {
		return codMatricula;
	}
	public void setCodMatricula(int codMatricula) {
		this.codMatricula = codMatricula;
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
	public String getDtNasc() {
		return dtNasc;
	}
	public void setDtNasc(String dtNasc) {
		this.dtNasc = dtNasc;
	}

	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}
	public String getPagamento() {
		return pagamento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public int getCodAvFisica() {
		return codAvFisica;
	}
	public void setCodAvFisica(int codAvFisica) {
		this.codAvFisica = codAvFisica;
	}

    public int getDiaPagamento() {
        return diaPagamento;
    }

    public void setDiaPagamento(int diaPagamento) {
        this.diaPagamento = diaPagamento;
    }
}