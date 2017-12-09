package br.com.ctesop.control;

import br.com.ctesop.model.Safra;
import br.com.ctesop.model.Terra;
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
public class ManejoController implements Initializable {

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Tab tpManejo;

    @FXML
    private ComboBox<Terra> cbTerra;

    @FXML
    private Button btnCadastroTerra;

    @FXML
    private DatePicker dpData;

    @FXML
    private ComboBox<?> cbTipoManejo;

    @FXML
    private Button btnCadastroFornecedor1;

    @FXML
    private TextField tfValorTotal;

    @FXML
    private ComboBox<Safra> cbSafra;

    @FXML
    private Button btnCadastroSafra;

    @FXML
    private TextField tfDescricao;

    @FXML
    private RadioButton rbPendente;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton rbConfirmado;

    @FXML
    private RadioButton rbCancelado;

    @FXML
    private TableView<?> tbManejo;

    @FXML
    private TableColumn<?, ?> tcTerra;

    @FXML
    private TableColumn<?, ?> tcData;

    @FXML
    private TableColumn<?, ?> tcValor;

    @FXML
    private TableColumn<?, ?> tcStatus;

    @FXML
    private Tab tpItemManejo;

    @FXML
    private Button btnAdicionarItem;

    @FXML
    private Button btnItemManejo;

    @FXML
    private ComboBox<?> cbProduto;

    @FXML
    private Button btnCadastroProduto;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private TextField tfValorUnitario;

    @FXML
    private TableView<?> tbItemCompra;

    @FXML
    private TableColumn<?, ?> tcProduto;

    @FXML
    private TableColumn<?, ?> tcQuantidade;

    @FXML
    private TableColumn<?, ?> tcValorUnitario;

   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
 @FXML
    void adicionarItem(ActionEvent event) {

    }

    @FXML
    void cadastroProduto(ActionEvent event) {

    }

    @FXML
    void cadastroSafra(ActionEvent event) {

    }

    @FXML
    void cadastroTerra(ActionEvent event) {

    }

    @FXML
    void cadastroTipoManejo(ActionEvent event) {

    }

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void editar(ActionEvent event) {

    }

    @FXML
    void novo(ActionEvent event) {

    }

    @FXML
    void removerItem(ActionEvent event) {

    }

    @FXML
    void salvar(ActionEvent event) {

    }
}
