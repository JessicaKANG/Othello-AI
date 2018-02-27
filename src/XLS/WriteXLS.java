package XLS;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


public class WriteXLS {
	
	public WriteXLS(int nodenum, long time, int nodenump, long timep){
		try {
			Workbook wb = Workbook.getWorkbook(new File("List.xls")); 
			WritableWorkbook book= Workbook.createWorkbook(new File("List.xls"),wb); 
			WritableSheet sheet=book.getSheet(0);            
            //写入内容
            int currentrows = sheet.getRows() ; 
            System.out.println(currentrows);
            sheet.addCell(new Label(0,currentrows,Integer.toString(nodenum)));
            sheet.addCell(new Label(1,currentrows,Long.toString(time)));
            sheet.addCell(new Label(2,currentrows,Integer.toString(nodenump)));
            sheet.addCell(new Label(3,currentrows,Long.toString(timep)));
            //写入数据
            book.write(); 
            //关闭文件
            book.close(); 
		} catch (IOException | WriteException | BiffException e) {
			e.printStackTrace();
		}
		
	}
	public WriteXLS(int score1, int score2, int score3, int score4,int score5,int score6,int score7){
		try {
			Workbook wb = Workbook.getWorkbook(new File("List.xls")); 
			WritableWorkbook book= Workbook.createWorkbook(new File("List.xls"),wb); 
			WritableSheet sheet=book.getSheet(0);            
            //写入内容
            int currentrows = sheet.getRows() ; 
            System.out.println(currentrows);
            sheet.addCell(new Label(0,currentrows,Integer.toString(score1)));
            sheet.addCell(new Label(1,currentrows,Long.toString(score2)));
            sheet.addCell(new Label(2,currentrows,Integer.toString(score3)));
            sheet.addCell(new Label(3,currentrows,Long.toString(score4)));
            sheet.addCell(new Label(4,currentrows,Long.toString(score5)));
            sheet.addCell(new Label(5,currentrows,Long.toString(score6)));
            sheet.addCell(new Label(6,currentrows,Long.toString(score7)));
            //写入数据
            book.write(); 
            //关闭文件
            book.close(); 
		} catch (IOException | WriteException | BiffException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
