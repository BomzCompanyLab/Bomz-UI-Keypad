package kr.co.bomz.keypad;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import kr.co.bomz.cmn.BomzUtils;
import kr.co.bomz.cmn.Commons;
import kr.co.bomz.cmn.module.ControllerResource;
import kr.co.bomz.cmn.module.ControllerStage;
import kr.co.bomz.keypad.key.EnglishKeyController;
import kr.co.bomz.keypad.key.Key;
import kr.co.bomz.keypad.key.KoreaKeyController;
import kr.co.bomz.keypad.key.NumberKeyController;
import kr.co.bomz.keypad.key.SymbolKeyController;

public class KeyPadController implements Initializable{
	
	/**		ȭ�� ������ ���� ���ҽ� Ű ��		*/
	public static final String RESOURCE_KEY = "KP_TY";
	
	/**		ȭ�� �ʱⰪ ���� ���ҽ� Ű ��		*/
	public static final String RESOURCE_INIT_VALUE = "KP_INIT";
	

	/**		
	 * 		Ű�е� ����Ʈ(Shift) ��ư ó�� Boolean Ÿ��
	 * 		�ѱ� : �⺻ ���� (true)
	 * 		���� : �ҹ��� (true)		
	 **/
	public static final String RESOURCE_KEYPAD_LOWER_TYPE = "KT";
	
	/**		
	 * 		Ű�е� Ÿ��
	 * 
	 * @see RESOURCE_VALUE_ALL		��� ���� �Է� ����
	 * @see RESOURCE_VALUE_ONLY_NUMBER		���ڸ� �Է� ����
	 */
	private char keyPadType;
		
	/**		�⺻ ���̾ƿ�		*/
	@FXML
	private BorderPane parent;
	
	/**		����� �Է°� �ʵ�		*/
	@FXML
	private TextField valueTf;
	
	/**		�ѱ� �Է� ��ư		*/
	@FXML
	private Button koreanLanguageBt;
	
	/**		���� �Է� ��ư		*/
	@FXML
	private Button englishLanguageBt;
	
	/**		Ư����ȣ �Է� ��ư		*/
	@FXML
	private Button symbolBt;
	
	/**		���� �Է� ��ư			*/
	@FXML
	private Button numberBt;
	
	/**		��ư ���		*/
	private Button[] buttons;
	
	/**		Ű�е� �Է� ���ڿ� ������ ���� ĳ���� ���			*/
	private List<CharacterValue> characterList = new ArrayList<CharacterValue>();
	
	/**		�Է�â Ŀ�� ��ġ		*/
	private int characterAnchor = 0;
	
	/**		Ű�е带 ��û�� ��������		*/
	private ControllerStage controllerStage;
	
	public KeyPadController(){}

	@Override
	public void initialize(URL url, ResourceBundle resource) {
		
		this.buttons = new Button[]{
				this.koreanLanguageBt, this.englishLanguageBt, this.symbolBt, this.numberBt
			};
				
		this.controllerStage = (ControllerStage)resource.getObject(ControllerStage.RESOURCE_ID);
		
		// UI Ÿ�� ����
		this.initializeUIType( (Character)resource.getObject(RESOURCE_KEY) );
		
		// �ʱ� �Է� �� ����
		this.initializeValue( resource.getObject(RESOURCE_INIT_VALUE) );
		
		// Ű���� ��� ���ο� ���� �̺�Ʈ ó��
		this.initKeyboardEvent();
		
		// Ű���� ��ܿ� ���� URL ǥ��
		this.initBomzText();
	}
	
	/**	Ű���� ��ܿ� ���� URL ǥ��		*/
	private void initBomzText(){
		Label lb = new Label("���� [http://www.bomz.co.kr]");
		lb.setTextFill(Paint.valueOf("838383"));
		lb.setFont(Font.font(15));
		
		FlowPane pn = new FlowPane();
		pn.setAlignment(Pos.CENTER);
		FlowPane.setMargin(lb, new Insets(10, 0, 5, 0));
		pn.getChildren().add(lb);
		
		this.parent.setBottom(pn);
	}
	
	/**	Ű���� ��� ����		*/
	private void initKeyboardEvent(){
	
		this.valueTf.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event){
				event.consume();
			}
		});
		
		this.valueTf.setOnKeyTyped(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event){
				event.consume();
			}
		});
	}
	
	/**		�ʱ� �Է°��� ���� ��� ����		*/
	private void initializeUIType(Character resourceValue){
		if( resourceValue == null ){		
			// ������ ���� ��� ��� ���� �Է� ����
			this.initializeAllCharacter();
			
		}else{
			// ������ ���� ��� �˻�. �������� �������� ��� ĳ���� Ÿ��
			switch(resourceValue){
			case KeyPadField.RESOURCE_VALUE_ALL :		// ��� ���� �Է� ����
				this.initializeAllCharacter();		break;
			case KeyPadField.RESOURCE_VALUE_ONLY_NUMBER :		// ���ڸ� �Է� ����
				this.initializeOnlyNumber();		break;
			case KeyPadField.RESOURCE_VALUE_PRICE :						// ���ڸ� �Է� ���� (���� ǥ�ÿ�)
				this.initializeOnlyNumberAsPrice();		break;
			default :
				// �˼� ���� ������ ��� ��� ���� �Է� ����
				this.initializeAllCharacter();		break;
			}
		}
	}
	
	/**		�ʱ� �Է°��� ���� ��� ����		*/
	private void initializeValue(Object initValueObj){
		if( initValueObj == null )		return;
		
		if( !(initValueObj instanceof String) )		return;
		
		String value = (String)initValueObj;
		if( this.keyPadType == KeyPadField.RESOURCE_VALUE_PRICE ){
			// ���� ǥ�� ������ ��� ���ڿ� ������ ��ǥ ����
			value = value.replaceAll(",", "");
		}
		
		int length = value.length();
		
		for(int i=0; i < length; i++){
			if( i != 0 )		this.plusCharacterAnchor();
			this.addAnchorCharacter(new CharacterValue(value.charAt(i)));
		}

	}
	
	/**	��� ���ڰ� �Է� �����ϵ��� ����		*/
	private void initializeAllCharacter(){
		this.keyPadType = KeyPadField.RESOURCE_VALUE_ALL;
		
		// �ѱ� �Է� â ǥ��
		this.handleKoreaLanguageButtonAction(new ActionEvent(this.koreanLanguageBt, this.koreanLanguageBt));
	}
	
	/**	���ڸ� �Է� �����ϵ��� ����		*/
	private void initializeOnlyNumber(){
		this.keyPadType = KeyPadField.RESOURCE_VALUE_ONLY_NUMBER;
		
		// ���� �Է� â ǥ��
		this.initKeyPad(NumberKeyController.fxml_only);
	}
	
	/**	���ڸ� �Է� �����ϵ��� ����(���� ǥ�� ����)		*/
	private void initializeOnlyNumberAsPrice(){
		this.keyPadType = KeyPadField.RESOURCE_VALUE_PRICE;
		
		// ���� �Է� â ǥ��
		this.initKeyPad(NumberKeyController.fxml_only);
	}
	
	/**		�ѱ�/����/���� �� Ű�е� ����		*/
	private void updateKeyPad(Button actionBt){
		if( this.keyPadType == KeyPadField.RESOURCE_VALUE_ONLY_NUMBER ){
			// ���ڸ� �Է� ������ ���
			for(Button button : this.buttons){
								
				if( button != numberBt ){		// �����е尡 �ƴ� ���
					button.setDisable(false);
					if( !button.getStyleClass().contains("disableKeyPadTypeButton") )
						button.getStyleClass().add("disableKeyPadTypeButton");
					
				}else{		// �����е��� ���
					if( !button.getStyleClass().contains("showKeyPadTypeButton") )
						button.getStyleClass().add("showKeyPadTypeButton");
				}
			}
			
		}else{
			// ��� ���� �Է� ������ ���
			
			for(Button button : this.buttons){
				
				if( button == actionBt ){		// �̺�Ʈ�� �߻��� ��ư�� ���
					if( button.getStyleClass().contains("disableKeyPadTypeButton") )
						button.getStyleClass().remove("disableKeyPadTypeButton");
					
					if( !button.getStyleClass().contains("showKeyPadTypeButton") )
						button.getStyleClass().add("showKeyPadTypeButton");
					
				}else{		// �̺�Ʈ�� �߻��� ��ư�� �ƴ� ���
					if( !button.getStyleClass().contains("disableKeyPadTypeButton") )
						button.getStyleClass().add("disableKeyPadTypeButton");
					
					if( button.getStyleClass().contains("showKeyPadTypeButton") )
						button.getStyleClass().remove("showKeyPadTypeButton");
				}
			}
		}
		
	}
	
	/**		Ű�е� ���� ���� �� ȣ��		*/
	private void initKeyPad(URL url){
		this.initKeyPad(url, new ControllerResource(this.controllerStage, Key.RESOURCE_KEY, this));
	}
	
	/**		Ű�е� ���� ���� �� ȣ��		*/
	private void initKeyPad(URL url, ControllerResource resource){
		try {
			Parent keyParent = FXMLLoader.load(url, resource);
			this.parent.setCenter(keyParent);
		} catch (IOException e) {
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			logger.error("Ű�е� ȣ�� ���� URL[{}]", url, e);
		}
	}
	
	/**		���� �ҹ��� Ű�е� ȭ�� ǥ��		*/
	public final void initEnglishLowerKeyPad(){
		ControllerResource resource = new ControllerResource(this.controllerStage, Key.RESOURCE_KEY, this);
		resource.addResource(RESOURCE_KEYPAD_LOWER_TYPE, true);
		resource.addResource(EnglishKeyController.RESOURCE_KEYPAD_UPPER_LOCK, false);
		
		this.initKeyPad(EnglishKeyController.fxml_lower, resource);
	}
	
	/**		���� �빮�� Ű�е� ȭ�� ǥ��		*/
	public final void initEnglishUpperKeyPad(boolean isUpperLock){
		ControllerResource resource = new ControllerResource(this.controllerStage, Key.RESOURCE_KEY, this);
		resource.addResource(RESOURCE_KEYPAD_LOWER_TYPE, false);
		resource.addResource(EnglishKeyController.RESOURCE_KEYPAD_UPPER_LOCK, isUpperLock);
		
		this.initKeyPad(EnglishKeyController.fxml_upper, resource);
	}
	
	/**		�ѱ� Shift ��ư�� ���� Ű�е� ȭ�� ǥ��		*/
	public final void initKoreaKeyPad(boolean lower){
		ControllerResource resource = new ControllerResource(this.controllerStage, Key.RESOURCE_KEY, this);
		resource.addResource(RESOURCE_KEYPAD_LOWER_TYPE, lower);
		
		this.initKeyPad(
				lower ? KoreaKeyController.fxml_lower : KoreaKeyController.fxml_upper, 
				resource
			);
	}
	
	/**		�ѱ� ��ư �̺�Ʈ		*/
	@FXML
	protected void handleKoreaLanguageButtonAction(ActionEvent event){
		this.updateKeyPad((Button)event.getSource());
		this.initKoreaKeyPad(true);
	}
	
	/**		���� ��ư �̺�Ʈ		*/
	@FXML
	protected void handleEnglishLanguageButtonAction(ActionEvent event){
		this.updateKeyPad((Button)event.getSource());
		this.initEnglishLowerKeyPad();
	}
		
	/**		���� ��ư �̺�Ʈ		*/
	@FXML
	protected void handleNumberButtonAction(ActionEvent event){
		this.updateKeyPad((Button)event.getSource());
		this.initKeyPad(NumberKeyController.fxml_number);
	}
	
	/**		Ư����ȣ ��ư �̺�Ʈ		*/
	@FXML
	protected void handleSymbolButtonAction(ActionEvent event){
		this.updateKeyPad((Button)event.getSource());
		this.initKeyPad(SymbolKeyController.fxml);
	}

	/**		���� Ŀ�� ��ġ ����		*/
	private void updateAnchor(boolean isDel){
		// ���� Ŀ�� ��ġ ��������
		int nowAnchor = this.valueTf.getAnchor();
		if( !isDel )		nowAnchor--;
		
		if( nowAnchor < 0 )		nowAnchor = 0;
		
		if( nowAnchor != this.characterAnchor ){
			// ������ Ŀ�� ��ġ�� �ٲ���� ��� ������ �۾����̴� ĳ���ʹ� �Ϸ� ó��
			if( this.characterList.size() > this.characterAnchor )
				this.characterList.get(this.characterAnchor).end();
			
			this.characterAnchor = nowAnchor;
		}
	}
	
	/**		Ŀ�� ��ġ�� �ۼ����� ���ڰ� ����. ���� ��� null		*/
	public CharacterValue getAnchorCharacter(){
		
		this.updateAnchor(false);			// ���� Ŀ�� ��ġ ����
		
		// ������ �۾� ���̴� ���� ����
		if( this.characterList.size() > this.characterAnchor )
			return this.characterList.get(this.characterAnchor);
		else
			return null;
	}
	
	/**	Ŀ�� ��ġ �������� ����		*/
	public void plusCharacterAnchor(){
		this.characterAnchor++;
	}
	
	public void addAnchorCharacter(CharacterValue value){
		this.addAnchorCharacter(value, true);
	}
	
	/**		Ŀ�� ��ġ�� ���ڰ� �߰�		*/
	public void addAnchorCharacter(CharacterValue value, boolean isAddList){
		if( isAddList )		this.characterList.add(this.characterAnchor, value);
		
		if( !value.isNullValue() )		// �Է�â���� �߰�
			this.valueTf.insertText(this.characterAnchor, value.getValue().toString());
	}
	
	/**		Ŀ�� ��ġ�� ���ڰ� ����		*/
	public void replaceAnchorCharacter(String value){
		this.valueTf.replaceText(this.characterAnchor, this.characterAnchor + 1, value);
	}
	
	/**		Ŀ�� ��ġ�� ���ڰ� ����		*/
	public void delAnchorCharacter(){
		
		this.updateAnchor(true);			// ���� Ŀ�� ��ġ ����
		
		if( this.characterAnchor == 0 )		return;
		int characterListSize = this.characterList.size();
		if( characterListSize <= this.characterAnchor ){
			this.valueTf.replaceText(this.characterAnchor - 1, this.characterAnchor, "");
			
			// ������� �ʴٸ� ������ ���� ����
			if( !this.characterList.isEmpty() )		this.characterList.remove(this.characterList.size() - 1);
			
		}else{
			this.characterList.remove(this.characterAnchor - 1);
			this.valueTf.replaceText(this.characterAnchor - 1, this.characterAnchor, "");
		}
		
	}
		
	/**		Ŀ���� ù���翡 ��ġ���� ��� true		*/
	public boolean isFirstAnchor(){
		return this.valueTf.getAnchor() == 0;
	}
	
	@FXML
	protected void handleCancel(){
		this.controllerStage.close(null);
	}
	
	@FXML
	protected void handleSubmit(){
		if( this.keyPadType == KeyPadField.RESOURCE_VALUE_PRICE ){
			// ���� ǥ���� ��� ��ǥ�� �߰�
			this.controllerStage.close( BomzUtils.toPriceValue(this.valueTf.getText()) );
		}else{
			this.controllerStage.close(this.valueTf.getText().trim());
		}
		
	}
	
	@FXML
	protected void handleClear(){
		this.characterList.clear();
		this.valueTf.setText("");
		this.characterAnchor = 0;
	}
	
}
