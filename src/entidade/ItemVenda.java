package entidade;

import java.math.BigDecimal;

public class ItemVenda {
    private int itemVendaId;
    private int vendaId;
    private int produtoId;
    private int quantidade;
    private BigDecimal precoUnitario;

    // Getters e Setters
    public int getItemVendaId() {
        return itemVendaId;
    }
    public void setItemVendaId(int itemVendaId) {
        this.itemVendaId = itemVendaId;
    }
    public int getVendaId() {
        return vendaId;
    }
    public void setVendaId(int vendaId) {
        this.vendaId = vendaId;
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
    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }
    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
