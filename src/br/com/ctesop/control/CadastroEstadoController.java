package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.dao.EstadoDAO;
import br.com.ctesop.model.Estado;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Bruna
 */
public class CadastroEstadoController implements Initializable {

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
    private TextField nome;

    @FXML
    private TextField sigla;

    @FXML
    private RadioButton rbAtivo;

    @FXML
    private RadioButton rbInativo;

    @FXML
    private ToggleGroup status;

    @FXML
    private TableView<Estado> tbEstado;

    @FXML
    private TableColumn<Estado, String> tcEstado;

    @FXML
    private TableColumn<Estado, String> tcSigla;

    @FXML
    private TableColumn<Estado, String> tcStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Exibir na tabela
        tcEstado.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcSigla.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        atualizarTabela();
        habilitar(false);
    }

    @FXML
    public void novo(ActionEvent event) {
        codigo = 0;
        limpar();
        habilitar(true);
    }

    @FXML
    public void editar(ActionEvent event) {
        if (tbEstado.getSelectionModel().isEmpty()) {
            return;
        }

        Estado selecionado = tbEstado.getSelectionModel().getSelectedItem();
        codigo = selecionado.getCodigo();
        nome.setText(selecionado.getNome());
        sigla.setText(selecionado.getSigla());
        if (selecionado.getStatus().equalsIgnoreCase("A")) {
            rbAtivo.setSelected(true);
        } else {
            rbInativo.setSelected(true);
        }
        habilitar(true);
    }

    @FXML
    public void salvar(ActionEvent event) {

        try {
            Estado estado = new Estado();
            estado.setCodigo(codigo);
            estado.setNome(nome.getText());
            estado.setSigla(sigla.getText());

            if (rbAtivo.isSelected()) {
                estado.setStatus("A");
            } else {
                estado.setStatus("I");
            }

            EstadoDAO.salvar(estado);

            Alerta.sucesso("Estado salvo com sucesso.");

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

    private void atualizarTabela() {
        try {
            tbEstado.setItems(EstadoDAO.listar(false));
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }

    private void limpar() {
        nome.setText("");
        sigla.setText("");
        rbAtivo.setSelected(true);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        nome.setDisable(!habilitar);
        sigla.setDisable(!habilitar);
        rbAtivo.setDisable(!habilitar);
        rbInativo.setDisable(!habilitar);
    }

}
