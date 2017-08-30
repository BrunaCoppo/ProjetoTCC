package br.com.ctesop.control;

import br.com.ctesop.model.Produto;
import br.com.ctesop.model.Safra;
import br.com.ctesop.model.TipoSafra;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class CadastroSafraController implements Initializable {

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
    private ChoiceBox<Produto> cbProduto;

    @FXML
    private ChoiceBox<TipoSafra> cbTipoSafra;

    @FXML
    private DatePicker dpDataInicio;

    @FXML
    private DatePicker dpDataTermino;

    @FXML
    private RadioButton rbAtivo;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton rbInativo;

    @FXML
    private TableView<Safra> tbSafra;

    @FXML
    private TableColumn<Safra, TipoSafra> tbTipoSafra;

    @FXML
    private TableColumn<Safra, TipoSafra> tcStatus;

    @FXML
    void novo(ActionEvent event) {
        
    }

    @FXML
    void editar(ActionEvent event) {

    }

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void salvar(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}