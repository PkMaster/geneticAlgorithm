package ga.aps.controller.entity;

public class APScs_group_entity {
	private int 	PopuNo;       	//��Ⱥ����
	private int 	SerialNo;     	//��Ⱥ����˳���
	private String 	Material;  		//��Ⱥ����
	private int 	FgProduction; 	//��ʶ-�ղ���ֵ-1
	
	public APScs_group_entity() {		
		
	}

	public APScs_group_entity(int popuNo, int serialNo, String material, int fgProduction) {
		super();
		PopuNo = popuNo;
		SerialNo = serialNo;
		Material = material;
		FgProduction = fgProduction;
	}

	public int getPopuNo() {
		return PopuNo;
	}

	public void setPopuNo(int popuNo) {
		PopuNo = popuNo;
	}

	public int getSerialNo() {
		return SerialNo;
	}

	public void setSerialNo(int serialNo) {
		SerialNo = serialNo;
	}

	public String getMaterial() {
		return Material;
	}

	public void setMaterial(String material) {
		Material = material;
	}

	public int getFgProduction() {
		return FgProduction;
	}

	public void setFgProduction(int fgProduction) {
		FgProduction = fgProduction;
	}

	
}
