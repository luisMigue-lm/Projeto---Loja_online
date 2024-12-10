package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import jdbc.ConexaoMySQL;
import model.Produto;

public class ProdutosDao {

    public static boolean cadastrar(Produto produto) {
    String sql;
        sql = "INSERT INTO produtos (nomeProduto, dtValidade, fornecedor, descricao, preco)";
        sql += "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, produto.getNomeProduto());
            ps.setObject(2, produto.getDtValidade());
            ps.setString(3, produto.getFornecedor());
            ps.setString(4, produto.getDescricao());
            ps.setDouble(5, produto.getPreco());

            return (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO INSERIR: " + e.getMessage(), "ERRO", 3);
            return false;
        }
    }

    public static List<Produto> listar(String nome){
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE nomeProduto LIKE ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "%"+ nome +"%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();

                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setNomeProduto(rs.getString("nomeProduto"));
                produto.setDtValidade(rs.getObject("dtValidade", LocalDate.class));
                produto.setFornecedor(rs.getString("fornecedor"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));

                lista.add(produto);
            }
            
            return lista;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO LISTAR: " + e.getMessage(), "ERRO!", 3);
            return null;

        }
    }

    public static boolean atualizar(Produto produto){
        String sql ="UPDATE produtos SET nomeProduto = ?, dtValidade = ?, fornecedor = ?, descricao = ?, preco = ?";
        sql +="WHERE idProduto = ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, produto.getNomeProduto());
            ps.setObject(2, produto.getDtValidade());
            ps.setString(3, produto.getFornecedor());
            ps.setString(4, produto.getDescricao());
            ps.setDouble(5, produto.getPreco());
            ps.setInt(6, produto.getIdProduto());

            return (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO ATUALIZAR: " + e.getMessage(), "ERRO!", 3);
            return false;

        }

    }

    public static boolean deletar(Produto produto){
        String sql = "DELETE FROM produtos WHERE idProduto = ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, produto.getIdProduto());

            return (ps.executeUpdate() > 0);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO DELETAR: " + e.getMessage(), "ERRO!", 3);
            return false;

        }
    }

    
}
