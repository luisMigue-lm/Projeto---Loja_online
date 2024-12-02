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

public class TelaPrincipalController {

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnPagamentos;

    @FXML
    private Button btnPedidos;

    @FXML
    private Button btnProdutos;

    @FXML
    private Button btnSair;
@FXML
    private void fecharTela() {
        Stage primaryStage = (Stage) btnSair.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void btnClientesOnClick(ActionEvent event) throws IOException{
        URL url = getClass().getResource("/view/TelaCliente.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Tela Clientes");
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();

        fecharTela();
    }

    @FXML
    void btnPagamentosOnClick(ActionEvent event) throws IOException{
        URL url = getClass().getResource("/view/TelaPagamento.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Tela Clientes");
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();

        fecharTela();
    }

    @FXML
    void btnPedidosOnClick(ActionEvent event) throws IOException{
        URL url = getClass().getResource("/view/TelaPedidos.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Tela Clientes");
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();

        fecharTela();
    }

    @FXML
    void btnProdutosOnClick(ActionEvent event) throws IOException{
        URL url = getClass().getResource("/view/TelaProdutos.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Tela Clientes");
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();

        fecharTela();
    }

    @FXML
    void btnSairOnClick(ActionEvent event) throws IOException{
        URL url = getClass().getResource("/view/TelaLogin.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Tela Clientes");
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();

        fecharTela();
    }

}
