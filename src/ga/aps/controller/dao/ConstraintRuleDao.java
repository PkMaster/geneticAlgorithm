package ga.aps.controller.dao;

import java.util.List;
import ga.aps.controller.entity.APS_ConstraintRule;

public interface ConstraintRuleDao {
	//����
	//public boolean doCreate(APS_FilterCondition filterCondition) throws Exception;
	//��ѯ����
	public List<APS_ConstraintRule> findAll(String SiteCode,String TemplateCode) throws Exception;
	//������ѯ
	public APS_ConstraintRule findById(String SiteCode,String TemplateCode) throws Exception;
}
