package init.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadTest {

	public static void main(String[] args) throws IOException {
		
				//Define path of excel sheet
		        String excelFilePath = "F:\\dataEquity.xlsx";
		        String date = null;
		        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		         
		        ArrayList<String> itemList = new ArrayList<String>();
		        
		        Workbook workbook = new XSSFWorkbook(inputStream);
		        Sheet firstSheet = workbook.getSheetAt(0);
		        Iterator<Row> iterator = firstSheet.iterator();
		        
		        System.out.println("Row count : "+firstSheet.getLastRowNum());
            	System.out.println("Column count : "+firstSheet.getRow(2).getLastCellNum());
		        
		        while (iterator.hasNext()) {
		            Row nextRow = iterator.next();
		            if(nextRow.getRowNum()!=0 && nextRow.getRowNum()==1){
		            	Iterator<Cell> cellIterator = nextRow.cellIterator();
			            while (cellIterator.hasNext()) {
			            	
			                Cell cell = cellIterator.next();
			                if(cell.getColumnIndex()==0){
			                	System.out.println("Date : "+cell.getDateCellValue());
				                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		                    	Date today = cell.getDateCellValue();
		                    	System.out.println("Date : "+df.format(today));
		                    	date = df.format(today);
		                    	date=date.replaceAll("/", "-");
			                }
			                
			                cell.setCellType(Cell.CELL_TYPE_STRING);
			                switch (cell.getCellType()) {
			                    case Cell.CELL_TYPE_STRING:
			                    	if(cell.getColumnIndex()==0)
			                    	{
			                    		System.out.println("Date : "+date);
			                    		itemList.add(date);
			                    	}
			                    	else{
			                    		System.out.println("String : "+cell.getStringCellValue());
				                        itemList.add(cell.getStringCellValue());
			                    	}
			                        
			                        break;
			                    case Cell.CELL_TYPE_BOOLEAN:
			                        System.out.print(cell.getBooleanCellValue());
			                        break;
			                    case Cell.CELL_TYPE_NUMERIC:
			                    	cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			                        System.out.println("Numeric : "+cell.getNumericCellValue());
			                        String valueInt = String.valueOf(cell.getNumericCellValue());
			                        itemList.add(valueInt);
			                }
			                System.out.println("");
			            }
			            //break;
		            }
		        }
		        workbook.close();
		        inputStream.close();
		        
		        // get specific value from arraylist
		        System.out.println(itemList.get(2));
		        
		        //Print the values
		        for(String arrList : itemList)
		        {
		        	System.out.println("Item : "+arrList);
		        }
	}
}
