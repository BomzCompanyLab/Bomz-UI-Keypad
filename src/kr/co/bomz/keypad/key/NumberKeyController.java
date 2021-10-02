package kr.co.bomz.keypad.key;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import kr.co.bomz.keypad.CharacterValue;

/**
 * 	���� ��ư Ű
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class NumberKeyController extends Key {

	/**		���� �� ��Ÿ ���� ���� FXML URL		*/
	public static final URL fxml_number = NumberKeyController.class.getResource("NumberKey.fxml");
	
	/**		���ڸ� �Է� ������ FXML URL		*/
	public static final URL fxml_only = NumberKeyController.class.getResource("NumberOnlyKey.fxml");
	
	@FXML
	public void handleButtonAction(ActionEvent event){
		Button bt = ((Button)event.getSource());
		
		Object userData = bt.getUserData();
		String inputValue = userData == null ? bt.getText() : userData.toString();
		
		CharacterValue value = super.controller.getAnchorCharacter();
		
		if( value != null )		value.end();
		
		// ���� ��ư�� Ư���ϰ� ���� ó���Ѵ�
		if( inputValue.equals("del") ){
			super.controller.delAnchorCharacter();
			
		}else{
			// ���� ��ư�� �ƴ� �ٸ� ��ư�� ���
			if( value != null && !super.controller.isFirstAnchor() )		super.controller.plusCharacterAnchor();
			
			int length = inputValue.length();
			for(int i=0; i < length; i++){
				if( i != 0 )		super.controller.plusCharacterAnchor();
				super.controller.addAnchorCharacter( new CharacterValue(inputValue.charAt(i)));
			}
			
		}
	}
	
}
