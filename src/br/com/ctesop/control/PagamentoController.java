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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private TableColumn<ContaPagar, String> tcContaFornecedor;

    @FXML
    private TableColumn<ContaPagar, String> tcContaData;

    @FXML
    private TableColumn<ContaPagar, String> tcContaValor;

    @FXML
    private TableColumn<ContaPagar, String> tcContaRestante;

    @FXML
    private TableColumn<ContaPagar, String> tcContaStatus;

    @FXML
    private TableView<ParcelaPagar> tbParcelaPagar;

    @FXML
    private TableColumn<ParcelaPagar, String> tcParcelaData;

    @FXML
    private TableColumn<ParcelaPagar, String> tcParcelaValor;

    @FXML
    private TableColumn<ParcelaPagar, String> tcParcelaRestante;

    @FXML
    private TableColumn<ParcelaPagar, String> tcParcelaStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcContaFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        tcContaData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        tcContaValor.setCellValueFactory(new PropertyValueFactory<>("valorFormatado"));
        tcContaRestante.setCellValueFactory(new PropertyValueFactory<>("valorRestanteFormatado"));
        tcContaStatus.setCellValueFactory(new PropertyValueFactory<>("statusFormatado"));

        tcParcelaData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        tcParcelaValor.setCellValueFactory(new PropertyValueFactory<>("valorParcelaFormatado"));
        tcParcelaRestante.setCellValueFactory(new PropertyValueFactory<>("valorRestanteFormatado"));
        tcParcelaStatus.setCellValueFactory(new PropertyValueFactory<>("statusFormatado"));

        atualizarTabelaContas();

        tbContaPagar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ContaPagar>() {
            @Override
            public void changed(ObservableValue<? extends ContaPagar> observable, ContaPagar oldValue, ContaPagar newValue) {
                atualizarTabelaParcelas();
            }
        });
    }

    @FXML
    void pagar(ActionEvent event) {
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

            try {
                NumberFormat nf = NumberFormat.getNumberInstance();
                float valorPagar = nf.parse(caixaTexto.getResult()).floatValue();

                ParcelaPagar parcela = tbParcelaPagar.getSelectionModel().getSelectedItem();
                if (valorPagar > parcela.getValorRestante()) {
                    valorPagar = parcela.getValorRestante();
                }
                Pagamento pagamento = new Pagamento();
                pagamento.setData(new Date());
                pagamento.setDescricao("Pagamento da parcela " + parcela.getCodigo());
                pagamento.setPacelaPagar(parcela);
                pagamento.setValor(valorPagar);
                pagamento.setStatus("P");

                PagamentoDAO.pagar(pagamento);
            } catch (Exception e) {
                Alerta.alerta("Valor invalido");
            }

            atualizarTabelaContas();
            atualizarTabelaParcelas();
        } catch (Exception e) {
            Alerta.alerta(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void cancelar(ActionEvent event
    ) {
        //fecha janela
        ((Stage) tbContaPagar.getScene().getWindow()).close();
    }

    private void atualizarTabelaContas() {
        try {
            tbContaPagar.setItems(ContaPagarDAO.listar());
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
