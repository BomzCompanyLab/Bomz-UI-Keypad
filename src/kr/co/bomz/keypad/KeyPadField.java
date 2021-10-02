package kr.co.bomz.keypad;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.co.bomz.cmn.Commons;
import kr.co.bomz.cmn.module.ControllerResource;
import kr.co.bomz.cmn.module.ControllerStage;

/**
 * 	Ű�е�� �ԷµǴ� �ʵ� �̺�Ʈ ó��<br>
 * 
 * 	Ű�е� Ÿ���� ��ü���� �Ǵ� ���ڸ� �ִ� Ű�е�� �Ǿ������� 
 * 	�� Ÿ���� �Ʒ� ���ҽ������� �������ָ�ȴ�<br>
 * 
 * 	KeyPadController.RESOURCE_VALUE_ALL : ��� ���� ��� ����<br>
 * 	KeyPadController.RESOURCE_VALUE_ONLY_NUMBER : ���ڸ� ��� ����
 * 
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class KeyPadField implements ControllerStage{
	

	/**		���ҽ� �� : ��� ���� �Է� ����		*/
	public static final char RESOURCE_VALUE_ALL = 'A';
	/**		���ҽ� �� : ���ڸ� �Է� ����			*/
	public static final char RESOURCE_VALUE_ONLY_NUMBER = 'N';
	/**		���ҽ� �� : ���� �Է� �� ���. ���ڸ� �Է� ����			*/
	public static final char RESOURCE_VALUE_PRICE = 'P';
	
	private final TextField valueTf;
	
	/**
	 * 		Ű�е� Ÿ��
	 * @see		RESOURCE_VALUE_ALL		��� ���� ��� ����
	 * @see		RESOURCE_VALUE_ONLY_NUMBER		���ڸ� ��� ����
	 * @see		RESOURCE_VALUE_PRICE	���� ǥ�ÿ� ���. ���ڸ� ��� ����
	 */
	private char keyPadType;
	
	private Stage stage;
	
	private Popup popup = null;
	
	private Stage popupStage = null;
	
	/**		�Է� ��ư Ŭ�� �� ����Ǵ� �̺�Ʈ		*/
	private EventHandler<ActionEvent> submitEventHandler;
	
	/**		��� ��ư Ŭ�� �� ����Ǵ� �̺�Ʈ		*/
	private EventHandler<ActionEvent> cancelEventHandler;
	
	/**
	 * 		�⺻ ������
	 * @param field		�Է� �ʵ�
	 */
	public KeyPadField(TextField field) throws UnknowKeyPadTypeException{
		this(field, RESOURCE_VALUE_ALL, null);
	}

	/**
	 * 		�⺻ ������
	 * @param field		�Է� �ʵ�
	 * @param keyPadType	Ű�е� Ÿ��
	 */
	public KeyPadField(TextField field, char keyPadType) throws UnknowKeyPadTypeException{
		this(field, keyPadType, null);
	}
	
	/**
	 * 		�ʱⰪ�� �����ϴ� ������
	 * @param field					�Է� �ʵ�
	 * @param keyPadType		Ű�е� Ÿ��. ��ü ���� �Ǵ� ����
	 * @param initValue			�ʱⰪ�� ���� ��� null
	 */
	public KeyPadField(TextField field, char keyPadType, String initValue) throws UnknowKeyPadTypeException{
		this.valueTf = field;
		
		this.setKeyPadType(keyPadType);
		
		if( initValue != null )		this.valueTf.setText(initValue);
		
		this.initEvent();
	}
	
	/**		�� ����		*/
	public void setValue(String value){
		this.valueTf.setText(value);
	}
	
	@Override
	public void setStage(Stage stage){
		this.stage = stage;
	}
	
	/**		�Է�â Ŭ�� �̺�Ʈ ó��		*/
	private void initEvent(){
		this.valueTf.setOnTouchPressed(TouchEvent ->{
			this.show();
		});
		
		this.valueTf.setOnMouseClicked(MouseEvent ->{
			this.show();
		});
		
	}
	
	/**		Ű�е� �Է� Ÿ�� ����		*/
	public final void setKeyPadType(char keyPadType) throws UnknowKeyPadTypeException{
		if( keyPadType != RESOURCE_VALUE_ALL && keyPadType != RESOURCE_VALUE_ONLY_NUMBER &&
				keyPadType != RESOURCE_VALUE_PRICE )
			throw new UnknowKeyPadTypeException(keyPadType);
		
		this.keyPadType = keyPadType;
	}
	
	@Override
	public synchronized void show(){

		if( this.stage == null ){
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			if( logger.isWarnEnabled() )		logger.warn("Stage �� �������� ���� ���¿��� Ű�е� ȭ�� ǥ�� ��û");
			return;
		}
		
		if( this.popup != null || this.popupStage != null ){
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			if( logger.isWarnEnabled() )		logger.warn("Ű�е� ȭ�� ǥ�� �� ��ǥ�� ��û");
			return;
		}
		
		Pane parent = null;
		
		ControllerResource resource = new ControllerResource(this);
		resource.addResource(KeyPadController.RESOURCE_KEY, this.keyPadType);
		resource.addResource(KeyPadController.RESOURCE_INIT_VALUE, this.valueTf.getText());
		
		try{
			parent = FXMLLoader.load(
				KeyPadController.class.getResource(this.keyPadType == RESOURCE_VALUE_ALL ? "KeyPad.fxml" : "NumberOnlyKeyPad.fxml"), 
				resource
			);
		}catch(IOException e){
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			logger.error("Ű�е� FXML �ε� �� ������ Ű�е� ȭ�� ���� ����", e);
			return;
		}
		

		this.popupStage = new Stage(StageStyle.TRANSPARENT);
		this.popupStage.setAlwaysOnTop(true);
		this.popupStage.setResizable(false);
		this.popupStage.initModality(Modality.APPLICATION_MODAL);
		this.popupStage.initOwner(this.stage);
		this.popupStage.show();
		
		this.popup = new Popup();
		this.popup.getContent().add(parent);
		this.popup.show(this.popupStage);
		
		
	}
	
	/**		�Է� ��ư Ŭ�� �� ������ �̺�Ʈ �ڵ鷯		*/
	void setSubmitEventHandler(EventHandler<ActionEvent> submitEventHandler){
		this.submitEventHandler = submitEventHandler;
	}
	
	/**		��� ��ư Ŭ�� �� ������ �̺�Ʈ �ڵ鷯		*/
	void setCancelEventHandler(EventHandler<ActionEvent> cancelEventHandler){
		this.cancelEventHandler = cancelEventHandler;
	}
	
	/**
	 * 		Ű�е� �Է�â ���� �� �Է� ���� ����
	 * 
	 * @param value		Ű�е�� �Է��� ����. ��Ҹ� ������ ��� null
	 */
	@Override
	public synchronized void close(Object value){
		
		if( this.popup == null || this.popupStage == null ){
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			if( logger.isWarnEnabled() )		logger.warn("Ű�е� ȭ���� ǥ�õ��� ���� ���¿��� ȭ�� �ݱ� ��û");
			return;
		}
		
		if( value != null ){
			// �Է� ��ư Ŭ�� ��
			this.valueTf.setText((String)value);
			
			if( this.submitEventHandler != null )		this.submitEventHandler.handle(new ActionEvent());
		}else{
			// ��� ��ư Ŭ�� ��
			if( this.cancelEventHandler != null )		this.cancelEventHandler.handle(new ActionEvent());
		}
		
		
		this.popup.hide();
		this.popup = null;
		
		this.popupStage.close();
		this.popupStage = null;
	}
	
	/**		���� ǥ�� ���� �ؽ�Ʈ�ʵ��� ��� true		*/
	public boolean isPriceField(){
		return this.keyPadType == RESOURCE_VALUE_PRICE;
	}
}
