package testesCRUD;

import dao.PagamentoDAO;
import entidade.Pagamento;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import conexao.ConexaoBD;

public class PagamentoDAOTeste {
    public static void main(String[] args) {
        try (Connection connection = ConexaoBD.getConnection()) {
            PagamentoDAO pagamentoDAO = new PagamentoDAO(connection);

            // Teste de criação
            Pagamento novoPagamento = new Pagamento();
            novoPagamento.setVendaId(1);
            novoPagamento.setValor(BigDecimal.valueOf(2550.00));
            novoPagamento.setMetodoPagamento("Cartão de Crédito");
            novoPagamento.setStatus("Concluído");
            pagamentoDAO.create(novoPagamento);
            System.out.println("Pagamento criado: " + novoPagamento);

            // Teste de leitura
            Pagamento pagamentoLido = pagamentoDAO.read(novoPagamento.getPagamentoId());
            System.out.println("Pagamento lido: " + pagamentoLido);

            // Teste de atualização
            pagamentoLido.setStatus("Cancelado");
            pagamentoDAO.update(pagamentoLido);
            Pagamento pagamentoAtualizado = pagamentoDAO.read(pagamentoLido.getPagamentoId());
            System.out.println("Pagamento atualizado: " + pagamentoAtualizado);

            // Teste de exclusão
            pagamentoDAO.delete(pagamentoAtualizado.getPagamentoId());
            Pagamento pagamentoDeletado = pagamentoDAO.read(pagamentoAtualizado.getPagamentoId());
            System.out.println("Pagamento deletado: " + (pagamentoDeletado == null ? "sucesso" : "falha"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
