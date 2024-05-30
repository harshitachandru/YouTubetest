package demo.utils;

import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    @DataProvider(name = "excelData")
    public static Object[][] excelData() {
        String fileLocation = System.getProperty("user.dir")+"/src/test/resources/data.xlsx";
        System.out.println("Fetching excel file from "+fileLocation);
        return ExcelReaderUtil.readExcelData(fileLocation);
    }
    public static void main(String args[]){
        excelData();
    }
}