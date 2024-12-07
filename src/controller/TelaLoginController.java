package controller;

import java.io.IOException;
import java.net.URL;

import javax.swing.JOptionPane;

import dao.FuncionarioDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Funcionario;

public class TelaLoginController {

    @FXML
    private Button btnCadastro;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private TextField tfCPF;

    @FXML
    private void fecharTela() {
        Stage primaryStage = (Stage) btnLogin.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void btnCadastroOnClick(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/view/TelaFuncionario.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Funcionários | Cadastrar, visualizar, modificar e deletar");
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();

        fecharTela();
    }

    @FXML
    void btnLoginOnClick(ActionEvent event) throws IOException {
        String cpf = tfCPF.getText().trim();
        String senha = pfSenha.getText().trim();

        Funcionario funcionario = FuncionarioDao.login(cpf, senha);

        if (funcionario != null) {

            URL url = getClass().getResource("/view/TelaPrincipal.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            TelaPrincipalController controller = loader.getController();
            controller.setFuncionarioLogado(funcionario);

            Stage stgTelaPrincipal = new Stage();
            stgTelaPrincipal.setTitle("Morcegão | Loja Online");
            stgTelaPrincipal.setScene(new Scene(root));
            stgTelaPrincipal.show();

            fecharTela();

        } else {
            JOptionPane.showMessageDialog(null, "CPF ou senha incorretos!", "ERRO!", JOptionPane.ERROR_MESSAGE);

        }

    }

}
