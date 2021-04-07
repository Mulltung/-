package kr.or.ddit.basic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class homework_IO {
	public static void main(String[] args) throws IOException{

		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		int count = 0;
		try {
			fis = new FileInputStream("d:/D_Other/Tulips.jpg"); // 원본파일
			fos = new FileOutputStream("d:/D_Other/복사본_Tulips.jpg"); // 복사위치

			byte[] buffer = new byte[8192];
			int readcount = 0;

			while ((readcount = fis.read(buffer)) != -1) {
				count++;
				fos.write(buffer, 0, readcount); // 파일 복사
			}
			System.out.println(count);
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
		
		fis.close();
		fos.close();

	}
}



