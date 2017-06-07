package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.dao.CidadeDAO;
import br.com.ctesop.dao.TerraDAO;
import br.com.ctesop.model.Cidade;
import br.com.ctesop.model.Terra;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CadastroTerraController implements Initializable {

    private int codigo=0;

    @FXML
    private ComboBox<Cidade> cbCidade;

    @FXML
    private TextField tfTamanho;

    @FXML
    private TextField tfDescricao;

    @FXML
    private RadioButton rbAtivo;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton rbInativo;

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCadastroCidade;

    @FXML
    private TableView<Terra> tbTerra;

    @FXML
    private TableColumn<Terra, String> tcCidade;

    @FXML
    private TableColumn<Terra, Integer> tcTamanho;

    @FXML
    private TableColumn<Terra, String> tcDescricao;

    @FXML
    private TableColumn<Terra, String> tcStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        tcTamanho.setCellValueFactory(new PropertyValueFactory<>("tamanhoFormatado"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        carregarComboCidade();
        atualizarTabela();
        habilitar(false);
    }

    @FXML
    void novo(ActionEvent event) {
        codigo = 0;
        limpar();
        habilitar(true);
    }

    @FXML
    void editar(ActionEvent event) {
        if (tbTerra.getSelectionModel().isEmpty()) {
            return;
        }

        Terra selecionado = tbTerra.getSelectionModel().getSelectedItem();
        codigo = selecionado.getCodigo();
        tfDescricao.setText(selecionado.getDescricao());
        cbCidade.getSelectionModel().select(selecionado.getCidade());
        if (selecionado.getStatus().equalsIgnoreCase("A")) {
            rbAtivo.setSelected(true);
        } else {
            rbInativo.setSelected(true);
        }
        habilitar(true);
    }

    @FXML
    void salvar(ActionEvent event) {
        try {
            Terra terra = new Terra();
            terra.setCodigo(codigo);
            terra.setDescricao(tfDescricao.getText());
            terra.setCidade(cbCidade.getSelectionModel().getSelectedItem());
            terra.setTamanho(tfTamanho.getText());

            if (rbAtivo.isSelected()) {
                terra.setStatus("A");
            } else {
                terra.setStatus("I");
            }

            TerraDAO.salvar(terra);

            Alerta.sucesso("Terra salva com sucesso.");

            atualizarTabela();
            limpar();
            habilitar(false);
        } catch (ExceptionValidacao e) {
            Alerta.alerta("Erro ao salvar.", e);
        } catch (Exception e) {
            Alerta.erro("Erro ao salvar.", e);
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        habilitar(false);
        limpar();
    }

    @FXML
    void cadastroCidade(ActionEvent event) {
         try {
            String url = "/br/com/ctesop/view/CadastroCidade.fxml";
            Scene scene = new Scene(new FXMLLoader(getClass().getResource(url)).load());
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Cidade");
            stage.setScene(scene);
            stage.show();
            //Carregar o estado na cidade alterado
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    carregarComboCidade();
                }
            });
        } catch (Exception e) {
            Alerta.erro("Erro ao abrir cadastro de cidade.", e);
        }

    }

    private void atualizarTabela() {
        try {
            tbTerra.setItems(TerraDAO.listar(false));
            tbTerra.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }

    private void limpar() {
        tfDescricao.setText("");
        tfTamanho.setText("");
        cbCidade.getSelectionModel().select(0);
        rbAtivo.setSelected(true);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tfDescricao.setDisable(!habilitar);
        tfTamanho.setDisable(!habilitar);
        cbCidade.setDisable(!habilitar);
        btnCadastroCidade.setDisable(!habilitar);
        rbAtivo.setDisable(!habilitar);
        rbInativo.setDisable(!habilitar);
    }

    private void carregarComboCidade() {
        try {
            cbCidade.setItems(CidadeDAO.listar(true));
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }

    }
}
