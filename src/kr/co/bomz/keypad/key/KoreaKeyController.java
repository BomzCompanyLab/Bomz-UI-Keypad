package kr.co.bomz.keypad.key;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * 	�ѱ��� ��ư Ű
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class KoreaKeyController extends Key{

	/**		FXML LOWER URL		*/
	public static final URL fxml_lower = KoreaKeyController.class.getResource("KoreaLowerKey.fxml");
	
	/**		FXML UPPER URL		*/
	public static final URL fxml_upper = KoreaKeyController.class.getResource("KoreaUpperKey.fxml");
	
	/**		Ű�е� ���ڰ� �ҹ������� ����		*/
	private boolean isKeypadLower = true;
	
	@FXML
	private Button upperLockShiftBt;
	
	/**	�ʼ� 19��		*/
	private Map<Character, Integer> step1 = new HashMap<Character, Integer>();
	/**	�߼� 21��		*/
	private Map<Character, Integer> step2 = new HashMap<Character, Integer>();
	/**	���� 28��		*/
	private Map<Character, Integer> step3 = new HashMap<Character, Integer>();
	
//	�ʼ��� 19�� : 
//	�߼��� 21�� : '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��'
//	������ 28�� : ' ','��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��'
	
	public KoreaKeyController(){
		this.initStep1();
		this.initStep2();
		this.initStep3();
	}
	
	/**	�ʼ� �ʱ�ȭ		*/
	private void initStep1(){
		char[] data = new char[]{'��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��'};
		for(int i=0; i < data.length; i++)		this.step1.put(data[i], i);
	}
	
	/**	�߼� �ʱ�ȭ		*/
	private void initStep2(){
		char[] data = new char[]{'��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��'};
		for(int i=0; i < data.length; i++)		this.step2.put(data[i], i);
	}
	
	/**	���� �ʱ�ȭ		*/
	private void initStep3(){
		char[] data = new char[]{'��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��'};
		for(int i=0; i < data.length; i++)		this.step3.put(data[i], i+1);
	}
	
	@FXML
	public void handleButtonAction(ActionEvent event){
		
		Button bt = ((Button)event.getSource());
		Object userData = bt.getUserData();
		String inputValue = userData == null ? bt.getText() : userData.toString();
		
		CharacterValue value = super.controller.getAnchorCharacter();
		
		// ���� ��ư�� Ư���ϰ� ���� ó���Ѵ�
		if( inputValue.equals("del") ){
			if( value == null ){		// �ƹ� ���ڰ� ���µ� ���� ��ư�� ������ ���
				super.controller.delAnchorCharacter();
				return;		
			}
			
			// ���� ��ư�� �����µ� ���� ���ڰ� �Ϸ�� ������ ��� ���� ��ü�� ����
			if( value.isEnd() )		super.controller.delAnchorCharacter();
			else							this.executeDel(value);
			
		}else{
			// ���� ��ư�� �ƴ� �ٸ� ��ư�� ���
			if( value == null ){
				value = new CharacterValue();
//				value.setValue('');
				super.controller.addAnchorCharacter(value);
			}
			
			char inputChar = inputValue.charAt(0);
			
			switch( inputChar ){
			case ' ' :		this.executeSpace(value);			break;		// ���� �߰�
			case '.' :
			case ',' :
			case '(' :
			case ')' :		this.executeSymbol(value, inputChar);		break;	// Ư����ȣ �߰�
			default :		this.executeValue(value, inputChar);		break;	// �ѱ� �߰�
			}
			
		}
		
		if( !this.isKeypadLower )		// ����ƮŰ�� �������� ��� �⺻�ѱ۷� ����
			super.controller.initKoreaKeyPad( !this.isKeypadLower );
	}
	
	/**		����Ʈ Ű Ŭ�� �̺�Ʈ ó��		*/
	@FXML
	public void handleShiftAction(MouseEvent event){
		super.controller.initKoreaKeyPad( !this.isKeypadLower );
	}
		
	/**		�⺻ Ư����ȣ ó��		*/
	private void executeSymbol(CharacterValue value, char inputValue){
		
		if( value.isEnd() ){		// ������ ���ڰ� ����Ǿ��� ��� ���ο� ���ڷ� �߰�
			if( !super.controller.isFirstAnchor() )
				super.controller.plusCharacterAnchor();
			
			super.controller.addAnchorCharacter(new CharacterValue(inputValue));
		}else{
			value.end();
			
			if( value.isNullValueList() ){		// ���� �Է°��� ���� ���
				value.setValue(inputValue);
				super.controller.replaceAnchorCharacter(inputValue + "");
				
			}else{		// ���� �Է°��� ���� ���
				super.controller.plusCharacterAnchor();
				super.controller.addAnchorCharacter(new CharacterValue(inputValue));
			}
		}
		
	}
	
	/**		���� ó��		*/
	private void executeDel(CharacterValue value){
		if( value.isNullValueList() ){
			LoggerFactory.getLogger(Commons.CMN_LOGGER_ID).error("�ѱ� Ű�е� ���� ó�� �� ���� �Է� �� NULL");
		}else{
			List<Character> valueList = value.getValueList();
			valueList.remove(valueList.size() - 1);
			if( valueList.isEmpty() )		super.controller.delAnchorCharacter();	// ���� �Է�Ű�� ���� ���
			else									this.executeValue(value, null);				// ���� �Է�Ű�� ���� ���
		}
		
	}
	
	/**		���� ó��		*/
	private void executeSpace(CharacterValue value){
		value.end();
		
		if( value.isNullValue() ){
			value.setValue(' ');
			super.controller.addAnchorCharacter(value, false);
		}else{
			if( !super.controller.isFirstAnchor() )
				super.controller.plusCharacterAnchor();
			
			super.controller.addAnchorCharacter(new CharacterValue(' '));
		}
		
	}
	
	/**		�ѱ� ó��		*/
	private void executeValue(CharacterValue value, Character inputChar){
		// inputChar �� null �� ���� ���� ��ư�� ������ �����
		if( inputChar == null )		this.executeValueAsDel(value);		
		else									this.executeValueAsSubstring(value, inputChar);
		
	}
	
	/**
	 * 		���� ���ڰ� �ϼ��Ǿ� ���� ���� ���¿��� �� �ٸ� �ѱ� ��ư�� ������ ���
	 */
	private void executeValueAsSubstring(CharacterValue value, Character inputChar){
		// ���� �ۼ����̴� ���� ��� ��������
		boolean newChar = false;
		List<Character> list;		
		if( value.isEnd() ){
			value = new CharacterValue();
			newChar = true;
			if( !super.controller.isFirstAnchor() )
				super.controller.plusCharacterAnchor();
		}
		
		list = value.getValueList();
		
		switch( list.size() ){
		case 0 :		// ù��° �Է��� ���
			this._executeValueAsFirst(value, inputChar, newChar);			break;
			
		case 1 :		// ���� �ʼ� �Ѱ��� �ԷµǾ� ���� ��� �̹� ���ڰ� �߼����� �˻�
			this._executeValueAsSubstring(value, inputChar, false);			break;
			
		case 2 : 	// ���� ���ڰ� �߼����� �ԷµǾ� ���� ��� �̹� ���ڰ� �������� �ʼ����� �߼����� �˻�
			this._executeValueAsSubstring(value, inputChar, true);			break;
			
		case 3 :		// ���� ���ڰ� �������� �ԷµǾ� ���� ��� �߰� �������� ���� �ʼ� �Ǵ� �߼����� �˻�
			this._executeValueAsSubstring1(value, inputChar, false);		break;
			
		case 4 :		// ������ 2���� ���ڰ� ���ļ� �Ǿ����� ���
			this._executeValueAsSubstring1(value, inputChar, true);		break;
			
		case 5 :		// �� 5���� Ű�� ������ ������ ���. '��', '�y' ���� ����
			this._executeValueAsString2(value, inputChar);						break;
		}
	}
	
	/**		�� 5���� Ű�� ������ '��', '�y' ���� �ѱ��� ��� 		*/
	private void _executeValueAsString2(CharacterValue value, Character inputChar){
		List<Character> list = value.getValueList();
		
		value.end();			// ���� ���� ����
		
		if( this.step2.containsKey(inputChar) ){
			// �߼��� ��� ���� ������ Ű���� ������ ó��
			Character beforeChar = list.remove(4);
			
			// ���� ���� ����
			Character checkStep2 = this.parsingStep2(list.get(1), list.get(2));
			if( checkStep2 == null )		// '��', '��' ���� �߼��� 1�� �ִ� ����
				super.controller.replaceAnchorCharacter(
						this.parsing(this.step1.get(list.get(0)), this.step2.get(list.get(1)), this.step3.get(this.parsingStep3(list.get(2), list.get(3), false)) ));
			else										// '��', '��' ���� �߼��� 2�� �ִ� ����
				super.controller.replaceAnchorCharacter(
						this.parsing(this.step1.get(list.get(0)), this.step2.get(checkStep2), this.step3.get(list.get(3)) ));
			
			// ���ο� ���� �߰�
			CharacterValue newValue = new CharacterValue();
			list = newValue.getValueList();
			list.add(beforeChar);
			list.add(inputChar);
			newValue.setValue( this.parsing(this.step1.get(beforeChar), this.step2.get(inputChar), 0).charAt(0) ) ;
			super.controller.plusCharacterAnchor();
			super.controller.addAnchorCharacter(newValue);
			
		}else{
			// �߼��� �ƴ� ��� ���� ���� ���� �� ���ο� ���ڷ� ó��
			CharacterValue newValue = new CharacterValue();
			list = newValue.getValueList();
			list.add(inputChar);
			newValue.setValue(inputChar);
			super.controller.plusCharacterAnchor();
			super.controller.addAnchorCharacter(newValue);
		}
		
	}
	
	/**		ù��° �ѱ� �Է��� ���		*/
	private void _executeValueAsFirst(CharacterValue value, Character inputChar, boolean newChar){
		if( this.step1.containsKey(inputChar) ){
			// �ʼ��� ���
			value.getValueList().add(inputChar);
		
		}else{
			// �ʼ��� �ƴ� ��� ����  ����
			value.end();
		}
		
		value.setValue(inputChar);
		super.controller.addAnchorCharacter(value, newChar);
	}
	
	private boolean _executeValueAsSubstring1Sub(CharacterValue value, Character inputChar, List<Character> list){
		// 2�ڸ��� �߼����� �Ǿ��ִ��� �˻��Ѵ� '��', '��' ��
		Character checkStep2 = this.parsingStep2(list.get(1), list.get(2));
		if( checkStep2 != null ){
			
			if( this.step3.containsKey(inputChar) ){
				// �������� ������ ��� ó��
				list.add(inputChar);
				value.setValue(
						this.parsing( 
								this.step1.get(list.get(0)),
								this.step2.get(checkStep2),
								this.step3.get(inputChar)
							).charAt(0)
					);
				super.controller.replaceAnchorCharacter(value.getValue().toString());
				
			}else{
				// �������� ������ �ƴ� ��� 
				
				this.controller.plusCharacterAnchor();
				
				if( this.step1.containsKey(inputChar) ){
					// �Է� Ű�� �ʼ��� ��� ���ο� ���� ���ڷ� ���
					value.end();
					CharacterValue newValue = new CharacterValue();
					newValue.getValueList().add(inputChar);
					newValue.setValue(inputChar);
					this.controller.addAnchorCharacter(newValue);
				}else{
					// �Է� Ű�� �߼��� ��� �ϳ��� ���ڷ� ���
					this.controller.addAnchorCharacter(new CharacterValue(inputChar));
				}
				
			}
				
			return true;
		}
		
		return false;
	}
	
	/**		�ѱ� Ű 3~4���� ���� ó��		*/
	private void _executeValueAsSubstring1(CharacterValue value, Character inputChar, boolean doubleStep3){
		
		if( this.step2.containsKey(inputChar) ){
			// ���ο� �Է� Ű�� �߼��� ��� ���� ������ ���ο� ���ڷ� ��ȯ
			List<Character> list = value.getValueList();
			
			if( !doubleStep3 && this.step2.get(list.get(list.size() - 1)) != null ){
				// '��' ���� �Է� �� �� �ٽ� '��' ���� �߼��� �Է��Ͽ��� ��� 
				// �������� ���� �� ���ο� ���ڷ� �߰�
				value.end();
				
				super.controller.plusCharacterAnchor();
				super.controller.addAnchorCharacter(new CharacterValue(inputChar));
				
			}else{
				Character beforeChar = list.remove(doubleStep3 ? 3 : 2);
				value.end();			// ���� ���� ����
				
				// ���� ���� ����
				if( doubleStep3 ){
					Character checkStep2 = this.parsingStep2(list.get(1), list.get(2));
					if( checkStep2 == null )
						super.controller.replaceAnchorCharacter(
								this.parsing(this.step1.get(list.get(0)), this.step2.get(list.get(1)), doubleStep3 ? this.step3.get(list.get(2)) : 0 ));
					else
						super.controller.replaceAnchorCharacter(
								this.parsing(this.step1.get(list.get(0)), this.step2.get(checkStep2), 0 ));
					
				}else{
					super.controller.replaceAnchorCharacter(
							this.parsing(this.step1.get(list.get(0)), this.step2.get(list.get(1)), 0 ));
				}
				
				
				// ���ο� ���� �߰�
				CharacterValue newValue = new CharacterValue();
				list = newValue.getValueList();
				list.add(beforeChar);
				list.add(inputChar);
				newValue.setValue( this.parsing(this.step1.get(beforeChar), this.step2.get(inputChar), 0).charAt(0) ) ;
				super.controller.plusCharacterAnchor();
				super.controller.addAnchorCharacter(newValue);
			}
			
		}else{
			
			List<Character> list = value.getValueList();
			
			if( !doubleStep3 && this._executeValueAsSubstring1Sub(value, inputChar, list))		return;
			
			
			// ���� '��', '��' ������ �˻�
			Character newCharacter = this.parsingStep3(list.get(doubleStep3 ? 3 : 2), inputChar, false);
			
			if( newCharacter != null ){
				// ���� '��', '��' ������ �� ���
				list.add(inputChar);
				
				if( list.size() == 5 ){
					// '��' , '�z' ���� 5���� Ű�� ������ �ѱ��� ���
					value.setValue(
							this.parsing(
								this.step1.get(list.get(0)), 
								this.step2.get(this.parsingStep2(list.get(1), list.get(2))), 
								this.step3.get(newCharacter)
							).charAt(0)
						);
					
				}else{
					// '��', '��' ���� 4���� Ű�� ������ �ѱ��� ���
					value.setValue(
							this.parsing(
								this.step1.get(list.get(0)), 
								this.step2.get(list.get(1)), 
								this.step3.get(newCharacter)
							).charAt(0)
						);
				}
				
				
				super.controller.replaceAnchorCharacter(value.getValue().toString());
			}else{
				// ���ο� �Է�Ű�� ������ �ƴ� ���
				value.end();
				
				if( this.step1.containsKey(inputChar) ){		// ���ο� �Է�Ű�� �ʼ��� ���
					CharacterValue newValue = new CharacterValue();
					newValue.getValueList().add(inputChar);
					newValue.setValue( inputChar );
					super.controller.plusCharacterAnchor();
					super.controller.addAnchorCharacter(newValue);
				}else{		// �ʼ��� �ƴ� ��� �ϳ��� ���ڷ� �߰�
					super.controller.plusCharacterAnchor();
					super.controller.addAnchorCharacter(new CharacterValue(inputChar));
				}
				
			}
			
		}
		
	}
	
	/**		���� ���� �Է� ó��		*/
	private void _executeValueAsSubstring(CharacterValue value, Character inputChar, boolean isStep3){
		Integer target = (isStep3 ? this.step3 : this.step2).get(inputChar);
		
		if( target == null ){		// ���� ���ڰ� �ƴ� ���
			this._executeValueAsSubstringToNext(value, inputChar, isStep3);
			
		}else{		// ���� ���ڰ� ���� ���
			List<Character> list = value.getValueList();
			list.add(inputChar);

			super.controller.replaceAnchorCharacter( 
					this.parsing(
						this.step1.get(list.get(0)), 
						this.step2.get(list.get(1)), 
						isStep3 ? this.step3.get(list.get(2)) : 0		// �߼��� ��� 0���� ó��
					) 
				);
			
		}
		
	}
	
	/**		���� ���ڸ� �����Ű�� ���� ���ڷ� �߰�		*/
	private void _executeValueAsSubstringToNext(CharacterValue value, Character inputChar, boolean isStep3){
					
		// ���ο� ���ڰ� �ʼ��� ���� �ƴ� ���� �и��ؼ� ó��
		if( this.step1.containsKey(inputChar) ){
			// ���� ���ڸ� �����Ű�� ���� ���ڷ� �߰�
			value.end();
			
			// ���ο� �Է� ���ڰ� �ʼ��� ���
			CharacterValue newValue = new CharacterValue();
			newValue.getValueList().add(inputChar);
			newValue.setValue(inputChar);
			
			super.controller.plusCharacterAnchor();
			super.controller.addAnchorCharacter(newValue);
		}else{
			// ���ο� �Է� ���ڰ� �ʼ��� �ƴ� ���
			
			if( isStep3 ){
				// '��', '��', '��' ���� �������� �˻�
				List<Character> list = value.getValueList();
				
				Character newChar = this.parsingStep2(list.get(1), inputChar);
				if( newChar == null ){
					// ���� ���� ���� �� ���ο� ���ڷ� �߰�
					value.end();
					super.controller.plusCharacterAnchor();
					super.controller.addAnchorCharacter(new CharacterValue(inputChar));
				}else{
					// ���ο� ���ڷ� ��ȯ
					list.add(inputChar);
					super.controller.replaceAnchorCharacter(
							this.parsing(this.step1.get(list.get(0)), this.step2.get(newChar), 0) 
						);
				}
			}else{
				// ���� ���ڸ� �����Ű�� ���� ���ڷ� �߰�
				value.end();
				super.controller.addAnchorCharacter(new CharacterValue(inputChar));
			}
			
			
		}
		
	}
	
	/**		
	 * 	���� ���ڰ� �ϼ��Ǿ� ���� ���� ���¿��� ���� ��ư�� ������ ���
	 * 	�ۼ����� ���ڰ� �ѱ����� Ȯ���ϰ� �ѱ��̶�� ������ �Է�Ű�� �����Ѵ�
	 */
	private void executeValueAsDel(CharacterValue value){
		// �̰��� �Դٸ� ������ ������ �ѱ��� �Է��ϴ� ���´�
		List<Character> list = value.getValueList();
		
		int step1=0, step2=0, step3=0;
		// ����� 0�� ����. 0�� ���� ��ư ������ ��  ó���ȴ�
		switch(list.size()){
		case 1 :			step1 = this.step1.get(list.get(0));		break;
		
		case 2 :			step1 = this.step1.get(list.get(0));		
							step2 = this.step2.get(list.get(1));		break;
							
		case 3 :			step1 = this.step1.get(list.get(0));
							Character checkStep2 = this.parsingStep2(list.get(1), list.get(2));
							if( checkStep2 == null ){
								step2 = this.step2.get(list.get(1));		
								step3 = this.step3.get(list.get(2));
							}else{
								step2 = this.step2.get(checkStep2);
							}
							
							break;
							
		case 4 :			step1 = this.step1.get(list.get(0));
		
							Character cs2 = this.parsingStep2(list.get(1), list.get(2));
							if( cs2 == null ){
								step2 = this.step2.get(list.get(1));
								// null �� ��� ���ǵ��� ���� ��. �α״� this.parsingStep3 ���� ����Ѵ�
								Character step3Value = this.parsingStep3(list.get(2), list.get(3), true);
								if( step3Value == null )			return;
								step3 = this.step3.get( step3Value );
							}else{
								step2 = this.step2.get(cs2);
								step3 = this.step3.get(list.get(3));
							}
							break;
							
		default :		LoggerFactory.getLogger(Commons.CMN_LOGGER_ID).error("Ű�е� �ѱ� ��ȯ �� ���� ���� [����={}]", list.size());
							return;		// �����̹Ƿ� ���̻��� ó�� ����
		}
		
		if( list.size() == 1 )	super.controller.replaceAnchorCharacter( list.get(0).toString() );	
		else							super.controller.replaceAnchorCharacter( this.parsing(step1, step2, step3) );
	}
	
	/**	�߼��� 2���� ĳ���ͷ� �������� ��� ó��		*/
	private Character parsingStep2(char s1, char s2){
		if( s1 == '��' && s2 == '��' )		return '��';
		if( s1 == '��' && s2 == '��' )		return '��';
		if( s1 == '��' && s2 == '��' )		return '��';
		
		if( s1 == '��' && s2 == '��' )		return '��';
		if( s1 == '��' && s2 == '��' )		return '��';
		if( s1 == '��' && s2 == '��' )		return '��';
		
		if( s1 == '��' && s2 == '��' )		return '��';
		
		return null;
	}
	
	/**	������ 2���� ĳ���ͷ� �������� ��� ó��		*/
	private Character parsingStep3(char s1, char s2, boolean log){
//		if( s1 == '��' && s2 == '��' )		return '��';		// Ű�е忡 ����
		if( s1 == '��' && s2 == '��' )		return '��';
		
		if( s1 == '��' && s2 == '��' )		return '��';
		if( s1 == '��' && s2 == '��' )		return '��';
		
		if( s1 == '��' && s2 == '��' )		return '��';
		
//		if( s1 == '��' && s2 == '��' )		return '��';		// Ű�е忡 ����
		
		if( s1 == '��' && s2 == '��' )		return '��';
		if( s1 == '��' && s2 == '��' )		return '��';
		if( s1 == '��' && s2 == '��' )		return '��';
		if( s1 == '��' && s2 == '��' )		return '��';
		if( s1 == '��' && s2 == '��' )		return '��';
		if( s1 == '��' && s2 == '��' )		return '��';
		if( s1 == '��' && s2 == '��' )		return '��';
		
		// �������� ����� ���⿡�� �� �� ������ ������ ���� �α� ó��
		if( log )
			LoggerFactory.getLogger(Commons.CMN_LOGGER_ID).error("���ǵ��� ���� ���� ó�� [Step3={}, Step4={}]", s1, s2);
		
		return null;
	}
	
	/**	�Էµ� ���� �����ڵ�� ��ȯ �� ĳ���� ��ȯ		*/
	private String parsing(int step1, int step2, int step3){
		return Character.toString( (char)(44032 + (step1*588) + (step2*28) + step3) );
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resource) {
		if( resource == null )		return;
		
		super.initialize(url, resource);
		
		// ����Ʈ ��ư
		Object obj = resource.getObject(KeyPadController.RESOURCE_KEYPAD_LOWER_TYPE);
		if( obj == null ){		// �������� ���ٸ� �⺻�ѱ۷� ����
			this.isKeypadLower = true;
		}else{
			try{
				this.isKeypadLower = (Boolean)obj;
			}catch(Exception e){
				// �ƿ� �߸��� ���� ���� ���
				Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
				logger.error("���ǵ��� ���� �ѱ� ����Ʈ ���� ���ҽ� �� [��={}]", obj);
				this.isKeypadLower = true;
			}
		}
	}
}
