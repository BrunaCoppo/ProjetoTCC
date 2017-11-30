package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class MenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void cidade(ActionEvent event) {
        abrir("CadastroCidade", "Cadastro de Cidades");
    }

    @FXML
    public void estado(ActionEvent event) {
        abrir("CadastroEstado", "Cadastro de Estados");
    }

    @FXML
    public void produto(ActionEvent event) {
        abrir("CadastroProduto", "Cadastro de Produtos");
    }

    @FXML
    public void cooperativa(ActionEvent event) {
        abrir("CadastroCooperativa", "Cadastro de Cooperativas");
    }

    @FXML
    public void terra(ActionEvent event) {
        abrir("CadastroTerra", "Cadastro de Terras");
    }

    @FXML
    public void tipoManejo(ActionEvent event) {
        abrir("CadastroTipoManejo", "Cadastro de Tipos de Manejo");
    }

    @FXML
    public void tipoSafra(ActionEvent event) {
        abrir("CadastroTipoSafra", "Cadastro de Tipos de Safra");
    }

    @FXML
    public void funcionario(ActionEvent event) {
        abrirPessoa("CadastroPessoa", "Cadastro de Funcionários", "Funcionario");
    }

    @FXML
    public void fornecedor(ActionEvent event) {
        abrirPessoa("CadastroPessoa", "Cadastro de Fornecedores", "Fornecedor");
    }

    @FXML
    public void proprietario(ActionEvent event) {
        abrirPessoa("CadastroPessoa", "Cadastro de Proprietários", "Proprietario");
    }

    @FXML
    public void safra(ActionEvent event) {
        abrir("CadastroSafra", "Cadastro de Safras");
    }

    @FXML
    public void caixa(ActionEvent event) {
        abrir("Caixa", "Caixa");
    }

    @FXML
    public void compra(ActionEvent event) {
        abrir("Compra", "Compra");
    }

    @FXML
    public void pagamento(ActionEvent event) {
        abrir("Pagamento", "Pagamento");
    }

    @FXML
    public void entregaProducao(ActionEvent event) {
        abrir("EntregaProducao", "Entrega Produção");
    }

    @FXML
    public void recebimento(ActionEvent event) {
        abrir("Recebimento", "Recebimento");
    }

    private void abrir(String arquivo, String titulo) {
        try {
            String url = "/br/com/ctesop/view/" + arquivo + ".fxml";
            Scene scene = new Scene(new FXMLLoader(getClass().getResource(url)).load());
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alerta.erro("Erro ao abrir " + titulo + ".", e);
            e.printStackTrace();
        }
    }

    private void abrirPessoa(String arquivo, String titulo, String tipo) {
        try {
            String url = "/br/com/ctesop/view/" + arquivo + ".fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Parent root = loader.load();

            CadastroPessoaController controller = loader.getController();
            controller.setTipo(tipo);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alerta.erro("Erro ao abrir " + titulo + ".", e);
            e.printStackTrace();
        }
    }
}
