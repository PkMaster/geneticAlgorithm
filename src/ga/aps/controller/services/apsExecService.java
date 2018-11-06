package ga.aps.controller.services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorService;
import ga.aps.controller.dao.*;
import ga.aps.controller.dao.apsExecDao;
import ga.aps.controller.entity.*;
import ga.aps.controller.impl.apsExecImpl;
import ga.aps.controller.myConnection.*;

public class apsExecService implements apsExecDao  {

	private DBConnection dbc;
	private ThreadPool ThreadPool;
	private apsExecDao dao = null;
	
	public apsExecService() throws Exception {
		System.out.println("proxy-apsExecService");
		dbc = new DBConnection();
		dao = new apsExecImpl(dbc.getConnection());
	}
	
	//���ݿ�����+ʵ����Impl + �̳߳�
	public apsExecService(APScs_Thread_parameters ThreadPara) throws Exception{
		System.out.println("proxy-apsExecService");
		//���ݿ�����
		dbc = new DBConnection();
		ThreadPool = new ThreadPool(ThreadPara);
		dao = new apsExecImpl(dbc.getConnection(),ThreadPool.getThreadPool());		
	}
	
	//1.��ѯ��������-����-����
	@Override
	public Map<String, Object> findVehicleInfo(String VersionNo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("proxy-ConstanintTemplateService-findVehicleInfo");
		Map<String, Object> map = dao.findVehicleInfo(VersionNo);
		dbc.close();
		return map;
	}
	
	//2.����������Ⱥ����
	@Override
	public Map<String, Object> VehiclePopulation(long PopulationSize,Map<String, Object> map) throws Exception{
		Map<String, Object> mapPopulation = dao.VehiclePopulation(PopulationSize,map);
		dbc.close();
		return mapPopulation;
	}
	
	//3.��Ⱥ��Ӧ�Ⱥ���ֵ����	
	public Map<String, Object> PopulationFitness(APScs_Thread_parameters ThreadPara,APScs_Initial_Parameters Parameters,Map<String, Object> map) throws Exception{
		Map<String, Object> mapPopulation = dao.PopulationFitness(ThreadPara,Parameters,map);
		dbc.close();
		ThreadPool.ThreadShutdown();
		return mapPopulation;
	}
}
