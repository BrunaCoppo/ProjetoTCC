package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.dao.ArrendamentoDAO;
import br.com.ctesop.dao.TerraDAO;
import br.com.ctesop.model.Arrendamento;
import br.com.ctesop.model.Terra;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class CadastroArrendamentoController implements Initializable {

    private int codigo = 0;

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<Terra> cbTerra;

    @FXML
    private Button btnCadastroTerra;

    @FXML
    private DatePicker dpData;

    @FXML
    private TextField tfValor;

    @FXML
    private TextField tfDescricao;

    @FXML
    private RadioButton rbAtivo;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton rbInativo;

    @FXML
    private TableView<Arrendamento> tbArrendamento;

    @FXML
    private TableColumn<Arrendamento, String> tcTerra;

    @FXML
    private TableColumn<Arrendamento, String> tcData;

    @FXML
    private TableColumn<Arrendamento, String> tcValor;

    @FXML
    private TableColumn<Arrendamento, String> tcStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcTerra.setCellValueFactory(new PropertyValueFactory<>("terra"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valorFormatado"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        carregarComboTerra();
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
        if (tbArrendamento.getSelectionModel().isEmpty()) {
            return;
        }

        Arrendamento selecionado = tbArrendamento.getSelectionModel().getSelectedItem();
        codigo = selecionado.getCodigo();
        dpData.setValue(Converter.toLocalDate(selecionado.getData()));
        tfDescricao.setText(selecionado.getDescricao());
        tfValor.setText(selecionado.getValorFormatado());
        cbTerra.getSelectionModel().select(selecionado.getTerra());
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
            Arrendamento arrendamento = new Arrendamento();
            arrendamento.setCodigo(codigo);
            arrendamento.setDescricao(tfDescricao.getText());
            arrendamento.setValor(tfValor.getText());
            arrendamento.setData(Converter.converterData(dpData.getValue()));
            arrendamento.setTerra(cbTerra.getSelectionModel().getSelectedItem());

            if (rbAtivo.isSelected()) {
                arrendamento.setStatus("A");
            } else {
                arrendamento.setStatus("I");
            }

            ArrendamentoDAO.salvar(arrendamento);

            Alerta.sucesso("Arrendamento salva com sucesso.");

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
    public void cancelar(ActionEvent event) {
        habilitar(false);
        limpar();
    }

    @FXML
    void cadastroTerra(ActionEvent event) {
        try {
            String url = "/br/com/ctesop/view/CadastroTerra.fxml";
            Scene scene = new Scene(new FXMLLoader(getClass().getResource(url)).load());
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Terra");
            stage.setScene(scene);
            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    carregarComboTerra();
                }
            });
        } catch (Exception e) {
            Alerta.erro("Erro ao abrir cadastro de estado.", e);
        }
    }

    private void atualizarTabela() {
        try {
            tbArrendamento.setItems(ArrendamentoDAO.listar(false));
            tbArrendamento.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }

    private void limpar() {
        tfDescricao.setText("");
        tfValor.setText("");
        dpData.setValue(null);
        cbTerra.getSelectionModel().select(0);
        rbAtivo.setSelected(true);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tfDescricao.setDisable(!habilitar);
        tfValor.setDisable(!habilitar);
        dpData.setDisable(!habilitar);
        cbTerra.setDisable(!habilitar);
        btnCadastroTerra.setDisable(!habilitar);
        rbAtivo.setDisable(!habilitar);
        rbInativo.setDisable(!habilitar);
    }

    private void carregarComboTerra() {
        try {
            cbTerra.setItems(TerraDAO.listar(true));
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }
}
