package ga.aps.controller.dao;

import java.util.List;
import ga.aps.controller.entity.*;

public interface FilterConditionDao {
	//����
	//public boolean doCreate(APS_FilterCondition filterCondition) throws Exception;
	//���ݹ���/������Ų�ѯ
	public List<APS_FilterCondition> findFccodeAll(String SiteCode,String FilterConditionCode) throws Exception;
	//���ݹ���/ģ���Ų�ѯ����
	public List<APS_FilterCondition> findAll(String SiteCode,String TemplateCode) throws Exception;
	//������ѯ
	public APS_FilterCondition findById(String SiteCode,String FilterConditionCode) throws Exception;
}
