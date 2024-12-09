package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import jdbc.ConexaoMySQL;
import model.Cliente;

public class ClienteDao {

    public static boolean cadastrar(Cliente cliente) {
        String sql;
        sql = "INSERT INTO cliente (nomeCliente, genero, cpfCliente, enderecoCliente, telefoneCliente)";
        sql += "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cliente.getNomeCliente());
            ps.setString(2, cliente.getGenero());
            ps.setString(3, cliente.getCpfCliente());
            ps.setString(4, cliente.getEnderecoClietne());
            ps.setString(5, cliente.getTelefoneCliente());

            return (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO INSERIR: " + e.getMessage(), "ERRO", 3);
            return false;
        }

    }

    public static List<Cliente> listar(String nome) {
        List<Cliente> lista = new ArrayList<Cliente>();
        String sql = "SELECT * FROM cliente WHERE nomeCliente LIKE ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNomeCliente(rs.getString("nomeCliente"));
                cliente.setGenero(rs.getString("genero"));
                cliente.setCpfCliente(rs.getString("cpfCliente"));
                cliente.setEnderecoClietne(rs.getString("enderecoCliente"));
                cliente.setTelefoneCliente(rs.getString("telefoneCliente"));

                lista.add(cliente);

            }

            return lista;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO LISTAR: " + e.getMessage(), "ERRO!", 3);
            return null;

        }

    }

    public static boolean atualizar(Cliente cliente){
        String sql = "UPDATE cliente SET nomeCliente = ?, genero = ?, cpfCliente = ?, enderecoCliente = ?, telefoneCliente = ? ";
        sql += "WHERE idCliente = ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cliente.getNomeCliente());
            ps.setString(2, cliente.getGenero());
            ps.setString(3, cliente.getCpfCliente());
            ps.setObject(4, cliente.getEnderecoClietne());
            ps.setString(5, cliente.getTelefoneCliente());
            ps.setInt(6, cliente.getIdCliente());

            return (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO ATUALIZAR: " + e.getMessage(), "ERRO!", 3);
            return false;

        }
    }

    public static boolean deletar(Cliente cliente){
        String sql = "DELETE FROM cliente WHERE idCliente = ?"; 

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, cliente.getIdCliente());

            return (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO DELETAR: " + e.getMessage(), "ERRO!", 3);
            return false;
            
        }
    }
}
