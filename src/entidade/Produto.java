package entidade;

import java.math.BigDecimal;

public class Produto {
    private int produtoId;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;
    private int categoriaId;
    private int fornecedorId;

    // Getters e Setters
    public int getProdutoId() {
        return produtoId;
    }
    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double bigDecimal) {
        this.preco = bigDecimal;
    }
    public int getEstoque() {
        return estoque;
    }
    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
    public int getCategoriaId() {
        return categoriaId;
    }
    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }
    public int getFornecedorId() {
        return fornecedorId;
    }
    public void setFornecedorId(int fornecedorId) {
        this.fornecedorId = fornecedorId;
    }
	public void setPreco(BigDecimal valueOf) {
		
		
	}
}
