package kr.co.bomz.keypad.key;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.Initializable;
import kr.co.bomz.cmn.Commons;
import kr.co.bomz.keypad.KeyPadController;

/**
 * 	���, ��ȣ, ���� �� Ű�е� ���� ����ü
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public abstract class Key implements Initializable{

	/**		��ġ �� �Է��� ���� TextField ���ҽ� Ű ��		*/
	public static final String RESOURCE_KEY = "VF";
	

	protected KeyPadController controller;
	
	@Override
	public void initialize(URL url, ResourceBundle resource) {
		Object obj = resource.getObject(Key.RESOURCE_KEY);
		if( obj == null ){
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			logger.error("Ű�е� �Է��ʵ� ���ҵ� NULL");
		}else{
			if( obj instanceof KeyPadController ){
				this.controller = (KeyPadController)obj;
			}else{
				Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
				logger.error("Ű�е� ��Ʈ�ѷ� ���ҵ� ����ȯ ���� [���ҵ�={}]",  obj);
			}
		}
		
	}
	
}
