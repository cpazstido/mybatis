package com.cf.utils;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReadWriteExcel {
    private Logger logger = Logger.getLogger(ReadWriteExcel.class);

    public static void main(String[] args) {
        ReadWriteExcel readExcel = new ReadWriteExcel();
//        readExcel.readExcel("D:\\大枣订单201804121350.xls");
        readExcel.writeExcel();
    }

    public void readExcel(String path) {
        File file = new File(path);
        String fileName = file.getName();
        int iIndex = fileName.lastIndexOf(".");
        String ext = (iIndex < 0) ? "" : fileName.substring(iIndex + 1).toLowerCase();
        if (!"xls,xlsx".contains(ext) || "".contains(ext)) {
            logger.error("文件类型不是EXCEL！");
            return;
        }

        try {
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
            HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
            HSSFSheet sheet = workbook.getSheetAt(0);
            for (int k = 0; k <= sheet.getLastRowNum(); k++) {
                HSSFRow row = sheet.getRow(k);
                StringBuffer sb = new StringBuffer();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    HSSFCell cell = row.getCell(j);
                    if (cell == null)
                        break;
                    sb.append(cell);
                    sb.append("\t");
                }
                System.out.println(sb);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeExcel() {
        try {
            //定义一个新的工作簿
            Workbook wb = new HSSFWorkbook();
            //创建sheet1
            Sheet sheet = wb.createSheet("asdlfkj");
            //创建sheet2
            wb.createSheet("sheet2");
            //创建行
            Row row = sheet.createRow(0);
            //创建单元格
            row.createCell(0).setCellValue("省份证号");
            row.createCell(1).setCellValue("年龄");
            row.createCell(2).setCellValue("性别");
            row.createCell(3).setCellValue("身高");
            //创建一个输入流
            FileOutputStream fileOutputStream = new FileOutputStream("d:\\tt.xls");
            //写入
            wb.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
