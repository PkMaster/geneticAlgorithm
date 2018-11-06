package ga.aps.controller.services;

import java.util.List;
import ga.aps.controller.impl.FilterConditionImpl;
import ga.aps.controller.myConnection.DBConnection;
import ga.aps.controller.dao.FilterConditionDao;
import ga.aps.controller.entity.APS_FilterCondition;

public class FilterConditionService implements FilterConditionDao {
	private DBConnection dbc;
	private FilterConditionImpl dao = null;
	
	//���ݿ�����+ʵ����Impl
	public FilterConditionService() throws Exception{
		dbc = new DBConnection();
		dao = new FilterConditionImpl(dbc.getConnection());
	}
	
	//����
//	@Override
//	public boolean doCreate(APS_FilterCondition FilterCondition) throws Exception {
//		// TODO Auto-generated method stub
//		boolean flag = false;
//		APS_FilterCondition location = new APS_FilterCondition();
//		location = dao.findById(FilterCondition.getSiteCode(),FilterCondition.getTemplateCode());
//		if (location==null) {
//			flag = dao.doCreate(FilterCondition);
//		}
//		dbc.close();
//		return flag;
//	}
//	
	//����������Ų�ѯ
	@Override
	public List<APS_FilterCondition> findFccodeAll(String SiteCode,String FilterConditionCode) throws Exception {
		// TODO Auto-generated method stub
		List<APS_FilterCondition>	list = dao.findFccodeAll(SiteCode,FilterConditionCode);
		dbc.close();
		return list;
	}
	
	//��ѯ����
	@Override
	public List<APS_FilterCondition> findAll(String SiteCode,String TemplateCode) throws Exception {
		// TODO Auto-generated method stub
		List<APS_FilterCondition>	list = dao.findAll(SiteCode,TemplateCode);
		dbc.close();
		return list;
	}

	@Override
	public APS_FilterCondition findById(String SiteCode,String TemplateCode) throws Exception {
		// TODO Auto-generated method stub
		APS_FilterCondition location = dao.findById(SiteCode,TemplateCode);
		dbc.close();
		return location;
	}

}