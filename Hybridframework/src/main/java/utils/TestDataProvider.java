package utils;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Map;

public class TestDataProvider {

   // private static final String EXCEL_PATH = "src/test/resources/AmazonEcomTestData.xlsx";

    @DataProvider(name = "excelData")
    public static Object[][] getExcelData(Method method) {
        // Automatically matches test method name to Excel sheet name
        String sheetName = method.getName(); 

        Map<String, Map<String, String>> activeTestData = ExcelUtil.loadActiveTestData(sheetName);

        Object[][] data = new Object[activeTestData.size()][2];
        int index = 0;

        for (Map.Entry<String, Map<String, String>> entry : activeTestData.entrySet()) {
            data[index][0] = entry.getKey();   // TestCaseID (String)
            data[index][1] = entry.getValue(); // Map of Header -> Value
            index++;
        }

        return data;
    }
}