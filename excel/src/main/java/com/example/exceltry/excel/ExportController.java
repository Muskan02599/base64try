package com.example.exceltry.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/entity")
public class ExportController {
    @Autowired
    private EntityService yourEntityService;

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> data = yourEntityService.getAllEntities();

        // Create Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Entity Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        int colNum = 0;
        for (String columnName : data.get(0).keySet()) {
            Cell cell = headerRow.createCell(colNum++);
            cell.setCellValue(columnName);
        }

        // Create data rows
        int rowNum = 1;
        for (Map<String, Object> rowMap : data) {
            Row row = sheet.createRow(rowNum++);
            colNum = 0;
            for (Object value : rowMap.values()) {
                Cell cell = row.createCell(colNum++);
                if (value != null) {
                    cell.setCellValue(value.toString());
                } else {
                    cell.setCellValue("");
                }
            }
        }

        // Set the response headers
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=entity_data.xlsx");

        // Write to response stream
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @PostMapping("/insert")
    public void insertEntity(@RequestBody EntityClass entity) {
        // Perform validation and other business logic as needed
        yourEntityService.insertEntity(entity);
    }
}
