package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kr.co.bomz.cmn.module.ModuleProperties;
import kr.co.bomz.keypad.KeyPadField;
import kr.co.bomz.keypad.KeyPadTextField;

/**
 * 	KeyPad Sample
 * 
 * @author Bomz
 *
 */
public class Sample extends Application {
	
	@Override
	public void start(Stage stage) throws Exception{
		stage.setTitle("JavaFX Keypad");
		
		// 1. All character software keypad
		KeyPadTextField keyPad1 = new KeyPadTextField();
		keyPad1.setStage(stage);
		keyPad1.setKeyPadType(KeyPadField.RESOURCE_VALUE_ALL);
		
		ModuleProperties.setKeyboardLock(false);	// setting input hardware keyboad
		
		// 2. All character hardware keypad
		KeyPadTextField keyPad2 = new KeyPadTextField();
		keyPad2.setStage(stage);
		
		ModuleProperties.setKeyboardLock(true);	// setting input software keypad
		
		// 3. Only number software keypad
		KeyPadTextField keyPad3 = new KeyPadTextField(KeyPadField.RESOURCE_VALUE_ONLY_NUMBER);
		keyPad3.setStage(stage);
		
		/*
		 * 4. Only number and price type software keypad
		 * 
		 * 	if
		 * 		keyPad4 value = 38,382,000
		 * 		
		 * 	source
		 * 		System.out.println( keyPad4.getText() );
		 * 		console> 38,382,000
		 * 
		 * 		System.out.println( keyPad4.getValue() );
		 * 		console> 38382000
		 */
		KeyPadTextField keyPad4 = new KeyPadTextField(KeyPadField.RESOURCE_VALUE_PRICE);
		keyPad4.setStage(stage);
		
		

		
		VBox box = new VBox();
		box.getChildren().add( this.getPane("type1", keyPad1) );
		box.getChildren().add( this.getPane("type2", keyPad2) );
		box.getChildren().add( this.getPane("type3", keyPad3) );
		box.getChildren().add( this.getPane("type4", keyPad4) );
		
		Scene scene = new Scene(box);
		stage.setScene(scene);
		stage.show();
	}
	
	private FlowPane getPane(String name, KeyPadTextField keyPad){
		FlowPane pn = new FlowPane();
		pn.setPadding(new Insets(5));
		pn.setHgap(15);
		pn.setVgap(20);
		pn.getChildren().addAll(new Label(name), keyPad);
		
		return pn;
	}
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
