package controller;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TelaPedidosController {

    @FXML
    private Button btPesquisar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnOpcoes;

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
        URL url = getClass().getResource("/view/TelaPrincipal.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Tela Principal");
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();
        
        
        fecharTela();
    }

}
