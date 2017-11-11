package br.com.ctesop.control;

import br.com.ctesop.model.ContaPagar;
import java.net.URL;
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
        limpar();
        habilitar(false);
    }

    @FXML
    void novo(ActionEvent event) {
        codigo = 0;
        
        habilitar(true);
    }

    @FXML
    void editar(ActionEvent event) {
        habilitar(true);
        
        
    }

    @FXML
    void salvar(ActionEvent event) {
       
    }

    @FXML
    void cancelar(ActionEvent event) {
        limpar();
        

    }

    private void limpar() {
        tfDescricao.setText("");
        tfValor.setText("");
        tfQuantParcelas.setText("");
        dpData.setValue(null);
        rbPrazo.setSelected(true);
      
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnCancelar.setDisable(!habilitar);
        btnSalvar.setDisable(!habilitar);
        tfValor.setDisable(!habilitar);
        tfDescricao.setDisable(!habilitar);
        tfQuantParcelas.setDisable(!habilitar);
        dpData.setDisable(!habilitar);
        rbVista.setDisable(!habilitar);
        rbPrazo.setDisable(!habilitar);
    }

}
