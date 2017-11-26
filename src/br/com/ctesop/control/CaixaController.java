package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.dao.CaixaDAO;
import br.com.ctesop.model.Caixa;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author BrunaI
 */
public class CaixaController implements Initializable {

    private int codigo = 0;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnAbertura;

    @FXML
    private Button btnFechamento;

    @FXML
    private TextField dpAbertura;

    @FXML
    private TextField tfValorAbertura;

    @FXML
    private TextField dpFechamento;

    @FXML
    private TextField tfValorFechamento;

    @FXML
    private TableView<Caixa> tbCaixa;

    @FXML
    private TableColumn<Caixa, String> tcDataAbertura;

    @FXML
    private TableColumn<Caixa, String> tcDataFechamento;

    @FXML
    private TableColumn<Caixa, String> tcStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcDataAbertura.setCellValueFactory(new PropertyValueFactory<>("dataAberturaFormatada"));
        tcDataFechamento.setCellValueFactory(new PropertyValueFactory<>("dataFechamentoFormatada"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        atualizarTabela();
        habilitar(false);
    }

    @FXML
    void abertura(ActionEvent event) {
        codigo = 0;
        dpAbertura.setText(Converter.formatarData(new Date()));       
        habilitar(true);
    }

    @FXML
    void confirmar(ActionEvent event) {
        try {
            Caixa caixa = new Caixa();
            caixa.setCodigo(codigo);
            caixa.setValorAbertura(tfValorAbertura.getText());
            caixa.setValorFechamento(0);
            caixa.setDataAbertura(Converter.converterData(dpAbertura.getText()));
            caixa.setDataFechamento(Converter.converterData(dpFechamento.getText()));
            caixa.setStatus("A");

            CaixaDAO.confirmar(caixa);

            Alerta.sucesso("Caixa aberto com sucesso.");

            atualizarTabela();
            limpar();
            habilitar(false);
        } catch (ExceptionValidacao e) {
            Alerta.alerta("Erro ao salvar.", e);
        } catch (Exception e) {
            Alerta.erro("Erro ao salvar.", e);
            e.printStackTrace();
        }
    }

    @FXML
    void fechamento(ActionEvent event) {
        try {
            if (tbCaixa.getSelectionModel().isEmpty()) {
                return;
            }

            Caixa selecionado = tbCaixa.getSelectionModel().getSelectedItem();
            selecionado.setStatus("F");
            selecionado.setDataFechamento(new Date());
            CaixaDAO.fechamento(selecionado);
            
            habilitar(false);
            limpar();
            atualizarTabela();
        } catch (Exception e) {
            Alerta.erro("Erro ao salvar.", e);
        }
    }

    private void atualizarTabela() {
        try {
            tbCaixa.setItems(CaixaDAO.listar(false));
            tbCaixa.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }

    private void limpar() {
        tfValorAbertura.setText("");
        tfValorFechamento.setText("");
        dpAbertura.setText("");
        dpFechamento.setText("");
    }

    private void habilitar(boolean habilitar) {
        btnAbertura.setDisable(habilitar);
        btnFechamento.setDisable(habilitar);
        btnConfirmar.setDisable(!habilitar);
        tfValorAbertura.setDisable(!habilitar);
        tfValorFechamento.setDisable(!habilitar);
        dpAbertura.setDisable(!habilitar);
        dpFechamento.setDisable(!habilitar);
    }

}
