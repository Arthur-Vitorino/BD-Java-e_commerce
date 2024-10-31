package entidade;

public class Carrinho {
    private int carrinhoId;
    private int clienteId;
    private int produtoId;
    private int quantidade;

    // Getters e Setters
    public int getCarrinhoId() {
        return carrinhoId;
    }
    public void setCarrinhoId(int carrinhoId) {
        this.carrinhoId = carrinhoId;
    }
    public int getClienteId() {
        return clienteId;
    }
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    public int getProdutoId() {
        return produtoId;
    }
    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
