package ga.aps.controller.dao;

import java.util.List;
import ga.aps.controller.entity.*;

public interface ConstaintTemplateDao {
	//����
	public boolean doCreate(APS_ConstaintTemplate ConstaintTemplate) throws Exception;
	//��ѯ����
	public List<APS_ConstaintTemplate> findAll() throws Exception;
	//��ѯ����
	public APS_ConstaintTemplate findById(String SiteCode,String TemplateCode) throws Exception;
}
