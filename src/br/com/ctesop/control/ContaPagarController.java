package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.dao.Conexao;
import br.com.ctesop.dao.ContaPagarDAO;
import br.com.ctesop.model.Compra;
import br.com.ctesop.model.ContaPagar;
import br.com.ctesop.model.ParcelaPagar;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class ContaPagarController implements Initializable {

    private int codigo = 0;
    private Conexao conexao;
    private Compra compra;

    @FXML
    private Button btnNovo;

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
    private TableView<ParcelaPagar> tbContaPagar;

    @FXML
    private TableColumn<ParcelaPagar, String> tcDatavencimento;

    @FXML
    private TableColumn<ParcelaPagar, String> tcValorParcela;

    @FXML
    private TableColumn<ParcelaPagar, String> tcStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcDatavencimento.setCellValueFactory(new PropertyValueFactory<>("DataFormatada"));
        tcValorParcela.setCellValueFactory(new PropertyValueFactory<>("valorParcela"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tfQuantParcelas.setDisable(true);
        limpar();
        habilitar(false);
        
          tfQuantParcelas.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    gerarParcelas(null);
                } catch (Exception ex) {
                    Logger.getLogger(ContaPagarController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    @FXML
    void novo(ActionEvent event) {
        codigo = 0;
        limpar();
        habilitar(true);

    }

    @FXML
    void salvar(ActionEvent event) {
        try {

            ContaPagar contaPagar = new ContaPagar();
            contaPagar.setCodigo(codigo);
            contaPagar.setData(Converter.converterData(dpData.getValue()));
            contaPagar.setDescricao(tfDescricao.getText());

            if (rbPrazo.isSelected()) {
                contaPagar.setFormaPagamento("P");
                contaPagar.setStatus("A");
            } else {
                contaPagar.setFormaPagamento("V");

            }
            ContaPagarDAO.salvar(contaPagar, conexao);
            gerarParcelas(event);

            Alerta.sucesso("Conta pagar salva com sucesso.");

            limpar();
            habilitar(false);

        } catch (Exception e) {
            Alerta.erro("Erro ao salvar", e);
            e.printStackTrace();
        }
    }

    @FXML
    void cancelar(ActionEvent event) throws Throwable {
        finalize();
    }

    @FXML
    void trocaFormaPagamento(ActionEvent event) throws Exception {
        if (rbVista.isSelected()) {
            tfQuantParcelas.setText("1");
            tfQuantParcelas.setDisable(true);

            gerarParcelas(null);
        } else {
            tfQuantParcelas.setDisable(false);
        }

    }

    @FXML
    void gerarParcelas(ActionEvent event) throws Exception {
        try {
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);

            Calendar cal = Calendar.getInstance();

            float valorConta = nf.parse(tfValor.getText()).floatValue();
            int quantidadeParcelas = nf.parse(tfQuantParcelas.getText()).intValue();
            float valorParcela = nf.parse(nf.format(valorConta / quantidadeParcelas)).floatValue();
            Date dataVencimento = Converter.converterData(dpData.getValue());

            ObservableList<ParcelaPagar> parcelas = FXCollections.observableArrayList();

            for (int numeroParcela = 1; numeroParcela <= quantidadeParcelas; numeroParcela++) {
                ParcelaPagar parcela = new ParcelaPagar();
                parcela.setData(dataVencimento);
                parcela.setStatus("A");
                parcela.setValorParcela(valorParcela);
                if (numeroParcela == quantidadeParcelas) {
                    parcela.setValorParcela(valorConta - (valorParcela * (numeroParcela - 1)));
                }
                parcelas.add(parcela);

                cal.setTime(dataVencimento);
                cal.add(Calendar.MONTH, 1);
                dataVencimento = cal.getTime();

            }
            tbContaPagar.setItems(parcelas);
            tbContaPagar.refresh();

            if (quantidadeParcelas > 1) {
                rbPrazo.setSelected(true);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void limpar() {
        tfDescricao.setText("");
        tfQuantParcelas.setText("");
        dpData.setValue(null);
        rbVista.setSelected(true);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tfDescricao.setDisable(!habilitar);
        dpData.setDisable(!habilitar);
        rbPrazo.setDisable(!habilitar);
        rbVista.setDisable(!habilitar);
    }

    public Conexao getConexao() {
        return conexao;
    }

    public void setConexao(Conexao conexao) {
        this.conexao = conexao;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {

        this.compra = compra;
        tfValor.setText(compra.getValorTotalFormatado());
    }

}
