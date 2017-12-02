package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.dao.Conexao;
import br.com.ctesop.dao.ContaReceberDAO;
import br.com.ctesop.dao.CooperativaDAO;
import br.com.ctesop.dao.EntregaProducaoDAO;
import br.com.ctesop.dao.SafraDAO;
import br.com.ctesop.model.Cooperativa;
import br.com.ctesop.model.EntregaProducao;
import br.com.ctesop.model.Safra;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class EntregaProducaoController implements Initializable {

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
    private ComboBox<Cooperativa> cbCooperativa;

    @FXML
    private Button btnCadastroCooperativa;

    @FXML
    private DatePicker dpData;

    @FXML
    private ComboBox<Safra> cbSafra;

    @FXML
    private Button btnCadastroSafra;

    @FXML
    private TextField tfQuantidadeEntregue;

    @FXML
    private TextField tfDesconto;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfValor;

    @FXML
    private RadioButton rbPendente;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton rbCancelada;

    @FXML
    private RadioButton rbEntregue;

    @FXML
    private RadioButton rbVendido;
    @FXML
    private TableView<EntregaProducao> tbEntregaProducao;

    @FXML
    private TableColumn<Cooperativa, String> tcCooperativa;

    @FXML
    private TableColumn<EntregaProducao, String> tcData;

    @FXML
    private TableColumn<EntregaProducao, String> tcQuantidade;

    @FXML
    private TableColumn<EntregaProducao, String> tcDesconto;

    @FXML
    private TableColumn<EntregaProducao, String> tcStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcCooperativa.setCellValueFactory(new PropertyValueFactory<>("cooperativa"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("dataFormatada"));
        tcDesconto.setCellValueFactory(new PropertyValueFactory<>("descontoFormatado"));
        tcQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadeFormatada"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        carregarComboCooperativa();
        carregarComboSafra();
        atualizarTabela();
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
        if (tbEntregaProducao.getSelectionModel().isEmpty()) {
            return;
        }
        try {
            EntregaProducao selecionado = tbEntregaProducao.getSelectionModel().getSelectedItem();

            if (selecionado.getStatus().equalsIgnoreCase("V")) {
                Alerta.alerta("Entrega de produto já vendida!");
                return;
            }
            if (selecionado.getStatus().equalsIgnoreCase("C")) {
                Alerta.alerta("Entrega de produto cancelada!");
                return;
            }
 
             
            codigo = selecionado.getCodigo();
            dpData.setValue(Converter.toLocalDate(selecionado.getData()));
            tfDesconto.setText(selecionado.getDescontoFormatado());
            tfDescricao.setText(selecionado.getDescricao());
            tfValor.setText(selecionado.getValorFormatado());
            tfQuantidadeEntregue.setText(selecionado.getQuantidadeFormatada());
            cbCooperativa.getSelectionModel().select(selecionado.getCooperativa());
            cbSafra.getSelectionModel().select(selecionado.getSafra());

            if (selecionado.getStatus().equalsIgnoreCase("P")) {
                rbPendente.setSelected(true);
            } else if (selecionado.getStatus().equalsIgnoreCase("V")) {
                rbVendido.setSelected(true);
            } else if (selecionado.getStatus().equalsIgnoreCase("E")) {
                rbEntregue.setSelected(true);
            } else {
                rbCancelada.setSelected(true);
            }

            habilitar(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void salvar(ActionEvent event) {
        Conexao c;
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            EntregaProducao entregaProducao = new EntregaProducao();
            entregaProducao.setCodigo(codigo);
            entregaProducao.setCooperativa(cbCooperativa.getSelectionModel().getSelectedItem());
            entregaProducao.setSafra(cbSafra.getSelectionModel().getSelectedItem());
            entregaProducao.setDesconto(nf.parse(tfDesconto.getText()).floatValue());
            entregaProducao.setQuantidadeEntregue(nf.parse(tfQuantidadeEntregue.getText()).intValue());
            entregaProducao.setValor(nf.parse(tfValor.getText()).floatValue());
            entregaProducao.setData(Converter.converterData(dpData.getValue()));

            if (entregaProducao.getData() == null) {
                throw new Exception("Data inválida.");
            }

            entregaProducao.setCooperativa(cbCooperativa.getSelectionModel().getSelectedItem());
            entregaProducao.setSafra(cbSafra.getSelectionModel().getSelectedItem());

            if (rbPendente.isSelected()) {
                entregaProducao.setStatus("P");
            } else if (rbVendido.isSelected()) {
                entregaProducao.setStatus("V");
            } else if (rbEntregue.isSelected()) {
                entregaProducao.setStatus("E");
            } else {
                entregaProducao.setStatus("C");
            }

            c = new Conexao();

            int codigo = EntregaProducaoDAO.salvar(entregaProducao, c);
            entregaProducao.setCodigo(codigo);

            if (entregaProducao.getStatus().equalsIgnoreCase("V")) {

                contaReceber(c, entregaProducao);

                if (!ContaReceberDAO.gerouConta(entregaProducao.getCodigo(), c)) {
                    throw new Exception("Não gerou conta a receber");
                }

            }

            c.confirmar();
            limpar();
            atualizarTabela();
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

    public void contaReceber(Conexao c, EntregaProducao entregaProducao) throws Exception {
        String url = "/br/com/ctesop/view/ContaReceber.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent root = loader.load();
        ContaReceberController controler = loader.getController();
        controler.setConexao(c);
        controler.setEntregaProducao(entregaProducao);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Conta Receber");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void cadastroCooperativa(ActionEvent event) {
        try {
            String url = "/br/com/ctesop/view/CadastroCooperativa.fxml";
            Scene scene = new Scene(new FXMLLoader(getClass().getResource(url)).load());
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Cooperativa");
            stage.setScene(scene);
            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    carregarComboCooperativa();
                }
            });
        } catch (Exception e) {
            Alerta.erro("Erro ao abrir cadastro de cooperativa.", e);
        }
    }

    @FXML
    void cadastroSafra(ActionEvent event) {
        try {
            String url = "/br/com/ctesop/view/CadastroSafra.fxml";
            Scene scene = new Scene(new FXMLLoader(getClass().getResource(url)).load());
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Safra");
            stage.setScene(scene);
            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    carregarComboSafra();
                }
            });
        } catch (Exception e) {
            Alerta.erro("Erro ao abrir cadastro de safra.", e);
        }
    }

    private void limpar() {
        tfDesconto.setText("");
        tfDescricao.setText("");
        tfQuantidadeEntregue.setText("");
        tfValor.setText("");
        cbCooperativa.setValue(null);
        cbSafra.setValue(null);
        dpData.setValue(null);
        rbPendente.setSelected(false);
        rbCancelada.setSelected(false);
        rbEntregue.setSelected(true);
        rbVendido.setSelected(false);

    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        cbCooperativa.setDisable(!habilitar);
        cbSafra.setDisable(!habilitar);
        btnCadastroCooperativa.setDisable(!habilitar);
        btnCadastroSafra.setDisable(!habilitar);
        tfDesconto.setDisable(!habilitar);
        tfDescricao.setDisable(!habilitar);
        tfQuantidadeEntregue.setDisable(!habilitar);
        tfValor.setDisable(!habilitar);
        dpData.setDisable(!habilitar);
        rbCancelada.setDisable(!habilitar);
        rbEntregue.setDisable(!habilitar);
        rbVendido.setDisable(!habilitar);
        rbPendente.setDisable(!habilitar);
    }

    private void carregarComboCooperativa() {
        try {
            cbCooperativa.setItems(CooperativaDAO.listar(true));
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }

    private void carregarComboSafra() {
        try {
            cbSafra.setItems(SafraDAO.listar(true));
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }                                              
    }

    private void atualizarTabela() {
        try {
            tbEntregaProducao.setItems(EntregaProducaoDAO.listar());
            tbEntregaProducao.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
            e.printStackTrace();
        }
    }

}
