package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.dao.CidadeDAO;
import br.com.ctesop.dao.CooperativaDAO;
import br.com.ctesop.dao.EstadoDAO;
import br.com.ctesop.model.Cidade;
import br.com.ctesop.model.Cooperativa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class CadastroCooperativaController implements Initializable {

    private int codigo;
    @FXML
    private TextField tfNome;

    @FXML
    private ComboBox<Cidade> cbCidade;

    @FXML
    private TextField tfUnidade;

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
    private TableView<Cooperativa> tbCooperativa;

    @FXML
    private TableColumn<Cooperativa, String> tcNome;

    @FXML
    private TableColumn<Cooperativa, String> tcCidade;

    @FXML
    private TableColumn<Cooperativa, String> tcUnidade;

    @FXML
    private TableColumn<Cooperativa, String> tcStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcCidade.setCellValueFactory(new PropertyValueFactory<>("Cidade"));
        tcUnidade.setCellValueFactory(new PropertyValueFactory<>("unidade"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        carregarComboEstado();
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
        if (tbCooperativa.getSelectionModel().isEmpty()) {
            return;
        }

        Cooperativa selecionado = tbCooperativa.getSelectionModel().getSelectedItem();
        codigo = selecionado.getCodigo();
        tfNome.setText(selecionado.getNome());
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
            Cooperativa cooperativa = new Cooperativa();
            cooperativa.setCodigo(codigo);
            cooperativa.setNome(tfNome.getText());
            cooperativa.setCidade(cbCidade.getSelectionModel().getSelectedItem());

            if (rbAtivo.isSelected()) {
                cooperativa.setStatus("A");
            } else {
                cooperativa.setStatus("I");
            }

            CooperativaDAO.salvar(cooperativa);

            Alerta.sucesso("Cidade salva com sucesso.");

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

    private void atualizarTabela() {
        try {
            tbCooperativa.setItems(CooperativaDAO.listar(false));
            tbCooperativa.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }

    private void limpar() {
        tfNome.setText("");
        cbCidade.getSelectionModel().select(0);
        rbAtivo.setSelected(true);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tfNome.setDisable(!habilitar);
        cbCidade.setDisable(!habilitar);
        // btn.setDisable(!habilitar);
        rbAtivo.setDisable(!habilitar);
        rbInativo.setDisable(!habilitar);
    }

    private void carregarComboEstado() {
        try {
            cbCidade.setItems(CidadeDAO.listar(true));
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }

}
