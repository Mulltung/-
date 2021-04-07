package kr.or.ddit.basic;

public class homework1 {
	public enum Planet {
		수성(2439), 
		금성(6052), 
		지구(6371), 
		성(3390), 
		목성(69911), 
		토성(58232), 
		천왕성(25362), 
		해왕성(24622);
	
	private int l;
	
	//생성자
	Planet(int data) {
		l = data;
	}
	
	public int getL() {
		return l;
	}
	
}
	public static void main(String[] args) {
		
		Planet[] enumArr = Planet.values();
	
		float pi = 3.141592f;
		for(int i = 0; i < enumArr.length; i++) {
			System.out.println("============================================================");
			System.out.println(enumArr[i].name() + " : " + enumArr[i].getL() + "\n" + "면적 : "+ enumArr[i].getL()*enumArr[i].getL()*pi*4 +" Km²");
			System.out.println("============================================================");
		}
	}	
}

