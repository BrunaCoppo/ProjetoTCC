package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.dao.TipoSafraDAO;
import br.com.ctesop.model.TipoSafra;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
public class CadastroTipoSafraController implements Initializable {

    private int codigo = 0;

    @FXML
    private TextField tfTipoSafra;

    @FXML
    private RadioButton rbAtivo;

    @FXML
    private ToggleGroup status;

    @FXML
    private TableView<TipoSafra> tbtipoSafra;

    @FXML
    private TableColumn<TipoSafra, String> tcTipoSafra;

    @FXML
    private TableColumn<TipoSafra, String> tcStatus;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcTipoSafra.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
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
        if (tbtipoSafra.getSelectionModel().isEmpty()) {
            return;
        }

        TipoSafra selecionado = tbtipoSafra.getSelectionModel().getSelectedItem();
        codigo = selecionado.getCodigo();
        tfTipoSafra.setText(selecionado.getNome());
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
            TipoSafra tipoSafra = new TipoSafra();
            tipoSafra.setCodigo(codigo);
            tipoSafra.setNome(tfTipoSafra.getText());

            if (rbAtivo.isSelected()) {
                tipoSafra.setStatus("A");
            } else {
                tipoSafra.setStatus("I");
            }

            TipoSafraDAO.salvar(tipoSafra);

            Alerta.sucesso("Tipo safra salvo com sucesso.");

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
            tbtipoSafra.setItems(TipoSafraDAO.listar(false));
            tbtipoSafra.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }

    private void limpar() {
        tfTipoSafra.setText("");
        rbAtivo.setSelected(true);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tfTipoSafra.setDisable(!habilitar);
        rbAtivo.setDisable(!habilitar);
        rbInativo.setDisable(!habilitar);
    }
}
