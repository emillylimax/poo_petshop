package visao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
	    Parent loginRoot = FXMLLoader.load(getClass().getResource("telaLogin.fxml"));
	    Scene loginScene = new Scene(loginRoot);
	    primaryStage.setScene(loginScene);
	    primaryStage.setTitle("Bem vindo(a) ao RoyalPet!");

	    primaryStage.show();
	}

    public void mostrarTelaCadastro() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("telaCadastroClientes.fxml"));
        Parent cadastroRoot = loader.load();
        Scene cadastroScene = new Scene(cadastroRoot);
        Stage cadastroStage = new Stage();
        cadastroStage.setScene(cadastroScene);
        cadastroStage.setTitle("Cadastro de Clientes");
        cadastroStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}