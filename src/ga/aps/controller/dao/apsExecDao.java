package ga.aps.controller.dao;

import java.util.*;
import ga.aps.controller.entity.*;

public interface apsExecDao {
	
	//1.��ȡ��������-����-����ֵ
	public Map<String, Object> findVehicleInfo(String VersionNo) throws Exception;

	//2.����������Ⱥ����
	public Map<String, Object> VehiclePopulation(long PopulationSize,Map<String, Object> map) throws Exception;
	
	//3.��Ⱥ��Ӧ�Ⱥ���ֵ����	
	public Map<String, Object> PopulationFitness(APScs_Thread_parameters ThreadPara,APScs_Initial_Parameters Parameters,Map<String, Object> map) throws Exception;
}
