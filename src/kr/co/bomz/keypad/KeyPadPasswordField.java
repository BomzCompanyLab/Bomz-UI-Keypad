package kr.co.bomz.keypad;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import kr.co.bomz.cmn.module.ModuleProperties;

/**
 * 		��ȣ�ʵ� Ű�е� �Է�
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class KeyPadPasswordField extends PasswordField{

	private KeyPadField keyPadField = null;
	
	/**
	 * 	��ü ���ڸ� ����ϴ� �⺻ Ű�е�
	 */
	public KeyPadPasswordField() {
		this(KeyPadField.RESOURCE_VALUE_ALL);
	}

	/**
	 * 	Ű�е� Ÿ���� ������ ������
	 * @param keyPadType	Ű�е� Ÿ��. KeyPadController.RESOURCE_VALUE_ALL �Ǵ� KeyPadController.RESOURCE_VALUE_ONLY_NUMBER ���
	 */
	public KeyPadPasswordField(char keyPadType) throws UnknowKeyPadTypeException{
		if( ModuleProperties.isKeyboardLock() ){
			if( keyPadType == KeyPadField.RESOURCE_VALUE_PRICE )
				throw new UnknowKeyPadTypeException(keyPadType);
			
			this.keyPadField = new KeyPadField(this, keyPadType);
			
			super.setEditable(false);
		}
	}
	public void setStage(Stage stage){
		// Ű���� ��� ���°� �ƴ� ��� Ű�е�� ȣ����� �ʴ´�
		if( this.keyPadField != null )		this.keyPadField.setStage(stage);
	}
	
	/**		�Է� ��ư Ŭ�� �� ������ �̺�Ʈ �ڵ鷯		*/
	public void setSubmitEventHandler(EventHandler<ActionEvent> submitEventHandler){
		this.keyPadField.setSubmitEventHandler(submitEventHandler);
	}
	
	/**		��� ��ư Ŭ�� �� ������ �̺�Ʈ �ڵ鷯		*/
	public void setCancelEventHandler(EventHandler<ActionEvent> cancelEventHandler){
		this.keyPadField.setCancelEventHandler(cancelEventHandler);
	}
}
