package kr.or.ddit.basic;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class homework_IO2 {
	public static void main(String[] args) throws IOException{

		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream("d:/D_Other/Tulips.jpg"); // 원본파일
			fos = new FileOutputStream("d:/D_Other/복사본_Tulips.jpg"); // 복사위치

			int readcount = 0;

			while ((readcount = fis.read()) != -1) {
				fos.write(readcount); // 파일 복사
			}
			
			
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
		
		fis.close();
		fos.close();

	}
}
