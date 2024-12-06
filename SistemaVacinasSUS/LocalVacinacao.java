package SistemaVacinasSUS;

public class LocalVacinacao {
	private int id;
	private String nome;
	private String endereco;
	private String telefone;

	public LocalVacinacao(int id, String nome, String endereco, String telefone) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String obterInformacoes() {
		return String.format("Local: %s, Endereço: %s, Telefone: %s", nome, endereco, telefone);
	}

	@Override
	public String toString() {
		return "LocalVacinacao [id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", telefone=" + telefone
				+ "]";
	}
}
