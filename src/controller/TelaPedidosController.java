package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import dao.ClienteDao;
import dao.PagamentoDao;
import dao.PedidoDao;
import dao.ProdutosDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Cliente;
import model.Pagamento;
import model.Pedidos;
import model.Produto;

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
    private Button btnCadastrarPedido;

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
    private ComboBox<Cliente> cmbCliente;

    @FXML
    private ComboBox<Pagamento> cmbFormaPagamento;

    @FXML
    private ComboBox<Produto> cmbProduto;

    @FXML
    private TableColumn<Cliente, String> colCliente;

    @FXML
    private TableColumn<Pedidos, Integer> colIdPedidos;

    @FXML
    private TableColumn<Pagamento, String> colPagamento;

    @FXML
    private TableColumn<Pedidos, Double> colPreco;

    @FXML
    private TableColumn<Produto, String> colProduto;

    @FXML
    private TableColumn<Pedidos, Integer> colQuantidade;

    @FXML
    private TableView<Pedidos> tbvPedidos;

    @FXML
    private TextField tfPesquisa;

    @FXML
    private TextField tfPrecoVenda;

    @FXML
    private TextField tfQuantdVendida;

    ObservableList<Pedidos> obsPedd;
    ObservableList<Cliente> obsClint;
    ObservableList<Produto> obsProdt;
    ObservableList<Pagamento> obsPagmt;

    @FXML
    private void initialize() {
        colIdPedidos.setCellValueFactory(new PropertyValueFactory<>("idItensPedidos"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantdVendida"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colPagamento.setCellValueFactory(new PropertyValueFactory<>("pagamento"));

        obsPedd = FXCollections.observableArrayList();
        tbvPedidos.setItems(obsPedd);

        obsClint = FXCollections.observableArrayList(ClienteDao.listar(" "));
        cmbCliente.setItems(obsClint);

        obsProdt = FXCollections.observableArrayList(ProdutosDao.listar(" "));
        cmbProduto.setItems(obsProdt);

        obsPagmt = FXCollections.observableArrayList(PagamentoDao.listar(" "));
        cmbFormaPagamento.setItems(obsPagmt);

        tbvPedidos.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            btnEditar.setDisable(newValue == null);
            btnDeletar.setDisable(newValue == null);

        });
    }

    private void alerta(AlertType tipo, String titulo, String cabecalho, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(mensagem);
        alerta.show();
    }

    private void limparCampos() {
        tfPrecoVenda.clear();
        tfQuantdVendida.clear();
        cmbCliente.setValue(null);
        cmbFormaPagamento.setValue(null);
        cmbProduto.setValue(null);
    }

    private void salvarAtualizacao(int idItensPedidos) {
        try {
            int quantdVendida = Integer.parseInt(tfQuantdVendida.getText());
            double precoVenda = Double.parseDouble(tfPrecoVenda.getText());
            Cliente cliente = cmbCliente.getSelectionModel().getSelectedItem();
            Produto produto = cmbProduto.getSelectionModel().getSelectedItem();
            Pagamento pagamento = cmbFormaPagamento.getSelectionModel().getSelectedItem();

            Pedidos pedidosAtualizado = new Pedidos(idItensPedidos, quantdVendida, precoVenda, cliente, produto,
                    pagamento);

            if (PedidoDao.atualizar(pedidosAtualizado)) {
                alerta(AlertType.INFORMATION, "Sucesso!", "É um sucesso!", "Pedido atualizado com sucesso!");

                btnPesquisar.setDisable(false);
                btnOpcoes.setDisable(false);
                btnCadastrar.setDisable(false);
                tbvPedidos.refresh();
                limparCampos();

            } else {
                alerta(AlertType.ERROR, "ERRO!", "Encontremos um erro!", "Erro ao atualizar Pedido!");

            }

            btnCadastrarPedido.setText("Cadastrar");
            btnCadastrarPedido.setOnAction(this::btnCadastrarPedidoOnClick);

        } catch (NumberFormatException e) {
            alerta(AlertType.ERROR, "ERRO!", "Encontremos um erro!", "Erro ao converter os valores!" + e);

        }
    }

    Stage stage;

    public void setStage(Stage stg) {
        preencherDados((Pedidos) stg.getUserData());
    }

    private void preencherDados(Pedidos pedidos) {
        tfQuantdVendida.setText(String.valueOf(pedidos.getQuantdVendida()));
        tfPrecoVenda.setText(String.valueOf(pedidos.getPrecoVenda()));
        cmbCliente.setValue(pedidos.getCliente());
        cmbProduto.setValue(pedidos.getProduto());
        cmbFormaPagamento.setValue(pedidos.getPagamento());

    }

    @FXML
    private void fecharTela() {
        Stage primaryStage = (Stage) btnVoltar.getScene().getWindow();
        primaryStage.close();

    }

    @FXML
    void btnCadastrarOnClick(ActionEvent event) {
        btnDeletar.setDisable(true);

        apCadastro.setVisible(true);
        apPesquisa.setVisible(false);
    }

    @FXML
    void btnCadastrarPedidoOnClick(ActionEvent event) {
        try {
            int quantdVendida = Integer.parseInt(tfQuantdVendida.getText());
            double precoVenda = Double.parseDouble(tfPrecoVenda.getText());
            Cliente cliente = cmbCliente.getSelectionModel().getSelectedItem();
            Produto produto = cmbProduto.getSelectionModel().getSelectedItem();
            Pagamento pagamento = cmbFormaPagamento.getSelectionModel().getSelectedItem();

            Pedidos pedidos = new Pedidos(1, quantdVendida, precoVenda, cliente, produto, pagamento);

            if (PedidoDao.cadastrar(pedidos)) {
                alerta(AlertType.INFORMATION, "Sucesso!", "É um sucesso!", "Pedido cadastrado com sucesso!");
                limparCampos();

            } else {
                alerta(AlertType.ERROR, "ERRO!", "Encontremos um erro!", "Erro ao cadastrar Pedido!");
            }

        } catch (Exception e) {
            alerta(AlertType.ERROR, "ERRO!", "Encontremos um erro!", "Erro ao converter os valores!" + e);
        }
    }

    @FXML
    void btnDeletarOnClick(ActionEvent event) {
        Pedidos pedidoSelecionado = tbvPedidos.getSelectionModel().getSelectedItem();

        if (pedidoSelecionado != null) {
            Alert alertaDeletar = new Alert(AlertType.CONFIRMATION);
            alertaDeletar.setTitle("Confirmação");
            alertaDeletar.setHeaderText("Você tem certeza?");
            alertaDeletar.setContentText("Deseja realmente excluir o pedido do cliente: "
                    + pedidoSelecionado.getCliente().getNomeCliente() + "?");

            Optional<ButtonType> resposta = alertaDeletar.showAndWait();

            if (resposta.isPresent() && resposta.get() == ButtonType.OK) {
                if (PedidoDao.deletar(pedidoSelecionado)) {
                    obsPedd.remove(pedidoSelecionado);
                    tbvPedidos.refresh();

                    alerta(AlertType.INFORMATION, "Sucesso!", "É um sucesso!", "Pedido excluído com sucesso!");

                } else {
                    alerta(AlertType.ERROR, "ERRO!", "OCORREU UM ERRO!", "Encontramos um erro ao realizar a ação!");

                }
            }
        }
    }

    @FXML
    void btnEditarOnClick(ActionEvent event) {
        Pedidos pedidoSelecionado = tbvPedidos.getSelectionModel().getSelectedItem();
        preencherDados(pedidoSelecionado);

        apCadastro.setVisible(true);
        apPesquisa.setVisible(false);

        btnCadastrar.setDisable(true);
        btnPesquisar.setDisable(true);
        btnDeletar.setDisable(true);
        btnOpcoes.setDisable(true);

        btnCadastrarPedido.setText("Atualizar");
        btnCadastrarPedido.setOnAction(e -> salvarAtualizacao(pedidoSelecionado.getIdItensPedidos()));
    }

    @FXML
    void btnLimparOnclick(ActionEvent event) {
        obsPedd.clear();
    }

    @FXML
    void btnOpcoesOnClick(ActionEvent event) {
        if (apCadastro.isVisible()) {
            apCadastro.setVisible(false);
        }

        if (apPesquisa.isVisible()) {
            apPesquisa.setVisible(false);
        }

        limparCampos();
        obsPedd.clear();
    }

    @FXML
    void btnPesquisarOnClick(ActionEvent event) {
        apCadastro.setVisible(false);
        apPesquisa.setVisible(true);
    }

    @FXML
    void btnPesquisarPedidosOnClick(ActionEvent event) {
        String pesquisa = tfPesquisa.getText().trim();
        List<Pedidos> pedidosCadastrados = PedidoDao.listar(pesquisa);

        obsPedd.clear();
        obsPedd.addAll(pedidosCadastrados);

        tbvPedidos.refresh();

        if (pedidosCadastrados.isEmpty()) {
            alerta(AlertType.WARNING, "AVISO!", "É um AVISO!", "Nenhum cliente encontrado!");

        }
    }

    @FXML
    void btnVoltarOnClick(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/view/TelaPrincipal.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Morcegão | Loja Online");
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();

        fecharTela();
    }

}
