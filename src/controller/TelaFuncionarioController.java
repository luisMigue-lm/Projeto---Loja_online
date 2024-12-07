package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import dao.FuncionarioDao;
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
import model.Funcionario;

public class TelaFuncionarioController {

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
    private Button btnPesquisarFuncionario;

    @FXML
    private Button btnVoltar;

    @FXML
    private TableColumn<Funcionario, Integer> colCPF;

    @FXML
    private TableColumn<Funcionario, LocalDate> colDtNascmt;

    @FXML
    private TableColumn<Funcionario, String> colEmail;

    @FXML
    private TableColumn<Funcionario, Integer> colIdFuncionario;

    @FXML
    private TableColumn<Funcionario, String> colNome;

    @FXML
    private TableColumn<Funcionario, Integer> colSenha;
    
    @FXML
    private DatePicker dpDtNascimento;
    
    @FXML
    private TableView<Funcionario> tbvFuncionarios;
    
    @FXML
    private TextField tfCPF;
    
    @FXML
    private TextField tfEmail;
    
    @FXML
    private TextField tfNome;
    
    @FXML
    private TextField tfPesquisa;
    
    @FXML
    private TextField tfSenha;
    
    ObservableList<Funcionario> obsFunc;
    
    @FXML
    private void initialize() {
        colIdFuncionario.setCellValueFactory(new PropertyValueFactory<>("idFuncionario"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario"));
        colSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
        colCPF.setCellValueFactory(new PropertyValueFactory<>("cpfFuncionario"));
        colDtNascmt.setCellValueFactory(new PropertyValueFactory<>("dtNascimento"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("emailFuncionario"));
        
        obsFunc = FXCollections.observableArrayList();
        tbvFuncionarios.setItems(obsFunc);

        tbvFuncionarios.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            btnEditar.setDisable(newValue == null);
            btnDeletar.setDisable(newValue == null);

        });

    }

    private void limparCampos() {
        tfNome.clear();
        tfSenha.clear();
        tfCPF.clear();
        dpDtNascimento.setValue(null);
        tfEmail.clear();
    }

    private void salvarAtualizacao(int idFuncionario) {
        String nomeFuncionario = tfNome.getText().trim();
        String senha = tfSenha.getText().trim();
        String cpfFuncionario = tfCPF.getText();
        LocalDate dtNascimento = dpDtNascimento.getValue();
        String emailFuncionario = tfEmail.getText();

        Funcionario funcionarioAtualizado = new Funcionario(idFuncionario, nomeFuncionario, senha, cpfFuncionario,dtNascimento, emailFuncionario);

        if (FuncionarioDao.atualizar(funcionarioAtualizado)) {
            JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso!", "Sucesso!",
                    JOptionPane.INFORMATION_MESSAGE);
            btnPesquisar.setDisable(false);
            btnOpcoes.setDisable(false);
            btnCadastrar.setDisable(false);
            tbvFuncionarios.refresh();
            limparCampos();

        } else {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o funcionário.", "ERRO", JOptionPane.ERROR_MESSAGE);

        }

        btnCadastrarCliente.setText("Cadastrar");
        btnCadastrarCliente.setOnAction(this::btnCadastrarClienteOnClick);

    }

    Stage stage;

    public void setStage(Stage stg) {
        preencherDados((Funcionario) stg.getUserData());
    }

    private void preencherDados(Funcionario funcionario) {
        tfNome.setText(funcionario.getNomeFuncionario());
        tfSenha.setText(funcionario.getSenha());
        tfCPF.setText(funcionario.getCpfFuncionario());
        dpDtNascimento.setValue(funcionario.getDtNascimento());
        tfEmail.setText(funcionario.getEmailFuncionario());

    }

    @FXML
    private void fecharTela() {
        Stage primaryStage = (Stage) btnVoltar.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void btnCadastrarClienteOnClick(ActionEvent event) {
        String nomeFuncionario = tfNome.getText().trim();
        String senha = tfSenha.getText().trim();
        String cpfFuncionario = tfCPF.getText().trim();
        LocalDate dtNascimento = dpDtNascimento.getValue();
        String emailFuncionario = tfEmail.getText().trim();

        Funcionario Funcionario = new Funcionario(1, nomeFuncionario, senha, cpfFuncionario, dtNascimento,
                emailFuncionario);

        if (FuncionarioDao.cadastrar(Funcionario)) {
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
        Funcionario funcionarioSelecionado = tbvFuncionarios.getSelectionModel().getSelectedItem();

        if (funcionarioSelecionado != null) {
            int resposta = JOptionPane.showConfirmDialog(null,
                    "Deseja realmente excluir o funcionário: " + funcionarioSelecionado.getNomeFuncionario() + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                boolean sucesso = FuncionarioDao.deletar(funcionarioSelecionado);

                if (sucesso) {

                    obsFunc.remove(funcionarioSelecionado);
                    tbvFuncionarios.refresh();

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
        Funcionario funcionarioSelecionado = tbvFuncionarios.getSelectionModel().getSelectedItem();
        preencherDados(funcionarioSelecionado);

        apCadastro.setVisible(true);
        apPesquisa.setVisible(false);

        btnCadastrar.setDisable(true);
        btnPesquisar.setDisable(true);
        btnDeletar.setDisable(true);
        btnOpcoes.setDisable(true);

        btnCadastrarCliente.setText("Atualizar");
        btnCadastrarCliente.setOnAction(e -> salvarAtualizacao(funcionarioSelecionado.getIdFuncionario()));
    }

    @FXML
    void btnLimparOnclick(ActionEvent event) {
        obsFunc.clear();
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
        obsFunc.clear();

    }

    @FXML
    void btnPesquisarFuncionariosOnClick(ActionEvent event) {
        String pesquisa = tfPesquisa.getText().trim();
        List<Funcionario> funcionariosCadastrados = FuncionarioDao.listar(pesquisa);

        obsFunc.clear();
        obsFunc.addAll(funcionariosCadastrados);

        tbvFuncionarios.refresh();

        if (funcionariosCadastrados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum funcionário encontrado.", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
        } 

    }

    @FXML
    void btnPesquisarOnClick(ActionEvent event) {
        apCadastro.setVisible(false);
        apPesquisa.setVisible(true);
        
    }

    @FXML
    void btnVoltarOnClick(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/view/TelaLogin.fxml");
        Parent root = FXMLLoader.load(url);

        Stage stgTelaPrincipal = new Stage();
        stgTelaPrincipal.setTitle("Morcegão | Login");
        stgTelaPrincipal.setScene(new Scene(root));
        stgTelaPrincipal.show();

        fecharTela();
    }

}
