package application;
	
import application.controller.SchedNotification;
import application.dal.FileDal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			HBox mainBox = (HBox)FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
			Scene scene = new Scene(mainBox,685,440);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// Keep a reference of the mainBox inside the commonObjs object
			CommonObjs commonObjs = CommonObjs.getInstance();
			commonObjs.setMainBox(mainBox);
			
			// Initialize FileDal
            FileDal fileDal = new FileDal();

            // Check for notifications
            SchedNotification.checkAndAlert(primaryStage, fileDal);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}