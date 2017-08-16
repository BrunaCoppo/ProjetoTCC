package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.dao.TipoManejoDAO;
import br.com.ctesop.model.TipoManejo;
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
 * FXML Controller class
 *
 * @author Bruna
 */
public class CadastroTipoManejoController implements Initializable {

    private int codigo = 0;

    @FXML
    private TextField tfTipoManejo;

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
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TableView<TipoManejo> tbTipoManejo;

    @FXML
    private TableColumn<TipoManejo, String> tcTipoManejo;

    @FXML
    private TableColumn<TipoManejo, String> tcValor;

    @FXML
    private TableColumn<TipoManejo, String> tcStatus; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcTipoManejo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valorFormatado"));
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
        if (tbTipoManejo.getSelectionModel().isEmpty()) {
            return;
        }

        TipoManejo selecionado = tbTipoManejo.getSelectionModel().getSelectedItem();
        codigo = selecionado.getCodigo();
        tfTipoManejo.setText(selecionado.getNome());
        tfValor.setText(selecionado.getValorFormatado());
        tfDescricao.setText(selecionado.getDescricao());
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
            TipoManejo tipoManejo = new TipoManejo();
            tipoManejo.setCodigo(codigo);
            tipoManejo.setNome(tfTipoManejo.getText());
            tipoManejo.setValor(tfValor.getText());
            tipoManejo.setDescricao(tfDescricao.getText());

            if (rbAtivo.isSelected()) {
                tipoManejo.setStatus("A");
            } else {
                tipoManejo.setStatus("I");
            }

            TipoManejoDAO.salvar(tipoManejo);

            Alerta.sucesso("Tipo manejo salvo com sucesso.");

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
            tbTipoManejo.setItems(TipoManejoDAO.listar(false));
            tbTipoManejo.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
            e.printStackTrace();
        }
    }

    private void limpar() {
        tfTipoManejo.setText("");
        tfValor.setText("");
        tfDescricao.setText("");
        rbAtivo.setSelected(true);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tfTipoManejo.setDisable(!habilitar);
        tfValor.setDisable(!habilitar);
        tfDescricao.setDisable(!habilitar);
        rbAtivo.setDisable(!habilitar);
        rbInativo.setDisable(!habilitar);
    }

}
