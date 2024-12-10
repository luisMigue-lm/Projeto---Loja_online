package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import jdbc.ConexaoMySQL;
import model.Pedidos;

public class PedidoDao {
    public static boolean cadastrar(Pedidos pedidos) {
        String sql = "INSERT INTO pedidos (quantdVendida, precoVenda, idCliente, idProduto, idFormaPagmnt)";
        sql = "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, pedidos.getQuantdVendida());
            ps.setDouble(2, pedidos.getPrecoVenda());
            ps.setInt(3, pedidos.getCliente().getIdCliente());
            ps.setInt(4, pedidos.getProduto().getIdProduto());
            ps.setInt(5, pedidos.getPagamento().getIdFormaPagmnt());

            return (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO INSERIR: " + e.getMessage(), "ERRO", 3);
            return false;
        }
    }
}
