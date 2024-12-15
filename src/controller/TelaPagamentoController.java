package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import dao.PagamentoDao;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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
    private Spinner<Integer> spnQuantdParcelas;

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

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12);
        spnQuantdParcelas.setValueFactory(valueFactory);

        tbvPagamentos.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
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
        tfFormaPagamento.clear();
        tfTaxaJuros.clear();
        spnQuantdParcelas.setValueFactory(null);
        dpDataPagamento.setValue(null);
        tfDescricao.clear();
    }

    private void salvarAtualizacao(int idFormaPagmnt) {
        try {
            String formaPagamento = tfFormaPagamento.getText().trim();
            double taxaJuros = Double.parseDouble(tfTaxaJuros.getText().replace(",", "."));
            int qntdParcelas = spnQuantdParcelas.getValue();
            LocalDate data = dpDataPagamento.getValue();
            String descricao = tfDescricao.getText().trim();

            if (formaPagamento.isEmpty()) {
                alerta(AlertType.ERROR, "ERRO!", "Forma de Pagamento inválida!",
                        "O campo Forma de Pagamento não pode estar vázio.");
                return;
            }

            if (dpDataPagamento.getValue() == null) {
                alerta(AlertType.ERROR, "ERRO!", "Data inválida!", "Por favor, selecione uma data.");
                return;
            }

            if (descricao.isEmpty()) {
                alerta(AlertType.ERROR, "ERRO!", "Descrição inválida!", "O campo Descrição não pode estar vázio.");
                return;
            }

            Pagamento pagamentoAtualizado = new Pagamento(idFormaPagmnt, formaPagamento, taxaJuros, qntdParcelas, data,
                    descricao);

            if (PagamentoDao.atualizar(pagamentoAtualizado)) {
                alerta(AlertType.INFORMATION, "Sucesso!", "É um sucesso!",
                        "Forma de pagamento atualizada com sucesso!");

                btnPesquisar.setDisable(false);
                btnOpcoes.setDisable(false);
                btnCadastrar.setDisable(false);
                tbvPagamentos.refresh();
                limparCampos();

            } else {
                alerta(AlertType.ERROR, "ERRO!", "Encontremos um erro!", "Erro ao atualizar Forma de pagamento!");

            }

            btnCadastrarPagamentos.setText("Cadastrar");
            btnCadastrarPagamentos.setOnAction(this::btnCadastrarPagamentoOnClick);

        } catch (NumberFormatException e){
            alerta(AlertType.ERROR, "ERRO!", "Erro Inesperado", "Ocorreu um erro ao converter: " + e.getMessage());
            
        } catch (Exception e) {
            alerta(AlertType.ERROR, "ERRO!", "Erro Inesperado", "Ocorreu um erro: " + e.getMessage());
        }

    }

    Stage stage;
    public void setStage(Stage stg) {
        preencherDados((Pagamento) stg.getUserData());
    }

    private void preencherDados(Pagamento pagamento) {
        tfFormaPagamento.setText(pagamento.getMeioPagmnt());
        tfTaxaJuros.setText(String.valueOf(pagamento.getTaxaJuros()));
        spnQuantdParcelas.getValueFactory().setValue(pagamento.getQuantParcelas());
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
        try {
            String formaPagamento = tfFormaPagamento.getText().trim();
            double taxaJuros = Double.parseDouble(tfTaxaJuros.getText().replace(",", "."));
            int qntdParcelas = spnQuantdParcelas.getValue();
            LocalDate data = dpDataPagamento.getValue();
            String descricao = tfDescricao.getText().trim();

            if (formaPagamento.isEmpty()) {
                alerta(AlertType.ERROR, "ERRO!", "Forma de Pagamento inválida!",
                        "O campo Forma de Pagamento não pode estar vázio.");
                return;
            }

            if (dpDataPagamento.getValue() == null) {
                alerta(AlertType.ERROR, "ERRO!", "Data inválida!", "Por favor, selecione uma data.");
                return;
            }

            if (descricao.isEmpty()) {
                alerta(AlertType.ERROR, "ERRO!", "Descrição inválida!",
                        "O campo Descrição não pode estar vázio.");
                return;
            }

            Pagamento pagamento = new Pagamento(1, formaPagamento, taxaJuros, qntdParcelas, data, descricao);

            if (PagamentoDao.cadastrar(pagamento)) {
                alerta(AlertType.INFORMATION, "Sucesso!", "É um sucesso!",
                        "Forma de pagamento cadastrado com sucesso!");
                limparCampos();

            } else {
                alerta(AlertType.ERROR, "ERRO!", "Encontremos um erro!", "Erro ao cadastrar Forma de pagamento!");
            }

        } catch (NumberFormatException e){
            alerta(AlertType.ERROR, "ERRO!", "Erro Inesperado", "Ocorreu um erro ao converter: " + e.getMessage());
        } catch (Exception e) {
            alerta(AlertType.ERROR, "ERRO!", "Erro Inesperado", "Ocorreu um erro: " + e.getMessage());
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
            Alert alertaDeletar = new Alert(AlertType.CONFIRMATION);
            alertaDeletar.setTitle("Confirmação");
            alertaDeletar.setHeaderText("Você tem certeza?");
            alertaDeletar.setContentText(
                    "Deseja realmente excluir a Forma de pagamento: " + pagamentoSelecionado.getMeioPagmnt() + "?");

            Optional<ButtonType> resposta = alertaDeletar.showAndWait();

            if (resposta.isPresent() && resposta.get() == ButtonType.OK) {
                if (PagamentoDao.deletar(pagamentoSelecionado)) {
                    obsForPagmt.remove(pagamentoSelecionado);
                    tbvPagamentos.refresh();

                    alerta(AlertType.INFORMATION, "Sucesso!", "É um sucesso!",
                            "Forma de pagamento excluído com sucesso!");

                } else {
                    alerta(AlertType.ERROR, "ERRO!", "OCORREU UM ERRO!", "Encontramos um erro ao realizar a ação!");

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
        apCadastro.setVisible(false);
        apPesquisa.setVisible(false);
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
            JOptionPane.showMessageDialog(null, "Nenhum funcionário encontrado.", "Aviso!",
                    JOptionPane.INFORMATION_MESSAGE);

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
