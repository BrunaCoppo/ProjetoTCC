package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.dao.ContaReceberDAO;
import br.com.ctesop.dao.ParcelaReceberDAO;
import br.com.ctesop.dao.RecebimentoDAO;
import br.com.ctesop.model.ContaReceber;
import br.com.ctesop.model.ParcelaReceber;
import br.com.ctesop.model.Recebimento;
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
public class RecebimentoController implements Initializable {

    @FXML
    private Button btnReceber;

    @FXML
    private Button btnCancelar;

    @FXML
    private TableView<ContaReceber> tbContaReceber;

    @FXML
    private TableColumn<ContaReceber, String> tcContaCooperativa;

    @FXML
    private TableColumn<ContaReceber, String> tcContaValor;
    
    @FXML
    private TableColumn<ContaReceber, String> tcContaRestante;

    @FXML
    private TableColumn<ContaReceber, String> tcContaData;

    @FXML
    private TableColumn<ContaReceber, String> tcStatusConta;

    @FXML
    private TableView<ParcelaReceber> tbParcelaReceber;

    @FXML
    private TableColumn<ParcelaReceber, String> tcParcelaData;

    @FXML
    private TableColumn<ParcelaReceber, String> tcParcelaValor;

    @FXML
    private TableColumn<ParcelaReceber, String> tcParcelaRestante;

    @FXML
    private TableColumn<ParcelaReceber, String> tcStatusParcela;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcContaCooperativa.setCellValueFactory(new PropertyValueFactory<>("cooperativa"));
        tcContaValor.setCellValueFactory(new PropertyValueFactory<>("valorFormatado"));
        tcContaRestante.setCellValueFactory(new PropertyValueFactory<>("valorRestanteFormatado"));
        tcContaData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        tcStatusConta.setCellValueFactory(new PropertyValueFactory<>("statusFormatado"));

        tcParcelaData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        tcParcelaValor.setCellValueFactory(new PropertyValueFactory<>("valorParcelaFormatado"));
        tcParcelaRestante.setCellValueFactory(new PropertyValueFactory<>("valorRestanteFormatado"));
        tcStatusParcela.setCellValueFactory(new PropertyValueFactory<>("statusFormatado"));

        atualizarTabelaConta();

        tbContaReceber.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ContaReceber>() {
            @Override
            public void changed(ObservableValue<? extends ContaReceber> observable, ContaReceber oldValue, ContaReceber newValue) {
                atualizarTabelaParcelas();
            }
        });
    }

    @FXML
    void cancelar(ActionEvent event) {
        ((Stage) tbContaReceber.getScene().getWindow()).close();
    }

    @FXML
    void receber(ActionEvent event) {
        try {

            if (tbContaReceber.getSelectionModel().isEmpty()) {
                Alerta.alerta("Selecione uma conta a receber");
                return;
            }
            if (tbParcelaReceber.getSelectionModel().isEmpty()) {
                Alerta.alerta("Selecione uma parcela a receber");
                return;
            }

            TextInputDialog caixaTexto = new TextInputDialog("0,00");
            caixaTexto.setHeaderText("Recebimento");
            caixaTexto.setContentText("Informe o valor a recebido:");
            caixaTexto.showAndWait();

            NumberFormat nf = NumberFormat.getNumberInstance();
            float valorReceber = nf.parse(caixaTexto.getResult()).floatValue();

            
            ParcelaReceber parcela = tbParcelaReceber.getSelectionModel().getSelectedItem();
            if (valorReceber > parcela.getValorRestante()) {
                valorReceber = parcela.getValorRestante();
            }

            Recebimento recebimento = new Recebimento();
            recebimento.setDataRecebimento(new Date());
            recebimento.setDescricao("Recebimento da parcela " + parcela.getCodigo());
            recebimento.setParcelaReceber(parcela);
            recebimento.setValorRecebimento(valorReceber);
            recebimento.setStatus("R");

            RecebimentoDAO.receber(recebimento);

            atualizarTabelaConta();
            atualizarTabelaParcelas();
        } catch (Exception e) {
            Alerta.alerta(e.getMessage());
            e.printStackTrace();
        }
    }

    private void atualizarTabelaConta() {
        try {
            tbContaReceber.setItems(ContaReceberDAO.listar());
            tbContaReceber.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
            e.printStackTrace();
        }
    }

    private void atualizarTabelaParcelas() {
        try {
            if (tbContaReceber.getSelectionModel().isEmpty()) {
                return;
            }
            ContaReceber cr = tbContaReceber.getSelectionModel().getSelectedItem();
            tbParcelaReceber.setItems(ParcelaReceberDAO.listar(cr));
            tbParcelaReceber.refresh();
        } catch (Exception e) {

            Alerta.erro("Erro ao consultar dados.", e);
            e.printStackTrace();
        }
    }
}
