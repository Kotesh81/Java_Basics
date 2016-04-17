import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class WriteExcel {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public static void writeXLSXFile() throws IOException {
				
				String excelFileName = "C:/Users/ckr/Desktop/workbook1.xlsx";//name of excel file
		
				String sheetName = "Sheet1";//name of sheet
		
			XSSFWorkbook wb = new XSSFWorkbook();
				XSSFSheet sheet = wb.createSheet(sheetName) ;
		
				//iterating r number of rows
				for (int r=0;r < 5; r++ )
				{
					XSSFRow row = sheet.createRow(r);
		
					//iterating c number of columns
					for (int c=0;c < 5; c++ )
					{
						XSSFCell cell = row.createCell(c);
			
						cell.setCellValue("Cell "+r+" "+c);
					}
				}
		
				FileOutputStream fileOut = new FileOutputStream(excelFileName);
		
				//write this workbook to an Output stream.
				wb.write(fileOut);
				fileOut.flush();
				fileOut.close();
			}
		
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		writeXLSXFile();
		
	}

}
