package kr.or.ddit_basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class homeWork_Student {
	public static void main(String[] args) {
		List <Study> studyList = new ArrayList<Study>();
		studyList.add(new Study("1", "홍길동", 50,60,72));
		studyList.add(new Study("5", "변학도", 55,62,23));
		studyList.add(new Study("9", "성춘향", 67,55,44));
		studyList.add(new Study("3", "이순신", 35,46,33));
		studyList.add(new Study("6", "강감찬", 11,88,49));
		studyList.add(new Study("2", "일지매", 44,55,85));
		Collections.sort(studyList); 
			System.out.println("학번의 오름차순으로 정렬 후");
			for(Study stu : studyList) {
				System.out.println(stu);
			}
			System.out.println("==================================");
			
			// 외부 정렬 기준을 이용한 정렬하기
			Collections.sort(studyList, new SortByTotal());
			System.out.println("총점의 내림차순으로 정렬 후");
			for(Study stu : studyList) {
				System.out.println(stu);
			}
			System.out.println("==================================");
			
		}
}
	


class SortByTotal implements Comparator<Study>{
	@Override
	public int compare(Study std1, Study std2) {
		if(std1.getTotal() == std2.getTotal()){
			return std1.getNum().compareTo(std2.getNum()) * -1;
		}else{
			return Integer.compare(std1.getTotal(), std2.getTotal()) * -1;
		}
		
	}
}

	


class Study implements Comparable<Study> {
	private String num; // 학번
	private String name; // 이름
	private int kor; // 국어
	private int eng; // 영어
	private int math; // 수학
	private int total; // 총합
	
	public Study(String num, String name, int kor, int eng, int math) {
		this.num = num;
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.total = kor + eng + math;
	}

	public Study(String num, String name, int kor, int eng, int math, int total) {
		this.num = num;
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	@Override
	public String toString() {
		return "Study [num=" + num + ", name=" + name + ", kor=" + kor + ", eng=" + eng + ", math=" + math + ", total=" + total + "]";
	}

	@Override
	public int compareTo(Study study) {
		return this.getNum().compareTo(study.getNum());
	}
	
}