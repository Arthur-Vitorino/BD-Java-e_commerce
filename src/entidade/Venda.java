package entidade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Venda {
    private int vendaId;
    private int clienteId;
    private LocalDateTime dataVenda;
    private int statusPedidoId;
    private BigDecimal valorTotal;

    // Getters e Setters
    public int getVendaId() {
        return vendaId;
    }
    public void setVendaId(int vendaId) {
        this.vendaId = vendaId;
    }
    public int getClienteId() {
        return clienteId;
    }
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    public LocalDateTime getDataVenda() {
        return dataVenda;
    }
    public void setDataVenda(LocalDateTime localDateTime) {
        this.dataVenda = localDateTime;
    }
    public int getStatusPedidoId() {
        return statusPedidoId;
    }
    public void setStatusPedidoId(int statusPedidoId) {
        this.statusPedidoId = statusPedidoId;
    }
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
