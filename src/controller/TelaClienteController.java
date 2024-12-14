package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import dao.ClienteDao;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Cliente;

public class TelaClienteController {

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
    private Button btnPesquisarClientes;

    @FXML
    private Button btnVoltar;

    @FXML
    private TableColumn<Cliente, String> colCPF;

    @FXML
    private TableColumn<Cliente, String> colEndereco;

    @FXML
    private TableColumn<Cliente, Integer> colIdCliente;

    @FXML
    private TableColumn<Cliente, String> colNome;

    @FXML
    private TableColumn<Cliente, String> colGenero;

    @FXML
    private TableColumn<Cliente, String> colTelefone;

    @FXML
    private TableView<Cliente> tbvClientes;

    @FXML
    private TextField tfCPF;

    @FXML
    private TextField tfEndereco;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfPesquisa;

    @FXML
    private TextField tfGenero;

    @FXML
    private TextField tfTelefone;

    ObservableList<Cliente> obsClint;

    @FXML
    private void initialize() {
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colCPF.setCellValueFactory(new PropertyValueFactory<>("cpfCliente"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefoneCliente"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("enderecoClietne"));

        obsClint = FXCollections.observableArrayList();
        tbvClientes.setItems(obsClint);

        tbvClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
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
        tfNome.clear();
        tfGenero.clear();
        tfCPF.clear();
        tfTelefone.clear();
        tfEndereco.clear();
    }

    private void salvarAtualizacao(int idCliente) {
        String nomeCliente = tfNome.getText().trim();
        String genero = tfGenero.getText().trim();
        String cpfCliente = tfCPF.getText();
        String endereco = tfEndereco.getText();
        String telefone = tfTelefone.getText();

        Cliente clienteAtualizado = new Cliente(idCliente, nomeCliente, genero, cpfCliente, endereco, telefone);

        if (ClienteDao.atualizar(clienteAtualizado)) {
            alerta(AlertType.INFORMATION, "Sucesso!", "É um sucesso!", "Cliente atualizado com sucesso!");  

            btnPesquisar.setDisable(false);
            btnOpcoes.setDisable(false);
            btnCadastrar.setDisable(false);
            tbvClientes.refresh();
            limparCampos();

        } else {
            alerta(AlertType.ERROR, "ERRO!", "Encontremos um erro!", "Erro ao atualizar Cliente!");  

        }

        btnCadastrarCliente.setText("Cadastrar");
        btnCadastrarCliente.setOnAction(this::btnCadastrarClienteOnClick);

    }

    Stage stage;
    public void setStage(Stage stg) {
        preencherDados((Cliente) stg.getUserData());
    }

    private void preencherDados(Cliente cliente) {
        tfNome.setText(cliente.getNomeCliente());
        tfGenero.setText(String.valueOf(cliente.getGenero()));
        tfCPF.setText(cliente.getCpfCliente());
        tfEndereco.setText(cliente.getEnderecoClietne());
        tfTelefone.setText(cliente.getTelefoneCliente());

    }

    @FXML
    private void fecharTela() {
        Stage primaryStage = (Stage) btnVoltar.getScene().getWindow();
        primaryStage.close();

    }

    @FXML
    void btnCadastrarClienteOnClick(ActionEvent event) {
        String nomeCliente = tfNome.getText().trim();
        String senha = tfGenero.getText().trim();
        String cpfCliente = tfCPF.getText().trim();
        String endereco = tfEndereco.getText().trim();
        String telefone = tfTelefone.getText().trim();

        Cliente cliente = new Cliente(1, nomeCliente, senha, cpfCliente, endereco, telefone);

        if (ClienteDao.cadastrar(cliente)) {
            alerta(AlertType.INFORMATION, "Sucesso!", "É um sucesso!", "Cliente cadastrado com sucesso!");  
            limparCampos();

        } else {
            alerta(AlertType.ERROR, "ERRO!", "Encontremos um erro!", "Erro ao cadastrar Cliente!");  

        }

    }

    @FXML
    void btnCadastrarOnClick(ActionEvent event) {
        btnDeletar.setDisable(true);

        apCadastro.setVisible(true);
        apPesquisa.setVisible(false);
    }

    @FXML
    void btnDeletarOnClick(ActionEvent event) {
        Cliente clienteSelcionado = tbvClientes.getSelectionModel().getSelectedItem();

        if (clienteSelcionado != null) {
            Alert alertaDeletar = new Alert(AlertType.CONFIRMATION);
            alertaDeletar.setTitle("Confirmação");
            alertaDeletar.setHeaderText("Você tem certeza?");
            alertaDeletar.setContentText("Deseja realmente excluir o cliente: " + clienteSelcionado.getNomeCliente() + "?");

            Optional<ButtonType> resposta = alertaDeletar.showAndWait();

            if (resposta.isPresent() && resposta.get() == ButtonType.OK) {
                if (ClienteDao.deletar(clienteSelcionado)) {
                    obsClint.remove(clienteSelcionado);
                    tbvClientes.refresh();

                    alerta(AlertType.INFORMATION, "Sucesso!", "É um sucesso!", "Cliente excluído com sucesso!");

                } else {
                    alerta(AlertType.ERROR, "ERRO!", "OCORREU UM ERRO!", "Encontramos um erro ao realizar a ação!"); 

                }
            }

        }
    }

    @FXML
    void btnEditarOnClick(ActionEvent event) {
        Cliente clienteSelcionado = tbvClientes.getSelectionModel().getSelectedItem();
        preencherDados(clienteSelcionado);

        apCadastro.setVisible(true);
        apPesquisa.setVisible(false);

        btnCadastrar.setDisable(true);
        btnPesquisar.setDisable(true);
        btnDeletar.setDisable(true);
        btnOpcoes.setDisable(true);

        btnCadastrarCliente.setText("Atualizar");
        btnCadastrarCliente.setOnAction(e -> salvarAtualizacao(clienteSelcionado.getIdCliente()));
    }

    @FXML
    void btnLimparOnclick(ActionEvent event) {
        obsClint.clear();
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
        obsClint.clear();
    }

    @FXML
    void btnPesquisarClientesOnClick(ActionEvent event) {
        String pesquisa = tfPesquisa.getText().trim();
        List<Cliente> clientesCadastrados = ClienteDao.listar(pesquisa);

        obsClint.clear();
        obsClint.addAll(clientesCadastrados);

        tbvClientes.refresh();

        if (clientesCadastrados.isEmpty()) {
            alerta(AlertType.WARNING, "AVISO!", "É um AVISO!", "Nenhum cliente encontrado!"); 

        }
    }

    @FXML
    void btnPesquisarOnClick(ActionEvent event) {
        apCadastro.setVisible(false);
        apPesquisa.setVisible(true);

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
