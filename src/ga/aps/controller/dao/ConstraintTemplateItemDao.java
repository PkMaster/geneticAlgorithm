package ga.aps.controller.dao;

import java.util.List;

import ga.aps.controller.entity.APS_ConstraintTemplateItem;;

public interface ConstraintTemplateItemDao {
	//����
	//public boolean doCreate(APS_FilterCondition filterCondition) throws Exception;
	//��ѯ����
	public List<APS_ConstraintTemplateItem> findAll(String SiteCode,String TemplateCode) throws Exception;
	//������ѯ
	public APS_ConstraintTemplateItem findById(String SiteCode,String TemplateCode) throws Exception;
}
