package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("ERRO!");
            alerta.setHeaderText("ERRO!");
            alerta.setContentText("ERRO AO INSERIR: " + e.getMessage());
            alerta.show();
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
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("ERRO!");
            alerta.setHeaderText("ERRO!");
            alerta.setContentText("ERRO AO LISTAR: " + e.getMessage());
            alerta.show();
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
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("ERRO!");
            alerta.setHeaderText("ERRO!");
            alerta.setContentText("ERRO AO ATUALIZAR: " + e.getMessage());
            alerta.show();
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
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("ERRO!");
            alerta.setHeaderText("ERRO!");
            alerta.setContentText("ERRO AO DELETAR: " + e.getMessage());
            alerta.show();
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
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("ERRO!");
            alerta.setHeaderText("ERRO!");
            alerta.setContentText("ERRO AO REALIZAR LOGIN: " + e.getMessage());
            alerta.show();

        }

        return null;

    }
}
