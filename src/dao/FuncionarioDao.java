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
import model.Funcionario;

public class FuncionarioDao {

    public static boolean cadastrar(Funcionario funcionario) {

        String sql;
        sql = "INSERT INTO funcionario (nomeFuncionario, senha, cpfFuncionario, dtNascimento, emailFuncionario)";
        sql += "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, funcionario.getNomeFuncionario());
            ps.setString(2, funcionario.getSenha());
            ps.setString(3, funcionario.getCpfFuncionario());
            ps.setObject(4, funcionario.getDtNascimento());
            ps.setString(5, funcionario.getEmailFuncionario());

            return (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO INSERIR: " + e.getMessage(), "ERRO", 3);
            return false;
        }
    }

    public static List<Funcionario> listar(String nome) {
        List<Funcionario> lista = new ArrayList<Funcionario>();
        String sql = "SELECT * FROM funcionario WHERE nomeFuncionario LIKE ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();

                funcionario.setIdFuncionario(rs.getInt("idFuncionario"));
                funcionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setCpfFuncionario(rs.getString("cpfFuncionario"));
                funcionario.setDtNascimento(rs.getObject("dtNascimento", LocalDate.class));
                funcionario.setEmailFuncionario(rs.getString("emailFuncionario"));

                lista.add(funcionario);

            }

            return lista;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO LISTAR: " + e.getMessage(), "ERRO!", 3);
            return null;

        }

    }

    public static boolean atualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nomeFuncionario = ?, senha = ?, cpfFuncionario = ?, dtNascimento = ?, emailFuncionario = ? ";
        sql += "WHERE idFuncionario = ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, funcionario.getNomeFuncionario());
            ps.setString(2, funcionario.getSenha());
            ps.setString(3, funcionario.getCpfFuncionario());
            ps.setObject(4, funcionario.getDtNascimento());
            ps.setString(5, funcionario.getEmailFuncionario());
            ps.setInt(6, funcionario.getIdFuncionario());

            return (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO ATUALIZAR: " + e.getMessage(), "ERRO!", 3);
            return false;

        }
    }

    public static boolean deletar(Funcionario funcionario) {
        String sql = "DELETE FROM funcionario WHERE idFuncionario = ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, funcionario.getIdFuncionario());

            return (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO DELETAR: " + e.getMessage(), "ERRO!", 3);
            return false;

        }
    }

    public static Funcionario login(String cpf, String senha) {
        String sql = "SELECT * FROM funcionario WHERE cpfFuncionario = ? AND senha = ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cpf);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Funcionario(rs.getInt("idFuncionario"),
                rs.getString("nomeFuncionario"),
                rs.getString("senha"),
                rs.getString("cpfFuncionario"),
                rs.getObject("dtNascimento", LocalDate.class), 
                rs.getString("dtNascimento"));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO REALIZAR LOGIN: " + e.getMessage(), "ERRO!", 3);

        }

        return null;

    }
}
