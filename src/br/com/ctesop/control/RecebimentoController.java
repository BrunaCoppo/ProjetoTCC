package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.dao.ContaPagarDAO;
import br.com.ctesop.dao.PagamentoDAO;
import br.com.ctesop.dao.ParcelaPagarDAO;
import br.com.ctesop.model.ContaPagar;
import br.com.ctesop.model.Pagamento;
import br.com.ctesop.model.ParcelaPagar;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class RecebimentoController implements Initializable {

 @FXML
    private Button btnReceber;

    @FXML
    private Button btnCancelar;

    @FXML
    private TableView<?> tcEntregaProducao;

    @FXML
    private TableColumn<?, ?> tcCooperativa;

    @FXML
    private TableColumn<?, ?> tcValorEntrega;

    @FXML
    private TableColumn<?, ?> tcDataEntrega;

    @FXML
    private TableColumn<?, ?> tcStatus;

    @FXML
    private TableView<?> tcParcelaReceber;

    @FXML
    private TableColumn<?, ?> tcDataPagamento;

    @FXML
    private TableColumn<?, ?> tcValorParcela;

    @FXML
    private TableColumn<?, ?> tcRestante;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void receber(ActionEvent event) {
 try {

            if (tbContaPagar.getSelectionModel().isEmpty()) {
                Alerta.alerta("Selecione uma conta a pagar");
                return;
            }
            if (tbParcelaPagar.getSelectionModel().isEmpty()) {
                Alerta.alerta("Selecione uma parcela a pagar");
                return;
            }

            TextInputDialog caixaTexto = new TextInputDialog("0,00");
            caixaTexto.setHeaderText("Pagamento");
            caixaTexto.setContentText("Informe o valor a pagar:");
            caixaTexto.showAndWait();

            NumberFormat nf = NumberFormat.getNumberInstance();
            float valorPagar = nf.parse(caixaTexto.getResult()).floatValue();
            
            ParcelaPagar parcela = tbParcelaPagar.getSelectionModel().getSelectedItem();
            if(valorPagar > parcela.getValorRestante()){
                valorPagar = parcela.getValorRestante();
            }
            
            
            Pagamento pagamento = new Pagamento();
            pagamento.setData(new Date());
            pagamento.setDescricao("Pagamento da parcela " + parcela.getCodigo());
            pagamento.setPacelaPagar(parcela);
            pagamento.setValor(valorPagar);
            pagamento.setStatus("P");
            
            PagamentoDAO.pagar(pagamento);
            
            atualizarTabelaContas();
            atualizarTabelaParcelas();
        } catch (Exception e) {
            Alerta.alerta(e.getMessage());
            e.printStackTrace();
        }
    }
        private void atualizarTabelaEntrega() {
        try {
            t.setItems(ContaPagarDAO.listar());
            tbContaPagar.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
            e.printStackTrace();
        }
    }

    private void atualizarTabelaParcelas() {
        try {
            if (tbContaPagar.getSelectionModel().isEmpty()) {
                return;
            }
            ContaPagar cp = tbContaPagar.getSelectionModel().getSelectedItem();
            tbParcelaPagar.setItems(ParcelaPagarDAO.listar(cp));
            tbParcelaPagar.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
            e.printStackTrace();
        }
    }
}
