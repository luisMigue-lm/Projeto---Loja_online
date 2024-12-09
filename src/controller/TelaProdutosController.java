package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import dao.ProdutosDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private void limparCampos() {
        tfNomeProduto.clear();
        tfFornecedor.clear();
        tfValor.clear();
        dpDataValidade.setValue(null);
        tfDescricao.clear();
    }

    private void salvarAtualizacao(int idProduto) {
        String nomeProduto = tfNomeProduto.getText().trim();
        LocalDate dtValidade = dpDataValidade.getValue();
        String forncedor = tfFornecedor.getText().trim();
        String descricao = tfDescricao.getText().trim();
        double valor = Double.parseDouble(tfValor.getText());

        Produto produtoAtualizado = new Produto(idProduto, nomeProduto, dtValidade, forncedor, descricao, valor);

        if (ProdutosDao.atualizar(produtoAtualizado)) {
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!", "Sucesso!",
                    JOptionPane.INFORMATION_MESSAGE);
            btnPesquisar.setDisable(false);
            btnOpcoes.setDisable(false);
            btnCadastrar.setDisable(false);
            tbvProdutos.refresh();
            limparCampos();

        } else {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o funcionário.", "ERRO", JOptionPane.ERROR_MESSAGE);

        }

        btnCadastrarProdutos.setText("Cadastrar");
        btnCadastrarProdutos.setOnAction(this::btnCadastrarProdutosOnClick);

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
        String nomeProduto = tfNomeProduto.getText().trim();
        LocalDate dtValidade = dpDataValidade.getValue();
        String forncedor = tfFornecedor.getText().trim();
        String descricao = tfDescricao.getText().trim();
        double valor = Double.parseDouble(tfValor.getText());

        Produto produto = new Produto(1, nomeProduto, dtValidade, forncedor, descricao, valor);

        if (ProdutosDao.cadastrar(produto)) {
            JOptionPane.showMessageDialog(null, "Seus dados foram cadastrados com sucesso!", "Sucesso!", 1);
            limparCampos();

        } else {
            JOptionPane.showMessageDialog(null, "ERRO AO CADASTRAR!", "ERRO!", 0);

        }
    }

    @FXML
    void btnDeletarOnClick(ActionEvent event) {
        Produto produtoSelecionado = tbvProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            int resposta = JOptionPane.showConfirmDialog(null,
            "Deseja realmente excluir o funcionário: " + produtoSelecionado.getNomeProduto() + "?",
            "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_NO_OPTION) {
                boolean sucesso = ProdutosDao.deletar(produtoSelecionado);

                if (sucesso) {
                    obsProdt.remove(produtoSelecionado);
                    tbvProdutos.refresh();

                    JOptionPane.showMessageDialog(null, "Funcionário excluído com sucesso!", "Sucesso!",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir o funcionário.", "Erro!",
                            JOptionPane.ERROR_MESSAGE);

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
            JOptionPane.showMessageDialog(null, "Nenhum produto encontrado.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);

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
