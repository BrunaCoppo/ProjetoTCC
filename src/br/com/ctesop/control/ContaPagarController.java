package br.com.ctesop.control;

import br.com.ctesop.model.ContaPagar;
import br.com.ctesop.model.Produto;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
public class ContaPagarController implements Initializable {

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
    private DatePicker dpData;

    @FXML
    private TextField tfValor;

    @FXML
    private TextField tfDescricao;

    @FXML
    private RadioButton rbPrazo;

    @FXML
    private ToggleGroup FormaPagamento;

    @FXML
    private RadioButton rbVista;

    @FXML
    private TextField tfQuantParcelas;

    @FXML
    private TableView<ContaPagar> tbContaPagar;

    @FXML
    private TableColumn<ContaPagar, String> tcCompra;

    @FXML
    private TableColumn<ContaPagar, String> tcValor;

    @FXML
    private TableColumn<ContaPagar, String> tcFormaPagamento;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcCompra.setCellValueFactory(new PropertyValueFactory<>("compra"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcFormaPagamento.setCellValueFactory(new PropertyValueFactory<>("compra"));
        limpar();
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
        habilitar(true);
        
        NumberFormat nf = NumberFormat.getNumberInstance();
        ContaPagar selecionado = tbContaPagar.getSelectionModel().getSelectedItem();
        codigo = selecionado.getCodigo();
        tfDescricao.setText(selecionado.getDescricao());
        tfValor.setText(nf.format(selecionado.getValor()));
        tfDescricao.setText(selecionado.getDescricao());
        
        if (selecionado.getFormaPagamento().equalsIgnoreCase("P")) {
            rbPrazo.setSelected(true);
            tfQuantParcelas.setDisable(true);
        } else {
            rbVista.setSelected(true);
        }

    }

    @FXML
    void salvar(ActionEvent event) {

    }

    @FXML
    void cancelar(ActionEvent event) {

    }

    private void limpar() {
        tfDescricao.setText("");
        tfQuantParcelas.setText("");
        tfValor.setText("");
        dpData.setValue(null);
        rbVista.setSelected(true);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tfDescricao.setDisable(!habilitar);
        tfQuantParcelas.setDisable(!habilitar);
        tfValor.setDisable(!habilitar);
        dpData.setDisable(!habilitar);
        rbPrazo.setDisable(!habilitar);
        rbVista.setDisable(!habilitar);
    }
}
