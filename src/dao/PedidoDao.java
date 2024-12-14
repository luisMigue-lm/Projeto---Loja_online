package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import jdbc.ConexaoMySQL;
import model.Cliente;
import model.Pagamento;
import model.Pedidos;
import model.Produto;

public class PedidoDao {
    public static boolean cadastrar(Pedidos pedidos) {
        String sql = "INSERT INTO pedidos (quantdVendida, precoVenda, idCliente, idProduto, idFormaPagmnt)";
        sql += "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, pedidos.getQuantdVendida());
            ps.setDouble(2, pedidos.getPrecoVenda());
            ps.setInt(3, pedidos.getCliente().getIdCliente());
            ps.setInt(4, pedidos.getProduto().getIdProduto());
            ps.setInt(5, pedidos.getPagamento().getIdFormaPagmnt());

            return (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("ERRO!");
            alerta.setHeaderText("ERRO!");
            alerta.setContentText("ERRO AO INSERIR: " + e.getMessage());
            alerta.show();
            return false;
        }
    }

    public static List<Pedidos> listar(String nome){
        List<Pedidos> lista = new ArrayList<Pedidos>();
        String sql = """
        SELECT p.idItensPedidos, p.quantdVendida, p.precoVenda, p.idCliente, p.idProduto, p.idFormaPagmnt, 
               c.nomeCliente, pr.nomeProduto, pg.tipoPagamento
        FROM pedidos p
        JOIN cliente c ON p.idCliente = c.idCliente
        JOIN produtos pr ON p.idProduto = pr.idProduto
        JOIN pagamento pg ON p.idFormaPagmnt = pg.idFormaPagmnt
        WHERE c.nomeCliente LIKE ?
    """;

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "%"+ nome +"%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pedidos pedidos = new Pedidos();

                pedidos.setIdItensPedidos(rs.getInt("idItensPedidos"));
                pedidos.setQuantdVendida(rs.getInt("quantdVendida"));
                pedidos.setPrecoVenda(rs.getDouble("precoVenda"));

                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNomeCliente(rs.getString("nomeCliente"));
                pedidos.setCliente(cliente);

                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setNomeProduto(rs.getString("nomeProduto"));
                pedidos.setProduto(produto);

                Pagamento pagamento = new Pagamento();
                pagamento.setIdFormaPagmnt(rs.getInt("idFormaPagmnt"));
                pagamento.setMeioPagmnt(rs.getString("meioPagmnt"));
                pedidos.setPagamento(pagamento);

                lista.add(pedidos);

            }

            return lista;
            
        } catch (SQLException e) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("ERRO!");
            alerta.setHeaderText("ERRO!");
            alerta.setContentText("ERRO AO LISTAR: " + e.getMessage());
            alerta.show();
            return null;
        }
    }

    public static boolean atualizar(Pedidos pedidos){
        String sql = "UPDATE pedidos SET quantdVendida = ?, precoVenda = ?, idCliente = ?, idProduto = ?, idFormaPagmnt = ?";
        sql += "WHERE idItensPedidos = ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, pedidos.getQuantdVendida());
            ps.setDouble(2, pedidos.getPrecoVenda());
            ps.setInt(3, pedidos.getCliente().getIdCliente());
            ps.setInt(4, pedidos.getProduto().getIdProduto());
            ps.setInt(5, pedidos.getPagamento().getIdFormaPagmnt());
            ps.setInt(6, pedidos.getIdItensPedidos());

            return (ps.executeUpdate() > 0);

            
        } catch (SQLException e) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("ERRO!");
            alerta.setHeaderText("ERRO!");
            alerta.setContentText("ERRO AO ATUALIZAR: " + e.getMessage());
            alerta.show();
            return false;
        }
    }

    public static boolean deletar(Pedidos pedidos){
        String sql = "DELETE FROM pedidos WHERE idItensPedidos = ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, pedidos.getIdItensPedidos());

            return (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("ERRO!");
            alerta.setHeaderText("ERRO!");
            alerta.setContentText("ERRO AO DELETAR: " + e.getMessage());
            alerta.show();
            return false;
        }
    }
}
