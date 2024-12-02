package controller;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaFuncionarioController {

    @FXML
    private AnchorPane apCadastro;

    @FXML
    private AnchorPane apPesquisa;

    @FXML
    private BorderPane bPane;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCadastrarCliente;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnOpcoes;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnPesquisarFuncionario;

    @FXML
    private Button btnVoltar;

    @FXML
    private TableColumn<?, ?> colCPF;

    @FXML
    private TableColumn<?, ?> colDtNascmt;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colIdFuncionario;

    @FXML
    private TableColumn<?, ?> colNome;

    @FXML
    private TableColumn<?, ?> colSenha;

    @FXML
    private DatePicker dpDtNascimento;

    @FXML
    private TextField tfCPF;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfPesquisa;

    @FXML
    private TextField tfSenha;
    
    @FXML
    private void fecharTela() {
        Stage primaryStage = (Stage) btnVoltar.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void btnCadastrarClienteOnClick(ActionEvent event) {

    }

    @FXML
    void btnCadastrarOnClick(ActionEvent event) {

    }

    @FXML
    void btnDeletarOnClick(ActionEvent event) {

    }

    @FXML
    void btnEditarOnClick(ActionEvent event) {

    }

    @FXML
    void btnLimparOnclick(ActionEvent event) {

    }

    @FXML
    void btnOpcoesOnClick(ActionEvent event) {

    }

    @FXML
    void btnPesquisarFuncionariosOnClick(ActionEvent event) {

    }

    @FXML
    void btnPesquisarOnClick(ActionEvent event) {

    }

    @FXML
    void btnVoltarOnClick(ActionEvent event) throws IOException{
        URL url = getClass().getResource("/view/TelaLogin.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Tela de Login");
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();

        fecharTela();
    }

}
