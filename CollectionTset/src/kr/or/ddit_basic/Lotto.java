package kr.or.ddit_basic;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Lotto {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("=============================");
		System.out.println("Lotto 프로그램");
		System.out.println("=============================");
		System.out.println("1. Lotto 구입");
		System.out.println("2. 프로그램 종료");
		
		while(true) {
			int input = sc.nextInt();
			switch(input) {
			case 1:
				Lottobuy();
				break;
			case 2:
				System.exit(0);
			case 3:
				System.out.println("잘못입력");
				break;
			}
		}
	}

	private static void Lottobuy() {
		System.out.println("로또 구입 시작");
		System.out.println("1000원에 로또번호 하나입니다.");
		System.out.print("금액을 입력하세요 :");
		
		Scanner sc = new Scanner(System.in);
		int user = sc.nextInt();
		
		if(user >= 1000) {
			System.out.println("행운의 로또번호는 아래와 같습니다.");
			for (int i = 1 ; i <= user /1000; i++) {
				Set<Integer> lotto = new HashSet<>();
				while (lotto.size() < 6	) {
					int random = (int)(Math.random() * 45 + 1);
					lotto.add(random);
				}
					System.out.println("로또 번호" + i +" : "+ lotto);
			}
			System.out.println();
			System.out.println("받은 금액은 " + user + "이고 거스름돈은 " + user%1000 + "원입니다.");
			System.out.println("===================================");
		}
		
	}

}
