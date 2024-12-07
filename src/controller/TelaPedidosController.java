package controller;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaPedidosController {

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
    private Button btnPesquisarPedidos;

    @FXML
    private Button btnVoltar;

    @FXML
    private ComboBox<?> cbIdCliente;

    @FXML
    private ComboBox<?> cbIdFormaPagamento;

    @FXML
    private ComboBox<?> cbIdProduto;

    @FXML
    private TableColumn<?, ?> colIdCliente;

    @FXML
    private TableColumn<?, ?> colIdPagamento;

    @FXML
    private TableColumn<?, ?> colIdPedidos;

    @FXML
    private TableColumn<?, ?> colIdProduto;

    @FXML
    private TableColumn<?, ?> colPreco;

    @FXML
    private TableColumn<?, ?> colQuantidade;

    @FXML
    private TextField tfPesquisa;

    @FXML
    private TextField tfPrecoVenda;

    @FXML
    private TextField tfQuantdVendida;

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
    void btnPesquisarOnClick(ActionEvent event) {

    }

    @FXML
    void btnPesquisarPedidosOnClick(ActionEvent event) {

    }

    @FXML
    void btnVoltarOnClick(ActionEvent event) throws IOException{
        URL url = getClass().getResource("/view/TelaPrincipal.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Morcegão | Loja Online");
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();
        
        fecharTela();
    }

    @FXML
    void cbIdClienteOnClick(ActionEvent event) {

    }

    @FXML
    void cbIdFormaPagamentoOnClick(ActionEvent event) {

    }

    @FXML
    void cbIdProdutoOnClick(ActionEvent event) {
        
    }

}
