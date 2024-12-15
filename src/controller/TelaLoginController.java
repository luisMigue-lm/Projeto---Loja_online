package controller;

import java.io.IOException;
import java.net.URL;

import dao.FuncionarioDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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

    private void alerta(AlertType tipo, String titulo, String cabecalho, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(mensagem);
        alerta.show();
    }

    @FXML
    void btnCadastroOnClick(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/view/TelaFuncionario.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Funcionários | Cadastrar, visualizar, modificar e deletar");
        stgTelaPrincipal.getIcons().add(new Image("file:src/resources/imgs/Logo - Laranja.png"));
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();

        fecharTela();
    }

    @FXML
    void btnLoginOnClick(ActionEvent event) throws IOException {
        try {
            String cpf = tfCPF.getText().trim().replaceAll("[^\\d]", "");
            String senha = pfSenha.getText().trim();
    
            if (!cpf.matches("\\d+")) {
                alerta(AlertType.ERROR, "ERRO!", "CPF inválido!", "O CPF só pode conter números.");
                return;
            }
    
            if (cpf.contains(" ")) {
                alerta(AlertType.ERROR, "ERRO!", "CPF inválido!", "O campo CPF não pode conter espaços.");
                return;
            }
    
            if (cpf.isEmpty()) {
                alerta(AlertType.ERROR, "ERRO!", "CPF inválido!", "O campo CPF não pode estar vázio.");
                return;
            }

            if (cpf.length() != 11) {
                alerta(AlertType.ERROR, "ERRO!", "CPF inválido!", "O campo CPF precisa ter 11 ou 14 digitos.");
                return;
            }

            if (cpf.length() == 11) {
                cpf = cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
            }
            
            if (!senha.matches("\\d+")) {
                alerta(AlertType.ERROR, "ERRO!", "Senha Inválida!", "A senha deve conter apenas números.");
                return;
            }
    
            if (senha.isEmpty()) {
                alerta(AlertType.ERROR, "ERRO!", "Senha inválida!", "O campo Senha não pode estar vázio.");
                return;
            }
    
            if (senha.length() < 4) {
                alerta(AlertType.ERROR, "ERRO!", "Senha inválida!", "A senha tem que possuir no mínino 4 caracteres numéricos.");
                return;
            }
    
            Funcionario funcionario = FuncionarioDao.login(cpf, senha);
    
            if (funcionario != null) {
    
                URL url = getClass().getResource("/view/TelaPrincipal.fxml");
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
    
                TelaPrincipalController controller = loader.getController();
                controller.setFuncionarioLogado(funcionario);
    
                Stage stgTelaPrincipal = new Stage();
                stgTelaPrincipal.setTitle("Morcegão | Loja Online");
                stgTelaPrincipal.getIcons().add(new Image("file:src/resources/imgs/Logo - Laranja.png"));
                stgTelaPrincipal.setScene(new Scene(root));
                stgTelaPrincipal.show();
    
                fecharTela();
    
            } else {
                alerta(AlertType.ERROR, "ERRO!", "Encontramos um erro!", "CPF ou senha incorretos!");
                
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
