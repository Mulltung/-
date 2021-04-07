package kr.or.ddit_basic;

import java.util.HashSet;

public class baseBall {
	public static void main(String[] args) {
		// 1~9사이의 중복되지 않는 정수 3개 만들기
		HashSet<Integer> intRnd = new HashSet<Integer>();

		while (intRnd.size() < 3) { // 데이터가 3개가 될때까지 반복...
			int num = (int) (Math.random() * 9 + 1);
			intRnd.add(num);
		}

		System.out.println("만들어진 난수들 : " + intRnd);

	}

}
