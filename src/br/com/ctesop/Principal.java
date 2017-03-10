package br.com.ctesop;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Principal extends Application{

   public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String url = "/br/com/ctesop/view/CadastroEstado.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sistema");
        primaryStage.show();
    }
}
