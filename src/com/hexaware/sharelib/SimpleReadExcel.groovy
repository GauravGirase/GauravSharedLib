package com.hexaware.sharelib

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


public class SimpleReadExcel {

	public static void main(String[] args) throws EncryptedDocumentException, IOException {


		try {
			Workbook workbook = WorkbookFactory.
		
					create(new FileInputStream("./data/data.xlsx"));
		//	System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

			Sheet sheet = workbook.getSheetAt(0);
		
		//	System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				// Now let's iterate over the columns of the current row
				
				int len = row.getLastCellNum();

				for ( int i = 0; len > i ; i++) {
					
					System.out.print(row.getCell(i).toString());
					
					if(len-1 == i)
					{
						// print nothing
					}
					else
					{
						System.out.print(",");
					}
					
				}
				System.out.println();

			}

		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}
}
