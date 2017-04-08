package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    void produto(ActionEvent event) {
        abrir("CadastroProduto", "Cadastro de Produto");
    }

    @SuppressWarnings("UseSpecificCatch")
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
        }
    }
}
