package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import dao.PagamentoDao;
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
import model.Pagamento;

public class TelaPagamentoController {

    @FXML
    private AnchorPane apCadastro;

    @FXML
    private AnchorPane apPesquisa;

    @FXML
    private BorderPane bPane;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCadastrarPagamentos;

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
    private Button btnPesquisarPagamentos;

    @FXML
    private Button btnVoltar;

    @FXML
    private TableColumn<Pagamento, LocalDate> colData;

    @FXML
    private TableColumn<Pagamento, String> colDescricao;

    @FXML
    private TableColumn<Pagamento, String> colFormaPagamento;

    @FXML
    private TableColumn<Pagamento, Integer> colIdPagamento;

    @FXML
    private TableColumn<Pagamento, Integer> colQuantdParcelas;

    @FXML
    private TableColumn<Pagamento, Double> colTaxaJuros;

    @FXML
    private DatePicker dpDataPagamento;

    @FXML
    private TableView<Pagamento> tbvPagamentos;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfFormaPagamento;

    @FXML
    private TextField tfPesquisa;

    @FXML
    private TextField tfQuantdParcelas;

    @FXML
    private TextField tfTaxaJuros;

    ObservableList<Pagamento> obsForPagmt;

    @FXML
    private void initialize() {
        colIdPagamento.setCellValueFactory(new PropertyValueFactory<>("idFormaPagmnt"));
        colFormaPagamento.setCellValueFactory(new PropertyValueFactory<>("meioPagmnt"));
        colTaxaJuros.setCellValueFactory(new PropertyValueFactory<>("taxaJuros"));
        colQuantdParcelas.setCellValueFactory(new PropertyValueFactory<>("quantParcelas"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        obsForPagmt = FXCollections.observableArrayList();
        tbvPagamentos.setItems(obsForPagmt);

        tbvPagamentos.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            btnEditar.setDisable(newValue == null);
            btnDeletar.setDisable(newValue == null);

        });
        
    }

    private void limparCampos() {
        tfFormaPagamento.clear();
        tfTaxaJuros.clear();
        tfQuantdParcelas.clear();
        dpDataPagamento.setValue(null);
        tfDescricao.clear();
    }

    private void salvarAtualizacao(int idFormaPagmnt) {
        String formaPagamento = tfFormaPagamento.getText().trim();
        double taxaJuros = Double.parseDouble(tfTaxaJuros.getText());
        int qntdParcelas = Integer.parseInt(tfQuantdParcelas.getText());
        LocalDate data = dpDataPagamento.getValue();
        String descricao = tfDescricao.getText().trim();
        
        Pagamento pagamentoAtualizado = new Pagamento(idFormaPagmnt, formaPagamento, taxaJuros, qntdParcelas, data, descricao);

        if (PagamentoDao.atualizar(pagamentoAtualizado)) {
            JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso!", "Sucesso!",
                    JOptionPane.INFORMATION_MESSAGE);
            btnPesquisar.setDisable(false);
            btnOpcoes.setDisable(false);
            btnCadastrar.setDisable(false);
            tbvPagamentos.refresh();
            limparCampos();

        } else {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o funcionário.", "ERRO", JOptionPane.ERROR_MESSAGE);

        }

        btnCadastrarPagamentos.setText("Cadastrar");
        btnCadastrarPagamentos.setOnAction(this::btnCadastrarPagamentoOnClick);

    }

    Stage stage;
    public void setStage(Stage stg) {
        preencherDados((Pagamento) stg.getUserData());
    }
    
    private void preencherDados(Pagamento pagamento) {
        tfFormaPagamento.setText(pagamento.getMeioPagmnt());
        tfTaxaJuros.setText(String.valueOf(pagamento.getTaxaJuros()));
        tfQuantdParcelas.setText(String.valueOf(pagamento.getQuantParcelas()));
        dpDataPagamento.setValue(pagamento.getData());
        tfDescricao.setText(pagamento.getDescricao());

    }

    @FXML
    private void fecharTela() {
        Stage primaryStage = (Stage) btnVoltar.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void btnCadastrarPagamentoOnClick(ActionEvent event) {
        String formaPagamento = tfFormaPagamento.getText().trim();
        double taxaJuros = Double.parseDouble(tfTaxaJuros.getText());
        int qntdParcelas = Integer.parseInt(tfQuantdParcelas.getText());
        LocalDate data = dpDataPagamento.getValue();
        String descricao = tfDescricao.getText().trim();

        Pagamento pagamento = new Pagamento(1, formaPagamento, taxaJuros, qntdParcelas, data, descricao);

        if (PagamentoDao.cadastrar(pagamento)) {
            JOptionPane.showMessageDialog(null, "Seus dados foram cadastrados com sucesso!", "Sucesso!", 1);
            limparCampos();

        } else {
            JOptionPane.showMessageDialog(null, "ERRO AO CADASTRAR!", "ERRO!", 0);

        }
    }

    @FXML
    void btnCadastrarOnClick(ActionEvent event) {
        apCadastro.setVisible(true);
        apPesquisa.setVisible(false);

    }

    @FXML
    void btnDeletarOnClick(ActionEvent event) {
        Pagamento pagamentoSelecionado = tbvPagamentos.getSelectionModel().getSelectedItem();

        if (pagamentoSelecionado != null) {
            int resposta = JOptionPane.showConfirmDialog(null,
                    "Deseja realmente excluir o funcionário: " + pagamentoSelecionado.getMeioPagmnt() + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                boolean sucesso = PagamentoDao.deletar(pagamentoSelecionado);

                if (sucesso) {
                    obsForPagmt.remove(pagamentoSelecionado);
                    tbvPagamentos.refresh();

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
        Pagamento pagamentoSelecionado = tbvPagamentos.getSelectionModel().getSelectedItem();
        preencherDados(pagamentoSelecionado);

        apCadastro.setVisible(true);
        apPesquisa.setVisible(false);

        btnCadastrar.setDisable(true);
        btnPesquisar.setDisable(true);
        btnDeletar.setDisable(true);
        btnOpcoes.setDisable(true);

        btnCadastrarPagamentos.setText("Atualizar");
        btnCadastrarPagamentos.setOnAction(e -> salvarAtualizacao(pagamentoSelecionado.getIdFormaPagmnt()));
    }

    @FXML
    void btnLimparOnclick(ActionEvent event) {
        obsForPagmt.clear();
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
        obsForPagmt.clear();
    }

    @FXML
    void btnPesquisarOnClick(ActionEvent event) {
        apCadastro.setVisible(false);
        apPesquisa.setVisible(true);

    }

    @FXML
    void btnPesquisarPagamentosOnClick(ActionEvent event) {
        String pesquisa = tfPesquisa.getText().trim();
        List<Pagamento> pagamentosSelecionados = PagamentoDao.listar(pesquisa);

        obsForPagmt.clear();
        obsForPagmt.addAll(pagamentosSelecionados);

        tbvPagamentos.refresh();

        if (pagamentosSelecionados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum funcionário encontrado.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);

        } 
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

}
