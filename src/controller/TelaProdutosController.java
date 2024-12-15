package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Produto;

public class TelaProdutosController {

    @FXML
    private AnchorPane apCadastro;

    @FXML
    private AnchorPane apPesquisa;

    @FXML
    private BorderPane bPane;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCadastrarProdutos;

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
    private Button btnPesquisarProdutos;

    @FXML
    private Button btnVoltar;

    @FXML
    private TableColumn<Produto, String> colDescricao;

    @FXML
    private TableColumn<Produto, LocalDate> colDtValidade;

    @FXML
    private TableColumn<Produto, String> colFornecedor;

    @FXML
    private TableColumn<Produto, Integer> colIdProduto;

    @FXML
    private TableColumn<Produto, String> colNomeProduto;

    @FXML
    private TableColumn<Produto, Double> colValor;

    @FXML
    private DatePicker dpDataValidade;

    @FXML
    private TableView<Produto> tbvProdutos;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfFornecedor;

    @FXML
    private TextField tfNomeProduto;

    @FXML
    private TextField tfPesquisa;

    @FXML
    private TextField tfValor;

    ObservableList<Produto> obsProdt;

    @FXML
    private void initialize() {
        colIdProduto.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        colNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colDtValidade.setCellValueFactory(new PropertyValueFactory<>("dtValidade"));
        colFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("preco"));

        obsProdt = FXCollections.observableArrayList();
        tbvProdutos.setItems(obsProdt);

        tbvProdutos.getSelectionModel().selectedIndexProperty().addListener((obs, oldValue, newValue) -> {
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
        tfNomeProduto.clear();
        tfFornecedor.clear();
        tfValor.clear();
        dpDataValidade.setValue(null);
        tfDescricao.clear();
    }

    private void salvarAtualizacao(int idProduto) {
        try {
            String nomeProduto = tfNomeProduto.getText().trim();
            LocalDate dtValidade = dpDataValidade.getValue();
            String forncedor = tfFornecedor.getText().trim();
            String descricao = tfDescricao.getText().trim();
            double valor = Double.parseDouble(tfValor.getText().trim().replace(",", "."));
    
            if (nomeProduto.isEmpty()) {
                alerta(AlertType.ERROR, "ERRO!", "Nome do Produto inválido!", "O campo Nome Produto não pode estar vázio.");
                    return;
            }
    
            if (forncedor.isEmpty()) {
                alerta(AlertType.ERROR, "ERRO!", "Nome do Fornecedor inválido!", "O campo Nome do Fornecedor não pode estar vázio.");
                return;
            }

            if (descricao.isEmpty()) {
                alerta(AlertType.ERROR, "ERRO!", "Descrição inválida!", "O campo Descrição não pode estar vázio.");
                return;
            }

            if (valor < 0.0) {
                alerta(AlertType.ERROR, "ERRO!", "Valor inválido!", "O campo Valor não pode ser menor do que zero.");
                return;
            }

            Produto produtoAtualizado = new Produto(idProduto, nomeProduto, dtValidade, forncedor, descricao, valor);
    
            if (ProdutosDao.atualizar(produtoAtualizado)) {
                alerta(AlertType.INFORMATION, "Sucesso!", "É um sucesso!", "Produto atualizado com sucesso!");  
    
                btnPesquisar.setDisable(false);
                btnOpcoes.setDisable(false);
                btnCadastrar.setDisable(false);
                tbvProdutos.refresh();
                limparCampos();
    
            } else {
                alerta(AlertType.ERROR, "ERRO!", "Encontremos um erro!", "Erro ao atualizar Produto!");  
    
            }
    
            btnCadastrarProdutos.setText("Cadastrar");
            btnCadastrarProdutos.setOnAction(this::btnCadastrarProdutosOnClick);
            
        } catch (Exception e) {
            alerta(AlertType.ERROR, "ERRO!", "Erro Inesperado", "Ocorreu um erro: " + e.getMessage());
        }

    }

    Stage stage;
    public void setStage(Stage stg) {
        preencherDados((Produto) stg.getUserData());
    }

    private void preencherDados(Produto produto) {
        tfNomeProduto.setText(produto.getNomeProduto());
        dpDataValidade.setValue(produto.getDtValidade());
        tfFornecedor.setText(produto.getFornecedor());
        tfDescricao.setText(produto.getDescricao());
        tfValor.setText(Double.toString(produto.getPreco()));
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
    void btnCadastrarProdutosOnClick(ActionEvent event) {
        try {
            String nomeProduto = tfNomeProduto.getText().trim();
            LocalDate dtValidade = dpDataValidade.getValue();
            String forncedor = tfFornecedor.getText().trim();
            String descricao = tfDescricao.getText().trim();
            double valor = Double.parseDouble(tfValor.getText().trim().replace(",", "."));
    
            if (nomeProduto.isEmpty()) {
                alerta(AlertType.ERROR, "ERRO!", "Nome do Produto inválido!", "O campo Nome Produto não pode estar vázio.");
                    return;
            }

            if (forncedor.isEmpty()) {
                alerta(AlertType.ERROR, "ERRO!", "Nome do Fornecedor inválido!", "O campo Nome do Fornecedor não pode estar vázio.");
                return;
            }

            if (descricao.isEmpty()) {
                alerta(AlertType.ERROR, "ERRO!", "Descrição inválida!", "O campo Descrição não pode estar vázio.");
                return;
            }

            if (valor < 0.0) {
                alerta(AlertType.ERROR, "ERRO!", "Valor inválido!", "O campo Valor não pode ser menor do que zero.");
                return;
            }
    
            Produto produto = new Produto(1, nomeProduto, dtValidade, forncedor, descricao, valor);
    
            if (ProdutosDao.cadastrar(produto)) {
                alerta(AlertType.INFORMATION, "Sucesso!", "É um sucesso!", "Produto cadastrado com sucesso!");  
                limparCampos();
    
            } else {
                alerta(AlertType.ERROR, "ERRO!", "Encontremos um erro!", "Erro ao cadastrar Produto!");  
    
            }
            
        } catch (Exception e) {
            alerta(AlertType.ERROR, "ERRO!", "Erro Inesperado", "Ocorreu um erro: " + e.getMessage());
        }
        
    }

    @FXML
    void btnDeletarOnClick(ActionEvent event) {
        Produto produtoSelecionado = tbvProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            Alert alertaDeletar = new Alert(AlertType.CONFIRMATION);
            alertaDeletar.setTitle("Confirmação");
            alertaDeletar.setHeaderText("Você tem certeza?");
            alertaDeletar.setContentText("Deseja realmente excluir o cliente: " + produtoSelecionado.getNomeProduto() + "?");

            Optional<ButtonType> resposta = alertaDeletar.showAndWait();

            if (resposta.isPresent() && resposta.get() == ButtonType.OK) {
                if (ProdutosDao.deletar(produtoSelecionado)) {
                    obsProdt.remove(produtoSelecionado);
                    tbvProdutos.refresh();

                    alerta(AlertType.INFORMATION, "Sucesso!", "É um sucesso!", "Produto excluído com sucesso!"); 

                } else {
                    alerta(AlertType.ERROR, "ERRO!", "OCORREU UM ERRO!", "Encontramos um erro ao realizar a ação!"); 

                }
            }
        }

    }

    @FXML
    void btnEditarOnClick(ActionEvent event) {
        Produto produtoSelecionado = tbvProdutos.getSelectionModel().getSelectedItem();
        preencherDados(produtoSelecionado);

        apCadastro.setVisible(true);
        apPesquisa.setVisible(false);

        btnCadastrar.setDisable(true);
        btnPesquisar.setDisable(true);
        btnDeletar.setDisable(true);
        btnOpcoes.setDisable(true);

        btnCadastrarProdutos.setText("Atualizar");
        btnCadastrarProdutos.setOnAction(e -> salvarAtualizacao(produtoSelecionado.getIdProduto()));
    }

    @FXML
    void btnLimparOnclick(ActionEvent event) {
        obsProdt.clear();

        btnDeletar.setDisable(true);
        btnEditar.setDisable(true);

    }

    @FXML
    void btnOpcoesOnClick(ActionEvent event) {
        apCadastro.setVisible(false);
        apPesquisa.setVisible(false);
        limparCampos();
        obsProdt.clear();
    }

    @FXML
    void btnPesquisarOnClick(ActionEvent event) {
        apCadastro.setVisible(false);
        apPesquisa.setVisible(true);
    }

    @FXML
    void btnPesquisarProdutosOnClick(ActionEvent event) {
        String pesquisa = tfPesquisa.getText().trim();
        List<Produto> produtosCadastrados = ProdutosDao.listar(pesquisa);

        obsProdt.clear();
        obsProdt.addAll(produtosCadastrados);

        tbvProdutos.refresh();

        if (produtosCadastrados.isEmpty()) {
            alerta(AlertType.WARNING, "AVISO!", "É um AVISO!", "Nenhum produto encontrado!"); 

        }
    }

    @FXML
    void btnVoltarOnClick(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/view/TelaPrincipal.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Morcegão | Loja Online");
        stgTelaPrincipal.getIcons().add(new Image("file:src/resources/imgs/Logo - Laranja.png"));
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();

        fecharTela();
    }
}
