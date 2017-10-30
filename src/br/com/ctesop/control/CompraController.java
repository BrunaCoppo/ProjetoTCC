package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.model.Compra;
import br.com.ctesop.model.ItensCompra;
import br.com.ctesop.model.Produto;
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
    private Tab tpCompra;

    @FXML
    private DatePicker dpData;

    @FXML
    private TextField tfValorTotal;

    @FXML
    private RadioButton rbPendente;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton rbConfirmado;

    @FXML
    private RadioButton rbCancelado;

    @FXML
    private ToggleGroup status1;

    @FXML
    private TableView<Compra> tbCompra;

    @FXML
    private TableColumn<Compra, String> tcData;

    @FXML
    private TableColumn<Compra, String> tcValor;

    @FXML
    private TableColumn<Compra, String> tcStatus;

    @FXML
    private Tab tpItemCompra;

    @FXML
    private Button btnAdicionarItem;

    @FXML
    private Button btnItemCompra;

    @FXML
    private ComboBox<Produto> cbProduto;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private TextField tfValor;

    @FXML
    private TableView<ItensCompra> tbItemCompra;

    @FXML
    private TableColumn<ItensCompra, String> tcProduto;

    @FXML
    private TableColumn<ItensCompra, String> tbQuantidade;

    @FXML
    private TableColumn<ItensCompra, String> tbValor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        habilitar(false);
        limpar();
    }

    @FXML
    void novo(ActionEvent event) {
        codigo = 0;
        limpar();
        habilitar(true);
    }

    @FXML
    void editar(ActionEvent event) {
        if (tbCompra.getSelectionModel().isEmpty()) {
            return;
        }
        try {
            Compra selecionado = tbCompra.getSelectionModel().getSelectedItem();
            codigo = selecionado.getCodigo();
            // tfValorTotal.setText(selecionado.getValorTotal());

        } catch (Exception e) {
        }

    }

    @FXML
    void salvar(ActionEvent event) {

        try {
            Compra compra = new Compra();
            compra.setCodigo(codigo);
            //compra.setData(Converter.converterData(dpData.getValue()));

            if (rbPendente.isSelected()) {
                compra.setStatus("P");
            } else if (rbCancelado.isSelected()) {
                compra.setStatus("C");
            } else {
                compra.setStatus("F");
            }

            ItensCompra itens = new ItensCompra();
            itens.setProduto(cbProduto.getValue());
            itens.setQuantidade(tfValor.getLength());
            itens.setValorUnitario(tfValorTotal.getLength());
            
            Alerta.sucesso("Compra realizada com sucesso.");

            
            limpar();
            habilitar(false);

        
        } catch (Exception e) {
            Alerta.erro("Erro ao salvar.", e);
            e.printStackTrace();
        }

    }

    @FXML
    void cancelar(ActionEvent event) {
        habilitar(false);
        limpar();
    }

    @FXML
    void removerItem(ActionEvent event) {

    }

    @FXML
    void adicionalItem(ActionEvent event) {

    }

    private void limpar() {
        tfValorTotal.setText("");
        tfQuantidade.setText("");
        tfValor.setText("");
        dpData.setValue(null);
        rbPendente.setSelected(true);
        rbConfirmado.setSelected(true);
        rbCancelado.setSelected(true);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tpItemCompra.setDisable(!habilitar);
        tpCompra.setDisable(!habilitar);
        tfValorTotal.setDisable(!habilitar);
        tfQuantidade.setDisable(!habilitar);
        tfValor.setDisable(!habilitar);
        dpData.setDisable(!habilitar);
        rbCancelado.setDisable(!habilitar);
        rbConfirmado.setDisable(!habilitar);
        rbPendente.setDisable(!habilitar);
        

    }

}
