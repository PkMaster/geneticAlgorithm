package ga.test.imp;

import ga.test.dao.CSCallBack;

/** @author Jeff Lee 
 * @since 2015-10-21 21:24:15 
 * �ص�ģʽ-ģ�������� 
*/ 
public class Server {      
	public void getClientMsg(CSCallBack csCallBack , String msg) {         
		System.out.println("����ˣ�����˽��յ��ͻ��˷��͵���ϢΪ:" + msg);          
		// ģ��������Ҫ�����ݴ���         
		try {             
			//Thread.sleep(5 * 1000);
			System.out.println("�����:ִ��Thread");    
		} catch (Exception e) {             //InterruptedException 
			e.printStackTrace();         
		}         
		System.out.println("�����:���ݴ���ɹ������سɹ�״̬ 200");         
		String status = "200";         
		csCallBack.process(status);     
	}
}
