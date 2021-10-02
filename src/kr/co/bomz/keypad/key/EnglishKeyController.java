package kr.co.bomz.keypad.key;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import kr.co.bomz.cmn.Commons;
import kr.co.bomz.keypad.CharacterValue;
import kr.co.bomz.keypad.KeyPadController;

/**
 * 	���� ��ư Ű
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class EnglishKeyController extends Key {

	/**		�ҹ��� FXML URL		*/
	public static final URL fxml_lower = EnglishKeyController.class.getResource("EnglishLowerKey.fxml");
	
	/**		�빮�� FXML URL		*/
	public static final URL fxml_upper = EnglishKeyController.class.getResource("EnglishUpperKey.fxml");
	
	/**		
	 * 		���� Ű�е� �빮�� ��ݻ��� ���� Boolean Ÿ��
	 * 		�빮�� ��ݻ����� ��� true			
	 **/
	public static final String RESOURCE_KEYPAD_UPPER_LOCK = "KL";
	
	@FXML
	private Button upperLockShiftBt;
	
	/**		Ű�е� ���ڰ� �ҹ������� ����		*/
	private boolean isKeypadLower = true;
	
	/**		
	 * 		Ű�е� ���ڸ� �빮�ڷ� ��� ����
	 * 		����Ʈ Ű ���� Ŭ�� �� ���
	 **/
	private boolean isKeypadUpperLock = false;
	
	public EnglishKeyController(){
		
	}
	
	/**		����Ʈ Ű Ŭ�� �̺�Ʈ ó��		*/
	@FXML
	public void handleShiftAction(MouseEvent event){
		
		switch( event.getClickCount() ){
		case 1 :		// �ѹ� Ŭ��. Ű�е� ��ȯ
			this.changeKeyPad();		break;
		case 2 :		// �ι� Ŭ��. �빮�� ����
			this.lockUpperKeyPad();	break;
		}
		
	}
	
	/**	����Ʈ Ű �ѹ� Ŭ�� �� ��/�ҹ��� Ű�е� ��ȯ		*/
	private void changeKeyPad(){
		if( this.isKeypadLower )		super.controller.initEnglishUpperKeyPad(false);
		else										super.controller.initEnglishLowerKeyPad();
	}
	
	/**	����Ʈ Ű �ι� Ŭ�� �� �빮�� Ű�е� ����		*/
	private void lockUpperKeyPad(){
		super.controller.initEnglishUpperKeyPad(true);
	}
		
	@FXML
	public void handleButtonAction(ActionEvent event){
		Button bt = ((Button)event.getSource());
		
		Object userData = bt.getUserData();
		String inputValue = userData == null ? bt.getText() : userData.toString();
		
		CharacterValue value = super.controller.getAnchorCharacter();
		
		if( value != null )		value.end();
		
		// shift ��ư �̺�Ʈ�� handleShiftAction ���� ó���Ѵ�
		if( inputValue.equals("shift") )		return;
		
		
		// ���� ��ư�� Ư���ϰ� ���� ó���Ѵ�
		if( inputValue.equals("del") ){
			super.controller.delAnchorCharacter();
					
		}else{
			// ����, Shift ��ư�� �ƴ� �ٸ� ��ư�� ���
			if( value != null && !super.controller.isFirstAnchor())		super.controller.plusCharacterAnchor();
			
			super.controller.addAnchorCharacter( new CharacterValue(inputValue.charAt(0)) );
			
		}
		
		if( !this.isKeypadLower && !this.isKeypadUpperLock )
			super.controller.initEnglishLowerKeyPad();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resource) {
		if( resource == null )		return;
		
		super.initialize(url, resource);
		
		// ��,�ҹ��� ����
		Object obj = resource.getObject(KeyPadController.RESOURCE_KEYPAD_LOWER_TYPE);
		if( obj == null ){		// �������� ���ٸ� �ҹ��ڷ� ����
			this.isKeypadLower = true;
		}else{
			try{
				this.isKeypadLower = (Boolean)obj;
			}catch(Exception e){
				// �ƿ� �߸��� ���� ���� ���
				Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
				logger.error("���ǵ��� ���� ���� ��/�ҹ��� ���� ���ҽ� �� [��={}]", obj);
				this.isKeypadLower = true;
			}
		}
			
		// �빮�� ���� ����
		obj = resource.getObject(RESOURCE_KEYPAD_UPPER_LOCK);
		if( obj == null ){		// �������� ���ٸ� �빮�� ���� ��� ����
			this.isKeypadUpperLock = false;
		}else{
			try{
				this.isKeypadUpperLock = (Boolean)obj;
			}catch(Exception e){
				// �ƿ� �߸��� ���� ���� ���
				Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
				logger.error("���ǵ��� ���� �빮�� ���� ���ҽ� �� [��={}]", obj);
				this.isKeypadUpperLock = false;
			}
		}
		
		if( !this.isKeypadLower && this.isKeypadUpperLock )
			this.upperLockShiftBt.getStyleClass().add("upperLockShift");
	}
	
}
