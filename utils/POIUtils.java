package com.hyqfx.util.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * POI excel表工具类
 * @auth len
 * @createTime 2019/8/30
 */
public class POIUtils {

    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet();
    Row headRow = null;
    String excelName = "ExcelData" + System.currentTimeMillis();


    /**
     * 设置表头
     * @param headers 表头数据
     * @return
     */
    public POIUtils createHeader(String[] headers){
        // 表头行
        Row headRow = sheet.createRow(0);
        headRow.setHeight((short)(3*100));
        // 设置表头行样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFFont font = wb.createFont();
        font.setFontHeight((short)(2.5*100));
        font.setFontName("黑体");
        style.setFont(font);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, (short)(50*100));
        }

        sheet.setAutobreaks(true);

        return this;
    }

    /**
     * 填充数据
     * @param data 数据体
     */
    public POIUtils fillData(List<String[]> data){
        int rowIndex = 1;// 行下标
        if(data != null){
            HSSFRow row = null;
            for(String[] rowData : data){
                // 2.1、创建一行
                row = sheet.createRow(rowIndex);
                row.setHeight((short)(3*100));
                rowIndex++;
                // 2.2、在当前行中创建单元格，用于存放数据
                for (int j = 0; j < rowData.length; j++) {
                    row.createCell(j).setCellValue(rowData[j]==null?"":rowData[j]);
                }

            }
        }

        return this;
    }

    /**
     * 设置文件名
     * @param excelName
     * @param excelName excel文件名
     */
    public POIUtils setExcelName(String excelName){
        this.excelName = excelName;
        return this;
    }


    /**
     * 生成exce表
     * 前端自动弹出下载窗口
     * @param response HttpServletResponse接口响应
     */
    public void getExcelAndDownload(HttpServletResponse response){

        OutputStream output = null;
        try {
            output = response.getOutputStream();
            response.reset();
            response.setContentType("application/msexcel");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(excelName, "UTF-8") + ".xls");
            wb.write(output);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(output != null){
                try {
                    output.flush();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
