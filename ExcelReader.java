import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream fis = new FileInputStream(new File("C:/Users/ckr/Desktop/workbook.xlsx"));
		
		// create a workbook instance that refers to .xls file 
		
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		
		// create a sheet object to retrieve the sheet 
		XSSFSheet Sheet = wb.getSheetAt(0);
		
		//For Evaluating The Cell Type
		FormulaEvaluator forlulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
		
		for(Row row : Sheet) {
			for(Cell cell : row){
				switch(forlulaEvaluator.evaluateInCell(cell).getCellType())
				{
				//if cell is a numeric value 
				case Cell.CELL_TYPE_NUMERIC: 
					System.out.print(cell.getNumericCellValue()+"\t\t");
					break;
					//if cell is a string value 
				case Cell.CELL_TYPE_STRING: 
					System.out.print(cell.getStringCellValue()+"\t\t");
					break;
					
				}
			}
			System.out.println();		}

			}

	}

 