package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.dao.ProdutoDAO;
import br.com.ctesop.dao.SafraDAO;
import br.com.ctesop.dao.TipoSafraDAO;
import br.com.ctesop.model.Produto;
import br.com.ctesop.model.Safra;
import br.com.ctesop.model.TipoSafra;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class CadastroSafraController implements Initializable {

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
    private Button btnCadastroTipoSafra;

    @FXML
    private ComboBox<Produto> cbProduto;

    @FXML
    private ComboBox<TipoSafra> cbTipoSafra;

    @FXML
    private DatePicker dpDataInicio;

    @FXML
    private DatePicker dpDataTermino;

    @FXML
    private RadioButton rbAtivo;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton rbInativo;

    @FXML
    private TableView<Safra> tbSafra;

    @FXML
    private TableColumn<Safra, TipoSafra> tcTipoSafra;

    @FXML
    private TableColumn<Safra, String> tcStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcTipoSafra.setCellValueFactory(new PropertyValueFactory<>("tipoSafra"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        carregarComboProduto();
        carregarComboTipoSafra();
        habilitar(false);
        atualizarTabela();
    }

    @FXML
    void novo(ActionEvent event) {
        codigo = 0;
        limpar();
        habilitar(true);

    }

    @FXML
    void editar(ActionEvent event) {
        if (tbSafra.getSelectionModel().isEmpty()) {
            return;
        }

        Safra selecionado = tbSafra.getSelectionModel().getSelectedItem();
        codigo = selecionado.getCodigo();
        cbProduto.getSelectionModel().select(selecionado.getProduto());
        cbTipoSafra.getSelectionModel().select(selecionado.getTipoSafra());
        dpDataInicio.setValue(Converter.toLocalDate(selecionado.getDataInicio()));
        dpDataTermino.setValue(Converter.toLocalDate(selecionado.getDataTermino()));
        if (selecionado.getStatus().equalsIgnoreCase("A")) {
            rbAtivo.setSelected(true);
        } else {
            rbInativo.setSelected(true);
        }
        habilitar(true);
    }

    @FXML
    void cancelar(ActionEvent event) {
        habilitar(false);
        limpar();
    }

    @FXML
    void salvar(ActionEvent event) {
        try {
            Safra safra = new Safra();
            safra.setCodigo(codigo);
            safra.setProduto(cbProduto.getSelectionModel().getSelectedItem());
            safra.setTipoSafra(cbTipoSafra.getSelectionModel().getSelectedItem());
            safra.setDataInicio(Converter.converterData(dpDataInicio.getValue()));
            safra.setDataTermino(Converter.converterData(dpDataTermino.getValue()));

            if (rbAtivo.isSelected()) {
                safra.setStatus("A");
            } else {
                safra.setStatus("I");
            }

            SafraDAO.salvar(safra);

            Alerta.sucesso("Safra salva com sucesso.");

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

    private void atualizarTabela() {
        try {
            tbSafra.setItems(SafraDAO.listar(false));
            tbSafra.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }

    private void limpar() {
        cbProduto.getSelectionModel().select(null);
        cbTipoSafra.getSelectionModel().select(null);
        dpDataInicio.setValue(null);
        dpDataTermino.setValue(null);
        rbAtivo.setSelected(true);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        btnCadastroProduto.setDisable(!habilitar);
        btnCadastroTipoSafra.setDisable(!habilitar);
        cbProduto.setDisable(!habilitar);
        cbTipoSafra.setDisable(!habilitar);
        dpDataInicio.setDisable(!habilitar);
        dpDataTermino.setDisable(!habilitar);

        rbAtivo.setDisable(!habilitar);
        rbInativo.setDisable(!habilitar);
    }

    private void carregarComboProduto() {
        try {
            cbProduto.setItems(ProdutoDAO.listar(true));
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }

    }

    private void carregarComboTipoSafra() {
        try {
            cbTipoSafra.setItems(TipoSafraDAO.listar(true));
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);

        }

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

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    carregarComboProduto();
                }
            });
        } catch (Exception e) {
            Alerta.erro("Erro ao abrir Cadastro de Produto.", e);
        }
    }
   

    @FXML
    void cadastrarTipoSafra(ActionEvent event) {
        try {
            String url = "/br/com/ctesop/view/CadastroTipoSafra.fxml";
            Scene scene = new Scene(new FXMLLoader(getClass().getResource(url)).load());
            Stage stage = new Stage();
            stage.setTitle("Cadastro Tipo de Safra");
            stage.setScene(scene);
            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    carregarComboProduto();
                }
            });
        } catch (Exception e) {
            Alerta.erro("Erro ao abrir Cadastro de Tipo Safra.", e);
        }
    }
}
