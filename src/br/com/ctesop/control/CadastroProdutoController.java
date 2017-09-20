package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.dao.ProdutoDAO;
import br.com.ctesop.model.Produto;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
public class CadastroProdutoController implements Initializable {

    private int codigo = 0;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfDescricao;
   
    @FXML
    private TextField tfQuantidade;

    @FXML 
    private CheckBox cbProduzido;
    
    @FXML
    private RadioButton rbAtivo;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton rbInativo;

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TableView<Produto> tbProduto;

    @FXML
    private TableColumn<Produto, String> tcNome;

    @FXML
    private TableColumn<Produto, String> tcDescricao;

    @FXML
    private TableColumn<Produto, String> tcStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        atualizarTabela();
        habilitar(false);
        
    }

    @FXML
    void novo(ActionEvent event) {
        codigo = 0;
        limpar();
        habilitar(true);

    }

    @FXML
    void editar(ActionEvent event) {
        if (tbProduto.getSelectionModel().isEmpty()) {
            return;
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        Produto selecionado = tbProduto.getSelectionModel().getSelectedItem();
        codigo = selecionado.getCodigo();
        tfNome.setText(selecionado.getNome());
        tfQuantidade.setText(nf.format(selecionado.getQuantidade()));
        tfDescricao.setText(selecionado.getDescricao());
        if (selecionado.getStatus().equalsIgnoreCase("A")) {
            rbAtivo.setSelected(true);
        } else {
            rbInativo.setSelected(true);
        }
        if(selecionado.getProduzido().equalsIgnoreCase("S")){
            cbProduzido.setSelected(true);
        } else {
            cbProduzido.setSelected(false);
        }
        habilitar(true);
    }

    @FXML
    void salvar(ActionEvent event) throws ExceptionValidacao {
        try {
            Produto produto = new Produto();
            produto.setCodigo(codigo);
            produto.setNome(tfNome.getText());
            produto.setDescricao(tfDescricao.getText());

            if (rbAtivo.isSelected()) {
                produto.setStatus("A");
            } else {
                produto.setStatus("I");
            }
            
            if(cbProduzido.isSelected()){
                produto.setProduzido("S");
            } else {
                produto.setProduzido("N");
            }

            ProdutoDAO.salvar(produto);

            Alerta.sucesso("Produto salvo com sucesso.");

            atualizarTabela();
            limpar();
            habilitar(false);
        } catch (ExceptionValidacao e) {
            Alerta.alerta("Erro ao salvar.", e);
        } catch (Exception e) {
            Alerta.erro("Erro ao salvar.", e);
            
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        habilitar(false);
        limpar();
    }

    private void atualizarTabela() {
        try {
            tbProduto.setItems(ProdutoDAO.listar(false));
            tbProduto.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
           
        }
    }

    private void limpar() {
        tfNome.setText("");
        tfQuantidade.setText("");
        tfDescricao.setText("");
        rbAtivo.setSelected(true);
        cbProduzido.setSelected(false);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tfNome.setDisable(!habilitar);
        tfQuantidade.setDisable(!habilitar);
        tfDescricao.setDisable(!habilitar);
        rbAtivo.setDisable(!habilitar);
        rbInativo.setDisable(!habilitar);
        cbProduzido.setDisable(!habilitar);
    }
}
