package controller;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaFuncionarioController {

    @FXML
    private BorderPane bPane;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnOpcoes;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnVoltar;

    @FXML
    private void fecharTela() {
        Stage primaryStage = (Stage) btnVoltar.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void btnCadastrarOnClick(ActionEvent event) {

    }

    @FXML
    void btnDeletarOnClick(ActionEvent event) {

    }

    @FXML
    void btnOpcoesOnClick(ActionEvent event) {

    }

    @FXML
    void btnPesquisarOnClick(ActionEvent event) {

    }

    @FXML
    void btnVoltarOnClick(ActionEvent event) throws IOException{
        URL url = getClass().getResource("/view/TelaLogin.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Tela Login");
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();
        
        
        fecharTela();
    }

}
