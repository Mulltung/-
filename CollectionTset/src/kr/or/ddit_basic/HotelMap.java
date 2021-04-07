package kr.or.ddit_basic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class HotelMap {
	private Scanner sc;
	private Map<String, HotelVO> hotelMap;
	
	public HotelMap() {
		sc = new Scanner(System.in);
		hotelMap = new HashMap<>();
	}
	// 프로그램 시작
	public void hotelOpen() {
		System.out.println("*******************************");
		System.out.println("\t호텔 문을 열었습니다.\t");
		System.out.println("*******************************");
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
				System.out.println("프로그램을 종료합니다...");
				return;
			default:
				System.out.println("잘못 입력했습니다. 다시입력하세요.");
			} // switch문
		} // while문
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
class HotelVO{
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