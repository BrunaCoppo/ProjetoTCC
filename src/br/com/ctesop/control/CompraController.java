package br.com.ctesop.control;

import br.com.ctesop.model.Compra;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class CompraController implements Initializable {

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Tab tpCompra;

    @FXML
    private DatePicker dpData;

    @FXML
    private TextField tfValor;

    @FXML
    private RadioButton rbAtivo;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton rbInativo;

    @FXML
    private RadioButton rbInativo1;

    @FXML
    private ToggleGroup status1;

    @FXML
    private TableView<?> tbCompra;

    @FXML
    private TableColumn<?, ?> tcData;

    @FXML
    private TableColumn<?, ?> tcValor;

    @FXML
    private TableColumn<?, ?> tcStatus;

    @FXML
    private Tab tpItemCompra;

    @FXML
    private Button btnAdicionarItem;

    @FXML
    private Button btnItemCompra;

    @FXML
    private ComboBox<?> cbProduto;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private TableView<?> tbItemCompre;

    @FXML
    private TableColumn<?, ?> tcProduto;

    @FXML
    private TableColumn<?, ?> tbQuantidade;

    @FXML
    private TableColumn<?, ?> tbValor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void novo(ActionEvent event) {
        
    }

    @FXML
    void editar(ActionEvent event) {

    }

    @FXML
    void salvar(ActionEvent event) {

    }

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void removerItem(ActionEvent event) {

    }

    @FXML
    void adicionalItem(ActionEvent event) {

    }

}
