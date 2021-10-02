package kr.co.bomz.keypad;

import java.util.ArrayList;
import java.util.List;

/**
 * 	�Է�â ���� ��
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class CharacterValue {

	/**		�Է� �Ϸ� ��		*/
	private Character value;
	
	private boolean end;
	
	private List<Character> valueList; 
	
	public CharacterValue(){
		this.end = false;
	}
	
	public CharacterValue(char value){
		this.value = value;
		this.end = true;
	}

	public boolean isNullValue(){
		return this.value == null;
	}
	
	public void setValue(char value){
		this.value = value;
	}
	
	public Character getValue() {
		return value;
	}

	public boolean isEnd(){
		return this.end;
	}
	
	public void end(){
		this.end = true;
	}
	
	public boolean isNullValueList(){
		return this.valueList == null;
	}
	
	public List<Character> getValueList() {
		if( this.valueList == null )		this.valueList = new ArrayList<Character>();
		return this.valueList;
	}
	
}
