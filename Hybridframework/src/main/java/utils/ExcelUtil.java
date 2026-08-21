package utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelUtil {

    /**
     * Loads sheet data into a Map structure: Map<TestCaseID, Map<ColumnHeader, CellValue>>
     * ONLY includes rows where ExecuteFlag == 'Y' or 'YES'.
     */
    public static Map<String, Map<String, String>> loadActiveTestData(String sheetName) {
        Map<String, Map<String, String>> testDataMap = new LinkedHashMap<>();
        String filePath = "src/test/resources/AmazonEcomTestData.xlsx";
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' does not exist.");
            }

            DataFormatter formatter = new DataFormatter();
            Row headerRow = sheet.getRow(0);

            // 1. Identify key column indexes (TestCaseID and ExecuteFlag)
            int testCaseIdColIndex = -1;
            int executeFlagColIndex = -1;

            for (int col = 0; col < headerRow.getLastCellNum(); col++) {
                String headerName = formatter.formatCellValue(headerRow.getCell(col)).trim();
                if (headerName.equalsIgnoreCase("TestcaseID") || headerName.equalsIgnoreCase("TCID")) {
                    testCaseIdColIndex = col;
                }
                if (headerName.equalsIgnoreCase("ExecuteFlag") || headerName.equalsIgnoreCase("Execute")) {
                    executeFlagColIndex = col;
                }
            }

            if (testCaseIdColIndex == -1 || executeFlagColIndex == -1) {
                throw new RuntimeException("Missing required headers ('TestaseID' or 'ExecuteFlag') in sheet: " + sheetName);
            }

            // 2. Read rows and store only rows where ExecuteFlag == 'Y'
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row currentRow = sheet.getRow(rowNum);
                if (currentRow == null) continue;

                String testCaseId = formatter.formatCellValue(currentRow.getCell(testCaseIdColIndex)).trim();
                String flag = formatter.formatCellValue(currentRow.getCell(executeFlagColIndex)).trim();

                // Apply ExecuteFlag condition
                if (flag.equalsIgnoreCase("Y") || flag.equalsIgnoreCase("YES") || flag.equalsIgnoreCase("Yes") ||flag.equalsIgnoreCase("yes") ||flag.equalsIgnoreCase("y")) {
                    Map<String, String> rowDataMap = new HashMap<>();

                    for (int colNum = 0; colNum < headerRow.getLastCellNum(); colNum++) {
                        String headerKey = formatter.formatCellValue(headerRow.getCell(colNum)).trim();
                        String cellValue = formatter.formatCellValue(currentRow.getCell(colNum)).trim();
                        rowDataMap.put(headerKey, cellValue);
                    }
                    testDataMap.put(testCaseId, rowDataMap);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testDataMap;
    }

    /**
     * Direct Fetcher: Retrieves a single cell value by TestCaseID and Column Header.
     */
    public static String getCellData(String sheetName, String testCaseId, String columnHeader) {
        Map<String, Map<String, String>> activeData = loadActiveTestData(sheetName);

        if (!activeData.containsKey(testCaseId)) {
            throw new org.testng.SkipException("Skipping execution: Test Case ID '" 
                + testCaseId + "' has ExecuteFlag set to 'N' or does not exist.");
        }

        Map<String, String> rowData = activeData.get(testCaseId);
        if (!rowData.containsKey(columnHeader)) {
            throw new IllegalArgumentException("Column Header '" + columnHeader + "' not found in sheet.");
        }

        return rowData.get(columnHeader);
    }
}