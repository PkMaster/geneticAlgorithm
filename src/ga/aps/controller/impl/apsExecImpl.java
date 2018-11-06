package ga.aps.controller.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.*;
import ga.aps.controller.dao.apsExecDao;
import ga.aps.controller.myConnection.*;
import ga.aps.controller.factory.ConstraintTemplateItemFactory;
import ga.aps.controller.entity.*;
import ga.aps.controller.callable.*;

public class apsExecImpl implements apsExecDao {
	private Connection conn;
	private ExecutorService threadpool;
	
	private PreparedStatement stat = null;
	
	public apsExecImpl(Connection conn) {
		this.conn = conn;
	}

	public apsExecImpl(Connection conn,ExecutorService threadpool) {
		this.conn = conn;
		this.threadpool = threadpool;
	}

	//1.��ѯ����������������ֵ
	@Override
	public Map<String, Object> findVehicleInfo(String VersionNo) throws Exception {
		// TODO Auto-generated method stub
		String sqlVehicle 	= "select * from APSdt_Import_Vehicle 			where VersionNo = ?";
		String sqlAttribute = "select * from APSdt_Import_Vehicle_Attribute where VersionNo = ?";
		
		//�������ϼ�����
		stat = conn.prepareStatement(sqlVehicle);
		stat.setString(1, VersionNo);
		ResultSet rsVehicle = stat.executeQuery();
		//��������ֵ
		stat = conn.prepareStatement(sqlAttribute);
		stat.setString(1, VersionNo);
		ResultSet rsAttribute = stat.executeQuery();
		
		APSdt_Import_Vehicle 			vehicle   = null;
		APSdt_Import_Vehicle_Attribute 	attribute = null;
		
		List<APSdt_Import_Vehicle> 			 listVehicle   = new ArrayList<APSdt_Import_Vehicle>();
		List<APSdt_Import_Vehicle_Attribute> listAttribute = new ArrayList<APSdt_Import_Vehicle_Attribute>();
		
		while(rsVehicle.next()) {			
			vehicle = new APSdt_Import_Vehicle();			
			vehicle.setVersionNo(rsVehicle.getString(1));
			vehicle.setOrderNo(rsVehicle.getInt(2));
			vehicle.setSiteCode(rsVehicle.getString(3));
			vehicle.setMaterial(rsVehicle.getString(4));
			vehicle.setPoNumber(rsVehicle.getString(5));
			vehicle.setAmount(rsVehicle.getLong(6));
			vehicle.setCreatDate(rsVehicle.getDate(7));
			vehicle.setCreatUser(rsVehicle.getString(8));
			listVehicle.add(vehicle);
		}

		while(rsAttribute.next()) {			
			attribute = new APSdt_Import_Vehicle_Attribute();			
			attribute.setVersionNO(rsAttribute.getString(1));
			attribute.setAttriNo(rsAttribute.getString(2));
			attribute.setSiteCode(rsAttribute.getString(3));
			attribute.setMaterial(rsAttribute.getString(4));
			attribute.setATBEZ(rsAttribute.getString(5));
			attribute.setATWRT(rsAttribute.getString(6));
			listAttribute.add(attribute);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("vehicle", listVehicle);
		map.put("attribute", listAttribute);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> VehiclePopulation(long PopulationSize,Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		//��������
		long PopuIndex = 0;
		List<APSdt_Import_Vehicle> 			 listVehicle   = (List<APSdt_Import_Vehicle>) map.get("vehicle");
		List<APSdt_Import_Vehicle_Attribute> listAttribute = (List<APSdt_Import_Vehicle_Attribute>) map.get("attribute");
		
		//�洢���������嵥
		APScs_group_entity		 strucMatnr = null;
		List<APScs_group_entity> listMatnr  = new ArrayList<APScs_group_entity>();
		List<APScs_group_entity> shuffleMat = new ArrayList<APScs_group_entity>();
		
		//��ȡ�����������嵥		
		for(int i=0;i<listVehicle.size();i++) {
			for(int j=0;j<listVehicle.get(i).getAmount();j++) {
				strucMatnr = new APScs_group_entity();
				strucMatnr.setPopuNo(0);
				strucMatnr.setSerialNo(j);
				strucMatnr.setMaterial(listVehicle.get(i).getMaterial());
				listMatnr.add(strucMatnr);
			}
		}
		
		//������Ⱥ��ģ��������������嵥		
		for(int i=1;i<=PopulationSize;i++) {
			//list���shuffle
			int j=0;
			Collections.shuffle(listMatnr); 
			
			for(APScs_group_entity strMat : listMatnr){
				strucMatnr = new APScs_group_entity();
				strucMatnr.setPopuNo(i);
				strucMatnr.setSerialNo(j);
				strucMatnr.setMaterial(strMat.getMaterial());
				shuffleMat.add(strucMatnr);
				j++;
			}
		}
		
		Map<String, Object> mapMaterial = new HashMap<String, Object>();
		mapMaterial.put("Material", shuffleMat);		
		return mapMaterial;
	}
	
	//3.��Ⱥ��Ӧ�Ⱥ���ֵ����
	@SuppressWarnings({ "unchecked"})
	@Override
	public Map<String, Object> PopulationFitness(APScs_Thread_parameters ThreadPara,APScs_Initial_Parameters Parameters,Map<String, Object> map) throws Exception{
		//��������
		int x = 0;
		int y = 0;
		
		//�̲߳���
		CompletionService<List<APScs_group_entity>> ServicedailyProduction  = null;
		CompletionService<List<APScs_group_entity>> ServicedailyProportions = null;
		
		//Լ��ģ��
		List<APS_ConstraintTemplateItem> conStraintTemplate = new ArrayList<APS_ConstraintTemplateItem>();
		
		//��ȡԼ������
		conStraintTemplate = ConstraintTemplateItemFactory.getInstance().findAll(Parameters.getSiteCode(), Parameters.getTemplateCode());
		
		
		//��ȡ��Ⱥ������
		List<APScs_group_entity> shuffleMat = (List<APScs_group_entity>) map.get("Material");
		List<APScs_group_entity> listProduction = new ArrayList<APScs_group_entity>();
		//��Ⱥ�����Ӧ�Ⱥ���ֵ����
		
		for(APS_ConstraintTemplateItem ConstraintTemplateStruc: conStraintTemplate) {
			//����������Ϊ��
			if(ConstraintTemplateStruc.getFilterConditionCode() != null || ConstraintTemplateStruc.getFilterConditionCode().length() != 0) {
				
			}
			
		}
		
		ServicedailyProduction 	= new ExecutorCompletionService<List<APScs_group_entity>>(threadpool);
		ServicedailyProportions = new ExecutorCompletionService<List<APScs_group_entity>>(threadpool);
		
		while( x < Parameters.getPopulation() ) {
			listProduction = new ArrayList<APScs_group_entity>();
			for(APScs_group_entity struc : shuffleMat) {
				if (struc.getPopuNo() == x) {
					listProduction.add(struc);
				}
			}	
			ServicedailyProduction.submit(new dailyProduction(listProduction));
			x++;
		}

		List<APScs_group_entity> listResult = new ArrayList<APScs_group_entity>();
		List<APScs_group_entity> listAll = new ArrayList<APScs_group_entity>();
		
		while(y < Parameters.getPopulation()) {
			listResult = null;
			listResult = ServicedailyProduction.take().get();
			listAll.addAll(listResult);
			
			//���շ��ؽ����ִ����һԼ������
			if(listResult != null) {
				ServicedailyProportions.submit(new dailyProportions(listResult));
			}
			
			y++;
		}
	
		for(int i=0;i<listAll.size();i++) {
			System.out.println(String.valueOf(listAll.get(i).getPopuNo()) + "   " + listAll.get(i).getMaterial() + "   " +  String.valueOf(listAll.get(i).getSerialNo()));
		}

		//results.get();
		System.out.println("main program working end");
		
		return null;
	}

}
