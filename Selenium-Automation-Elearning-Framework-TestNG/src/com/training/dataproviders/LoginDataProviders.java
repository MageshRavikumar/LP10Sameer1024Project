package com.training.dataproviders;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.training.bean.LoginBean;
import com.training.bean.UniformBean;
import com.training.dao.ELearningDAO;
import com.training.dao.UniformDAO;
import com.training.readexcel.ApachePOIExcelRead;
import com.training.readexcel.ReadExcel;

public class LoginDataProviders {

	@DataProvider(name = "db-inputs")
	public Object [][] getDBData() {

		List<UniformBean> list = new UniformDAO().getModel(); 
		
		Object[][] result = new Object[list.size()][]; 
		int count = 0; 
		for(UniformBean temp : list){
			Object[]  obj = new Object[1]; 
			obj[0] = temp.getModel(); 
			
			result[count ++] = obj; 
		}
				
		return result;
	}
	
	@DataProvider(name = "excel-inputs")
	public Object[][] getExcelData(){
		String fileName ="C:\\Users\\Sameer\\Downloads\\Reskill\\TestData_UNFTD_012.xlsx"; 
		return new ApachePOIExcelRead().getExcelContent(fileName); 
	}
	
	@DataProvider(name = "xls-inputs")
	public Object[][] getXLSData(){
		// ensure you will have the title as first line in the file 
		return new ReadExcel().getExcelData("C:/Users/Naveen/Desktop/Testing.xls", "Sheet1"); 
	}
}
