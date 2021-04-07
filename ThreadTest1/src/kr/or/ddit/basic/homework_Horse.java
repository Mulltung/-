package kr.or.ddit.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class homework_Horse {
	public static int rank = 1; 
	public static List<Horse> horseList = new ArrayList<Horse>(); // 말관리를 위한 리스트 변수 선언

	public static void main(String[] args) {

		horseList.add(new Horse("01번말"));
		horseList.add(new Horse("02번말"));
		horseList.add(new Horse("03번말"));
		horseList.add(new Horse("04번말"));
		horseList.add(new Horse("05번말"));
		horseList.add(new Horse("06번말"));
		horseList.add(new Horse("07번말"));
		horseList.add(new Horse("08번말"));
		horseList.add(new Horse("09번말"));
		horseList.add(new Horse("10번말"));

		HorsePositionDisplay hpd = new HorsePositionDisplay();
		hpd.start();

		for (int i = 0; i < horseList.size(); i++) {
			horseList.get(i).start();
		}

		try {
			hpd.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		System.out.println();
		System.out.println("경마 종료");
		System.out.println();

		Collections.sort(horseList); // 순위 오름차순으로 정렬

		System.out.println("===================================================");
		System.out.println("                    경마 순위                                                  ");
		System.out.println("===================================================");
		for (int i = 0; i < horseList.size(); i++) {
			System.out.println(horseList.get(i).getHorseRank() + "등" + " == >" + horseList.get(i).getHorseName());
		}

	}
}

class Horse extends Thread implements Comparable<Horse> {
	private String horseName; // 말이름
	private int horseRank; // 순위
	private int horsePosi; // 위치

	/**
	 * 생성자
	 */
	public Horse(String horseName) {
		this.horseName = horseName;
	}

	public String getHorseName() {
		return horseName;
	}

	public void setHorseName(String horseName) {
		this.horseName = horseName;
	}

	public int getHorseRank() {
		return horseRank;
	}

	public void setHorseRank(int horseRank) {
		this.horseRank = horseRank;
	}

	public int getHorsePosi() {
		return horsePosi;
	}

	private void setHorsePosi(int horsePosi) {
		this.horsePosi = horsePosi;
	}

	@Override
	public int compareTo(Horse o) {
		return Integer.compare(horseRank, o.getHorseRank()); // 순위를 오름차순으로 정렬
	}

	@Override
	public void run() {
		for (int i = 0; i < 50; i++) {
			try {
				Thread.sleep((int) (Math.random() * 500)); // 구간 사이 딜레이 주기
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 각 구간으로 말의 위치 이동
			setHorsePosi(i); 
		}
		this.horseRank = homework_Horse.rank;
		homework_Horse.rank++;
	}
}


/**
 * 전체 말의 위치를 출력하기 위한 스레드 클래스
 */
class HorsePositionDisplay extends Thread {

	/**
	 * 화면 출력 정리를 위한 메서드
	 * 일정한 여백을 준다.
	 */
	public void clear() {
		for (int i = 0; i < 30; i++) {
			System.out.println();
		}
	}

	@Override
	public void run() {

		while (true) {

			clear(); // 정렬용 메서드

			int finishedCnt = 0; // 도착한 말의 수
			System.out.println("경마 시작");
			System.out.println("==========================================================");
			System.out.println();

			for (int i = 0; i < homework_Horse.horseList.size(); i++) {

				String horseCourse = "--------------------------------------------------"; // 50구간
				Horse horse = homework_Horse.horseList.get(i);

				if (horse.getHorsePosi() != 49) { // 도착 X
					System.out.print(horse.getHorseName() + " : ");
					System.out.print(horseCourse.substring(0, horse.getHorsePosi()) + "뛴다");
					System.out.println(horseCourse.substring(horse.getHorsePosi(), 49));
				} else { // 도착 O
					System.out.print(horse.getHorseName() + " : ");
					System.out.print(horseCourse.substring(0, horse.getHorsePosi()) + "도착");
					System.out.println();
					
					// 도착한 말수 증가
					finishedCnt++; 
				}
			}

			// 모든 경주말이 도착한 경우 쓰레드 종료
			if (finishedCnt == 10) { 
				return; 
			}

			try {
				Thread.sleep(500); //0.5초마다 화면 출력
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}