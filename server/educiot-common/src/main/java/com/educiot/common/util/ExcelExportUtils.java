package com.educiot.common.util;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * excel导出工具类
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/3/20 17:42
 */
public class ExcelExportUtils {

    public static void write(HttpServletResponse response, Workbook workbook) {
        String fileName = UUID.randomUUID().toString() + ".xls";
        pwrite(response, workbook, fileName);
    }

    public static void write(HttpServletResponse response, Workbook workbook, String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            fileName = UUID.randomUUID().toString() + ".xls";
        }
        pwrite(response, workbook, fileName);
    }

    public static void write(HttpServletResponse response, List<List<String>> lists, String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            fileName = UUID.randomUUID().toString() + ".xls";
        }
        SXSSFWorkbook workbook = new SXSSFWorkbook(lists.size());
        SXSSFSheet sheet = workbook.createSheet(fileName.substring(0, fileName.indexOf(".xls")));
        Integer rowIndex = 0;
        Row row = null;
        Cell cell = null;
        for (List<String> rowData : lists) {
            Integer columnIndex = 0;
            row = sheet.createRow(rowIndex++);
            for (String columnVal : rowData) {
                cell = row.createCell(columnIndex++);
                cell.setCellValue(columnVal);
            }
        }
        pwrite(response, workbook, fileName);
    }

    public static void pwrite(HttpServletResponse response, Workbook workbook, String fileName) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8)
                    , "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fileName = UUID.randomUUID().toString() + ".xls";
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        }
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
