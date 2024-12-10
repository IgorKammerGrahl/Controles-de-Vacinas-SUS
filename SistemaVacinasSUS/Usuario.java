package SistemaVacinasSUS;

import java.util.Date;

public class Usuario {
	public enum PerfilUsuario { CIDADAO, AGENTE_DE_SAUDE }

	private int id;
	private String nome;
	private String cpf;
	private Date dataNascimento;
	private String email;
	private String senha;
	private PerfilUsuario perfil;


	public Usuario(int id, String nome, String cpf, Date dataNascimento, String email, String senha, PerfilUsuario perfil) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.email = email;
		this.senha = senha;
		this.perfil = perfil;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean autenticar(String email, String senha) {
		if (email == null || senha == null) {
			throw new IllegalArgumentException("Email e senha não podem ser nulos");
		}
		return this.email.equals(email) && this.senha.equals(senha);
	}

	public static void testarAutenticacao(Usuario usuario, String email, String senha) {
		try {
			boolean autenticado = usuario.autenticar(email, senha);
			if (autenticado) {
				System.out.println("- Usuário autenticado com sucesso: " + usuario.getNome());
			} else {
				System.out.println("- Falha na autenticação para o usuário: " + usuario.getNome());
			}
		} catch (IllegalArgumentException e) {
			System.out.println("- Erro ao tentar autenticar: " + e.getMessage());
		}
	}


	public PerfilUsuario getPerfil() {
		return perfil;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento
				+ ", email=" + email + ", senha=" + senha + "]";
	}
}
