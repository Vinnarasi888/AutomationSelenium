package utils;

import org.apache.poi.ss.usermodel.*;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelUtil {

    private static final String FILE_PATH = "src/test/resources/AmazonEcomTestData.xlsx";

    // Cache structure: Map<SheetName, Map<TestCaseID, Map<ColumnHeader, CellValue>>>
    private static Map<String, Map<String, Map<String, String>>> excelCache = new HashMap<>();

    /**
     * Direct Fetcher: Retrieves a single cell value by SheetName, TestCaseID, and Column Header.
     * 
     * @param sheetName    Name of the Excel sheet
     * @param testCaseId   TestCaseID value (e.g., "TC_001")
     * @param columnHeader Column header name (e.g., "Username")
     * @return Cell value as String
     */
    
    public static String getCellData(String sheetName, String testCaseId, String columnHeader) {
        // Load and cache sheet data if not already cached
        if (!excelCache.containsKey(sheetName)) {
            loadSheetData(sheetName);
        }

        Map<String, Map<String, String>> sheetData = excelCache.get(sheetName);

        // 1. Check if TestCaseID exists and is marked active (Y/YES)
        if (!sheetData.containsKey(testCaseId)) {
            throw new SkipException("Skipping execution: Test Case ID '" + testCaseId 
                    + "' in sheet '" + sheetName + "' has ExecuteFlag set to 'N/No' or does not exist.");
        }

        Map<String, String> rowData = sheetData.get(testCaseId);

        // 2. Check if requested Column Header exists
        if (!rowData.containsKey(columnHeader)) {
            throw new IllegalArgumentException("Column Header '" + columnHeader 
                    + "' not found in sheet: " + sheetName);
        }

        return rowData.get(columnHeader);
    }

    /**
     * Loads specified sheet into memory cache. 
     * ONLY keeps rows where ExecuteFlag == 'Y' or 'YES'.
     */
    private static synchronized void loadSheetData(String sheetName) {
        if (excelCache.containsKey(sheetName)) {
            return; // Double-check lock to avoid duplicate reads
        }

        Map<String, Map<String, String>> activeSheetData = new LinkedHashMap<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' does not exist in file: " + FILE_PATH);
            }

            DataFormatter formatter = new DataFormatter();
            Row headerRow = sheet.getRow(0);

            if (headerRow == null) {
                throw new RuntimeException("Header row is missing in sheet: " + sheetName);
            }

            // 1. Locate TestCaseID and ExecuteFlag column indexes dynamically
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
                throw new RuntimeException("Missing required headers ('TestcaseID' or 'ExecuteFlag') in sheet: " + sheetName);
            }

            // 2. Parse data rows and store only rows where ExecuteFlag == Y/YES
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row currentRow = sheet.getRow(rowNum);
                if (currentRow == null) continue;

                String testCaseId = formatter.formatCellValue(currentRow.getCell(testCaseIdColIndex)).trim();
                String flag = formatter.formatCellValue(currentRow.getCell(executeFlagColIndex)).trim();

                // Store row only if flag is Y or YES
                if ("Y".equalsIgnoreCase(flag) || "YES".equalsIgnoreCase(flag)) {
                    Map<String, String> rowDataMap = new LinkedHashMap<>();

                    for (int colNum = 0; colNum < headerRow.getLastCellNum(); colNum++) {
                        String headerKey = formatter.formatCellValue(headerRow.getCell(colNum)).trim();
                        String cellValue = formatter.formatCellValue(currentRow.getCell(colNum)).trim();
                        rowDataMap.put(headerKey, cellValue);
                    }
                    activeSheetData.put(testCaseId, rowDataMap);
                }
            }

            excelCache.put(sheetName, activeSheetData);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file at: " + FILE_PATH, e);
        }
    }
    
    /**
     * Optional helper: Clears cache between test suite executions if needed.
     */
    public static void clearCache() {
        excelCache.clear();
    }
}