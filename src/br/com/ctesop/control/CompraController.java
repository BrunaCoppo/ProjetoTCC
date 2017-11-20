package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.dao.CompraDAO;
import br.com.ctesop.dao.Conexao;
import br.com.ctesop.dao.ProdutoDAO;
import br.com.ctesop.model.Compra;
import br.com.ctesop.model.ItensCompra;
import br.com.ctesop.model.Produto;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
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
import javafx.scene.control.Tab;
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
    private Button btnCadastroProduto;

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
    private TextField tfValorUnitario;

    @FXML
    private TableView<ItensCompra> tbItemCompra;

    @FXML
    private TableColumn<ItensCompra, String> tcProduto;

    @FXML
    private TableColumn<ItensCompra, String> tcQuantidade;

    @FXML
    private TableColumn<ItensCompra, String> tcValorUnitario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tcProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        tcQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tcValorUnitario.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        tbItemCompra.refresh();

        tcValor.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("DataFormatada"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        carregarComboProduto();
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
            dpData.setValue(Converter.toLocalDate(selecionado.getData()));
            tfValorTotal.setText(selecionado.getValorTotalFormatado());

        } catch (Exception e) {
        }

    }

    @FXML
    void salvar(ActionEvent event) {

        try {
            Compra compra = new Compra();
            compra.setCodigo(codigo);
            compra.setData(Converter.converterData(dpData.getValue()));

            if (compra.getData() == null) {
                throw new Exception("Data inválida.");

            }

            if (rbPendente.isSelected()) {
                compra.setStatus("P");
            } else if (rbCancelado.isSelected()) {
                compra.setStatus("C");
            } else {
                compra.setStatus("F");
            }

            ArrayList<ItensCompra> itens = new ArrayList<>();
            for (ItensCompra ic : tbItemCompra.getItems()) {
                ic.setCompra(compra);
                itens.add(ic);

            }

            compra.setItens(itens);

            if (itens.isEmpty()) {
                throw new Exception("Compra sem itens.");
            }

            Conexao c = new Conexao();

            CompraDAO.salvar(compra, c);

            if (compra.getStatus().equalsIgnoreCase("F")) {

                for (ItensCompra ic : tbItemCompra.getItems()) {

                    contaPagar(c, compra);

                    Produto produto = ic.getProduto();
                    produto.setQuantidade(ic.getQuantidade());
                    ProdutoDAO.alterarQuantidade(produto, c);

                }

            }

            c.confirmar();

            Alerta.sucesso("Compra realizada com sucesso.");

            limpar();
            habilitar(false);

        } catch (Exception e) {
            Alerta.erro("Erro ao salvar.", e);
            e.printStackTrace();
        }

    }

    public void contaPagar(Conexao c, Compra compra) throws Exception {
        String url = "/br/com/ctesop/view/ContaPagar.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent root = loader.load();//Faz carregar o arquivo FXML
        ContaPagarController controler = loader.getController();
        controler.setConexao(c);
        controler.setCompra(compra);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Conta Pagar");
        stage.setScene(scene);
        stage.showAndWait();//Abrir a janela e esperar

    }

    @FXML
    void cancelar(ActionEvent event) {
        habilitar(false);
        limpar();
    }

    @FXML
    void removerItem(ActionEvent event) {
        ItensCompra ic = new ItensCompra();
        tbItemCompra.getItems().remove(ic);
        tbItemCompra.refresh();
    }

    @FXML
    void adicionarItem(ActionEvent event) throws Exception {

        NumberFormat nf = NumberFormat.getNumberInstance();

        ItensCompra ic = new ItensCompra();
        ic.setProduto(cbProduto.getSelectionModel().getSelectedItem());
        ic.setQuantidade(nf.parse(tfQuantidade.getText()).intValue());
        ic.setValorUnitario(nf.parse(tfValorUnitario.getText()).floatValue());

        tbItemCompra.getItems().add(ic);
        tbItemCompra.refresh();
        double total = 0;

        for (ItensCompra ic2 : tbItemCompra.getItems()) {
            total += ic2.getSubtotal();

        }

        tfValorTotal.setText(nf.format(total));
        limparItens();

    }

    @FXML
    public void cadastroProduto(ActionEvent event) {
        try {
            String url = "/br/com/ctesop/view/CadastroProduto.fxml";
            Scene scene = new Scene(new FXMLLoader(getClass().getResource(url)).load());
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Produto");
            stage.setScene(scene);
            stage.show();
            //Carregar o produto na compra alterado
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    carregarComboProduto();
                }
            });
        } catch (Exception e) {
            Alerta.erro("Erro ao abrir cadastro de produto.", e);
        }
    }

    private void limpar() {
        tfValorTotal.setText("");
        tfQuantidade.setText("");
        tfValorUnitario.setText("");
        cbProduto.setValue(null);
        dpData.setValue(null);
        rbPendente.setSelected(false);
        rbConfirmado.setSelected(true);
        rbCancelado.setSelected(false);
        tbItemCompra.getItems().clear();
    }

    private void limparItens() {
        tfQuantidade.setText("");
        tfValorUnitario.setText("");
        cbProduto.setValue(null);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tpItemCompra.setDisable(!habilitar);
        tpCompra.setDisable(!habilitar);
        tfValorTotal.setDisable(!habilitar);
        dpData.setDisable(!habilitar);
        rbCancelado.setDisable(!habilitar);
        rbConfirmado.setDisable(!habilitar);
        rbPendente.setDisable(!habilitar);
        tfQuantidade.setDisable(!habilitar);
        tfValorUnitario.setDisable(!habilitar);
        cbProduto.setDisable(!habilitar);
        btnCadastroProduto.setDisable(!habilitar);

    }

    private void carregarComboProduto() {
        try {
            cbProduto.setItems(ProdutoDAO.listar(true));
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }
}
