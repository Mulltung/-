package kr.or.ddit.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class HotelMap {
	private Scanner sc;
	private Map<String, HotelVO> hotelMap; //hotel손님 데이터를 넣을 map형태의 hotelMap변수를 생성
	
	public HotelMap() {
		sc = new Scanner(System.in);              //HotelMap 클래스 성성할때 생성자 호출할때 객체생성
		hotelMap = new HashMap<String,HotelVO>(); //hotelmap 객체를 생성한다.
												  //map형태 안에 HashMap형태로 객체를 생성한다. (다형성)
	}
	// 프로그램 시작
	public void hotelOpen() {
		System.out.println("*******************************");
		System.out.println("\t호텔 문을 열었습니다.\t");
		System.out.println("*******************************");
		
		
		//사용할 파일 객체를 만든다.
		File f1 = new File("d:/D_Other/hotelMember.txt");
		
		//파일 존재 여부
		if(f1.exists()) {
			Hotelimport();
		}
		
		while (true) {
			System.out.println("************************************");
			System.out.println("어떤 업무를 하시겠습니까?");
			System.out.println("1.체크인    2.체크아웃     3.객실상태     4.업무종료");
			System.out.println("************************************");
			System.out.println();
			int menuNum = sc.nextInt(); // 메뉴 번호 입력
			sc.nextLine();
			switch (menuNum) {
			case 1:
				checkIn(); // 체크인
				break;
			case 2:
				checkOut(); // 체크아웃
				break;
			case 3:
				roomState(); // 객실상태
				break;
			case 4:
				HotelExport();
				System.out.println("프로그램을 종료합니다...");
				return;
			default:
				System.out.println("잘못 입력했습니다. 다시입력하세요.");
			} // switch문
		} // while문
	}
	
	
	
	
	/*
	 * 저장한 파일을 불러와 역직렬화 하여 출력하는 메서드
	 */
	private void Hotelimport() {
		
		//저장한 객체를 읽어와 출력
		try {
			//입력용 스트림 객체 생성
			ObjectInputStream ois =
					new ObjectInputStream(
							new BufferedInputStream(
									new FileInputStream("d:/D_Other/hotelMember.txt")));
			
			Object obj = null;
			
			//역직렬화 작업 수행
			while((obj = ois.readObject()) != null) {  				
				HotelVO hotelVO = (HotelVO)obj;
				hotelMap.put(hotelVO.getrNum(),hotelVO);
			}
			
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("출력 작업 끝");
		}catch (ClassNotFoundException e) {
	         e.printStackTrace();
	      }

	}
	
	
	
	/*
	 * 입력한 고객 정보를 직렬화 후  파일로 저장하는 메서드
	 */
	private void HotelExport() {
		//키만 추출함
		Set<String> keySet = hotelMap.keySet();
		
		//출력용 스트림 생성
		//맵을 읽어온다.
		try {
			ObjectOutputStream oos = 
					new ObjectOutputStream(
							new BufferedOutputStream(
									new FileOutputStream("d:/D_Other/hotelMember.txt")));
		
			//추출한 키값 목록과 직렬화하여 쓰기작업
			for(String key: keySet) {
				System.out.println(key + " : " + hotelMap.get(key));
				oos.writeObject(hotelMap.get(key));
				System.out.println("직렬화하여 메모리에 저장 완료");
			}
			
			//사용한 메모리 반환
			oos.close();
		
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 고객 정보를 삭제하는 메서드
	 */
	private void checkOut() {
		System.out.println();
		System.out.println("어느 방을 체크아웃 하시겠습니까?");
		System.out.print("객실 번호 >> ");
		String rNum = sc.next();
		if(hotelMap.remove(rNum) == null) {
			System.out.println(rNum + " 번 방에 사용 중인 고객님은 없습니다.");
		} else {
			System.out.println("체크아웃하셨습니다. 저희 호텔을 이용해주셔서 감사합니다.");
			System.out.println("좋은 하루 되십시오. 고객님.");
		}
	}
	
	
	
	/**
	 * 호텔에 머무는 고객들의 객실 상태를 알려주는 메서드
	 */
	private void roomState() {
		Set<String> keySet = hotelMap.keySet();
		System.out.println("=====================================");
		System.out.println(" 번호\t객실번호\t\t이름 ");
		System.out.println("=====================================");
		
		if(keySet.size() == 0) {
			System.out.println("등록된 룸이 없습니다");
		} else {
			Iterator<String> it = keySet.iterator();
			
			int count = 0;
			while(it.hasNext()) {
				count++;
				String rNum = it.next();
				HotelVO h = hotelMap.get(rNum);
				System.out.println(" " + count + "번"+ "\t" +"방번호 : "+ h.getrNum() +"\t" + "투숙객 : "+ h.getName());

			}
		}
		System.out.println("================================");
	}
	
	/**
	 * 새로운 고객 정보를 등록하는 메서드
	 */
	private void checkIn() {
		System.out.println();
		System.out.println("어느 방에 체크인 하시겠습니까?");
		System.out.print("방 번호 입력 => ");
		String rNum = sc.next();
		
		if(hotelMap.get(rNum) != null) {
			System.out.println("죄송합니다. 다른 손님이 방을 사용중입니다.");
			return;
		}
		
		sc.nextLine();
		System.out.println("성함이 어떻게 되십니까?");
		System.out.print("이름 입력 => ");
		String name = sc.next();
		
		hotelMap.put(rNum, new HotelVO(rNum, name));
		System.out.println(name + " 님 체크인 준비가 완료되었습니다.");
		
	}
	
	
	public static void main(String[] args) {
		new HotelMap().hotelOpen();
	}


}

/**
 * 고객의 정보를 저장하기 위한 VO클래스
 */
class HotelVO implements Serializable{
	private String rNum;
	private String name;
	
	public HotelVO(String rNum, String name) {
		super();
		this.name = name;
		this.rNum = rNum;
	}
	
	public String getrNum() {
		return rNum;
	}


	public void setrNum(String rNum) {
		this.rNum = rNum;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "Hotel [rNum=" + rNum + ", name=" + name + "]";
	}
}