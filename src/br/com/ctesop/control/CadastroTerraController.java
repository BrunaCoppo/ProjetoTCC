package br.com.ctesop.control;

import br.com.ctesop.model.Cidade;
import br.com.ctesop.model.Terra;
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

public class CadastroTerraController implements Initializable {

    @FXML
    private ComboBox<Cidade> cbCidade;

    @FXML
    private TextField tfTamanho;

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
    private TableView<Terra> tbTerra;

    @FXML
    private TableColumn<Terra, String> tcCidade;

    @FXML
    private TableColumn<Terra, Integer> tcTamanho;

    @FXML
    private TableColumn<Terra, String> tcDescricao;

    @FXML
    private TableColumn<Terra, String> tcStatus;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
    void salvar(ActionEvent event) {

    }
}
