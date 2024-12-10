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
import model.Pagamento;


public class PagamentoDao {
    
    public static boolean cadastrar(Pagamento pagamento){
        String sql = "INSERT INTO pagamento (meioPagmnt, taxaJuros, quantParcelas, data, descricao)";
        sql += "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, pagamento.getMeioPagmnt());
            ps.setDouble(2, pagamento.getTaxaJuros());
            ps.setInt(3, pagamento.getQuantParcelas());
            ps.setObject(4, pagamento.getData());
            ps.setString(5, pagamento.getDescricao());

            return(ps.executeUpdate() > 0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO INSERIR: " + e.getMessage(), "ERRO", 3);
            return false;
        }
    }

    public static List<Pagamento> listar(String nome){
        List<Pagamento> lista = new ArrayList<Pagamento>();
        String sql = "SELECT * FROM pagamento WHERE meioPagmnt LIKE ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "%"+ nome +"%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pagamento pagamento = new Pagamento();

                pagamento.setIdFormaPagmnt(rs.getInt("idFormaPagmnt"));
                pagamento.setMeioPagmnt(rs.getString("meioPagmnt"));
                pagamento.setTaxaJuros(rs.getDouble("taxaJuros"));
                pagamento.setQuantParcelas(rs.getInt("quantParcelas"));
                pagamento.setData(rs.getObject("data", LocalDate.class));
                pagamento.setDescricao(rs.getString("descricao"));

                lista.add(pagamento);

            }

            return lista;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO LISTAR: " + e.getMessage(), "ERRO!", 3);
            return null;

        }
    }

    public static boolean atualizar(Pagamento pagamento){
        String sql = "UPDATE pagamento SET meioPagmnt = ?, taxaJuros = ?, quantParcelas = ?, data = ?, descricao = ?";
        sql += "WHERE idFormaPagmnt = ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, pagamento.getMeioPagmnt());
            ps.setDouble(2, pagamento.getTaxaJuros());
            ps.setInt(3, pagamento.getQuantParcelas());
            ps.setObject(4, pagamento.getData());
            ps.setString(5, pagamento.getDescricao());
            ps.setInt(6, pagamento.getIdFormaPagmnt());

            return (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO ATUALIZAR: " + e.getMessage(), "ERRO!", 3);
            return false;

        }
    }

    public static boolean deletar(Pagamento pagamento){
        String sql = "DELETE FROM pagamento WHERE idFormaPagmnt = ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, pagamento.getIdFormaPagmnt());
            
            return (ps.executeUpdate() > 0);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO DELETAR: " + e.getMessage(), "ERRO!", 3);
            return false;

        }
    }
}
