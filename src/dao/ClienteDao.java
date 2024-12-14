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
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("ERRO!");
            alerta.setHeaderText("ERRO!");
            alerta.setContentText("ERRO AO INSERIR: " + e.getMessage());
            alerta.show();
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
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("ERRO!");
            alerta.setHeaderText("ERRO!");
            alerta.setContentText("ERRO AO LISTAR: " + e.getMessage());
            alerta.show();
            return null;

        }

    }

    public static boolean atualizar(Cliente cliente) {
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
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("ERRO!");
            alerta.setHeaderText("ERRO!");
            alerta.setContentText("ERRO AO ATUALIZAR: " + e.getMessage());
            alerta.show();
            return false;

        }
    }

    public static boolean deletar(Cliente cliente) {
        String sql = "DELETE FROM cliente WHERE idCliente = ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, cliente.getIdCliente());

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
