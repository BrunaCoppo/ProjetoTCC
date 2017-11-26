package br.com.ctesop.control;

import br.com.ctesop.model.ContaPagar;
import br.com.ctesop.model.ParcelaPagar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class PagamentoController implements Initializable {

    @FXML
    private Button btnPagar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TableView<ContaPagar> tbContaPagar;

    @FXML
    private TableColumn<ContaPagar, String> tcContaPagar;

    @FXML
    private TableColumn<ContaPagar, String> tcDataConta;

    @FXML
    private TableColumn<ContaPagar, String> tcValor;

    @FXML
    private TableColumn<ContaPagar, String> tcStatus;

    @FXML
    private TableView<ParcelaPagar> tbParcelaPagar;

    @FXML
    private TableColumn<ParcelaPagar, String> tcValorConta;

    @FXML
    private TableColumn<ParcelaPagar, String> tcDataParcela;

    @FXML
    private TableColumn<ParcelaPagar, String> tcValorParcela;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void pagar(ActionEvent event) {

    }

    @FXML
    void cancelar(ActionEvent event) {

    }

}
