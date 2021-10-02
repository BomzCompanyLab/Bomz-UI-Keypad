package kr.co.bomz.keypad;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kr.co.bomz.cmn.BomzUtils;
import kr.co.bomz.cmn.module.ModuleProperties;

/**
 * 		�ؽ�Ʈ�ʵ� Ű�е� �Է�
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class KeyPadTextField extends TextField{

	private KeyPadField keyPadField = null;
	
	/**
	 * 	��ü ���ڸ� ����ϴ� �⺻ Ű�е�
	 */
	public KeyPadTextField() throws UnknowKeyPadTypeException{
		this(KeyPadField.RESOURCE_VALUE_ALL);
	}
	
	/**
	 * 	Ű�е� Ÿ���� ������ ������
	 * @param keyPadType	Ű�е� Ÿ��. KeyPadController.RESOURCE_VALUE_ALL �Ǵ� KeyPadController.RESOURCE_VALUE_ONLY_NUMBER ���
	 */
	public KeyPadTextField(char keyPadType) throws UnknowKeyPadTypeException{
		if( ModuleProperties.isKeyboardLock() ){
			this.keyPadField = new KeyPadField(this, keyPadType);
			super.setEditable(false);
		}
	}
	
	/**		Ű�е� ǥ�ø� ���� �������� ����		*/
	public void setStage(Stage stage){
		// Ű���� ��� ���°� �ƴ� ��� Ű�е�� ȣ����� �ʴ´�
		if( this.keyPadField != null )		this.keyPadField.setStage(stage);
	}
	
	/**		Ű�е� Ÿ�� ����		*/
	public final void setKeyPadType(char keyPadType) throws UnknowKeyPadTypeException{
		if( this.keyPadField != null )	
			this.keyPadField.setKeyPadType(keyPadType);
	}
	
	/**		�Է� �� ����		*/
	public void setValue(String value){
		if( value == null )		return;
		
		if( this.keyPadField != null && this.keyPadField.isPriceField() ){
			super.setText( BomzUtils.toPriceValue(value) );
		}else{
			super.setText(value);
		}
	}
	
	/**		�Է� �� ����		*/
	public String getValue(){
		if( this.keyPadField != null && this.keyPadField.isPriceField() ){
			return super.getText().replace(",", "");
		}else{
			return super.getText();
		}
	}
	
	/**		�Է°��� ������ �˻�		*/
	public boolean isEmptyValue(){
		return this.getValue().trim().isEmpty();
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
