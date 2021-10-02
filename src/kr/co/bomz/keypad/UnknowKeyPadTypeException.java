package kr.co.bomz.keypad;

/**
 * 	�� �� ���� Ű�е� Ÿ���� ������ ��� �߻�
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class UnknowKeyPadTypeException extends RuntimeException{
	private static final long serialVersionUID = -6413946998262752299L;

	public UnknowKeyPadTypeException(char keyPadType){
		super("See KeyPadController class static field [Request KeyPad Type=" + keyPadType + "]");
	}
}
