package entidade;

public class Fornecedor {
    private int fornecedorId;
    private String nome;
    private String contato;
    private String telefone;
    private String email;
    private String endereco;

    // Getters e Setters
    public int getFornecedorId() {
        return fornecedorId;
    }
    public void setFornecedorId(int fornecedorId) {
        this.fornecedorId = fornecedorId;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getContato() {
        return contato;
    }
    public void setContato(String contato) {
        this.contato = contato;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
