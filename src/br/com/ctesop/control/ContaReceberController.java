/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.dao.Conexao;
import br.com.ctesop.dao.ContaReceberDAO;
import br.com.ctesop.model.ContaReceber;
import br.com.ctesop.model.EntregaProducao;
import br.com.ctesop.model.ParcelaPagar;
import br.com.ctesop.model.ParcelaReceber;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class ContaReceberController implements Initializable {

    private int codigo = 0;
    private Conexao conexao;
    private EntregaProducao entregaProducao;

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<EntregaProducao> cbEntregaProducao;

    @FXML
    private DatePicker dpDataReceber;

    @FXML
    private TextField tfValor;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfQuantidadeParcela;

    @FXML
    private RadioButton rbPrazo;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton rbVista;

    @FXML
    private TableView<ParcelaReceber> tbContaReceber;

    @FXML
    private TableColumn<ParcelaReceber, String> tcCooperativa;

    @FXML
    private TableColumn<ParcelaReceber, String> tcDataPagamento;

    @FXML
    private TableColumn<ParcelaReceber, String> tcValor;

    @FXML
    private TableColumn<ParcelaReceber, String> tcStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcCooperativa.setCellValueFactory(new PropertyValueFactory<>("cooperativa"));
        tcDataPagamento.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valorFormatado"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        limpar();
        habilitar(false);
    }

    @FXML
    void novo(ActionEvent event) {
        codigo = 0;
        habilitar(true);
    }

    @FXML
    void salvar(ActionEvent event) {
        try {
            ContaReceber contaRecebimento = new ContaReceber();

            contaRecebimento.setCodigo(codigo);
            contaRecebimento.setData(Converter.converterData(dpDataReceber.getValue()));
            contaRecebimento.setValor(tfValor.getText());
            contaRecebimento.setDecricao(tfDescricao.getText());
            contaRecebimento.setEntregaProducao(entregaProducao);

            if (rbPrazo.isSelected()) {
                contaRecebimento.setFormaRecebimento("P");
                contaRecebimento.setStatus("A");
            } else {
                contaRecebimento.setFormaRecebimento("V");
                contaRecebimento.setStatus("P");
            }

            List parcelas = tbContaReceber.getItems();

            ContaReceberDAO.inserir(contaRecebimento, parcelas, conexao);

            //Fechar janela
            ((Stage) dpDataReceber.getScene().getWindow()).close();

        } catch (Exception e) {
            Alerta.erro("Erro ao salvar", e);
            e.printStackTrace();
        }
    }

    @FXML
    void cancelar(ActionEvent event) throws Throwable {
        ((Stage) dpDataReceber.getScene().getWindow()).close();
    }
    
    @FXML
    void gerarParcelas(ActionEvent event) throws Exception {
        try {
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);

            Calendar cal = Calendar.getInstance();

            float valorReceber = nf.parse(tfValor.getText()).floatValue();
            int quantidadeParcelas = nf.parse(tfQuantidadeParcela.getText()).intValue();
            float valorParcela = Math.round((valorReceber / quantidadeParcelas) * 100) / 100.0f;
            Date dataVencimento = Converter.converterData(dpDataReceber.getValue());

            ObservableList<ParcelaReceber> parcelas = FXCollections.observableArrayList();

            for (int numeroParcela = 1; numeroParcela <= quantidadeParcelas; numeroParcela++) {
                ParcelaReceber parcela = new ParcelaReceber();
                parcela.setDataVencimento(dataVencimento);
                parcela.setStatus("A");
                parcela.setValor(valorParcela);
                if (numeroParcela == quantidadeParcelas) {
                    parcela.setValor(valorReceber - (valorParcela * (numeroParcela - 1)));
                }
                parcelas.add(parcela);

                cal.setTime(dataVencimento);
                cal.add(Calendar.MONTH, 1);
                dataVencimento = cal.getTime();

            }
            tbContaReceber.setItems(parcelas);
            tbContaReceber.refresh();

            if (quantidadeParcelas > 1) {
                rbPrazo.setSelected(true);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void limpar() {
        tfValor.setText("");
        tfDescricao.setText("");
        dpDataReceber.setValue(null);
        rbVista.setSelected(true);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tfDescricao.setDisable(!habilitar);
        tfValor.setDisable(!habilitar);
        dpDataReceber.setDisable(!habilitar);
        rbPrazo.setDisable(!habilitar);
        rbVista.setDisable(!habilitar);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Conexao getConexao() {
        return conexao;
    }

    public void setConexao(Conexao conexao) {
        this.conexao = conexao;
    }

    public EntregaProducao getEntregaProducao() {
        return entregaProducao;
    }

    public void setEntregaProducao(EntregaProducao entregaProducao) {
        this.entregaProducao = entregaProducao;
    }

}
