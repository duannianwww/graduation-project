package com.gg;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Excel�ĵ�������
 */
public class ExcelUtil {

    /**
     * ������д�뵽Excel�ļ�
     * @param fileName �Զ����ɵ�Excel�ļ���ȫ·���ļ�����
     * @param users Ҫд�뵽Excel�ļ��е�����
     */
//    public static void writeExcel(String fileName, List<User> users) throws IOException {
//        Workbook workbook = null;
//        Sheet sheet = null;
//        Row row = null;
//        Cell cell = null;
//
//        //����Excel�ļ�
//        File excelFile = new File(fileName.trim());
//
//        //����Excel������
//        if (excelFile.getName().endsWith("xlsx")) {
//            workbook = new XSSFWorkbook();
//        } else {
//            workbook = new HSSFWorkbook();
//        }
//
//        //����Excel��
//        sheet = workbook.createSheet();
//
//        //�����п����Ϊ256��������
//        sheet.setColumnWidth(1, 5120);
//        sheet.setColumnWidth(2, 3840);
//        sheet.setColumnWidth(3, 2560);
//        sheet.setColumnWidth(4, 2560);
//        sheet.setColumnWidth(5, 5120);
//
//        //����Ĭ���и�(Ĭ��Ϊ300)
//        sheet.setDefaultRowHeight((short) 512);
//
//        //���úϲ���Ԫ��
//        CellRangeAddress titleCellAddresses = new CellRangeAddress(1,2,1,5);
//        sheet.addMergedRegion(titleCellAddresses);
//
//        //����������
//        row = sheet.createRow(1);
//        cell = row.createCell(1, CellType.STRING);
//        cell.setCellStyle(getTitleCellStyle(workbook));
//        cell.setCellValue("User��Ϣ���");
//
//        //���úϲ���Ԫ��ı߿������Ҫ���ڴ���������֮��
//        setRegionBorderStyle(BorderStyle.THIN, titleCellAddresses, sheet);
//
//        //������ͷ��
//        row = sheet.createRow(3);
//        cell = row.createCell(1, CellType.STRING);
//        cell.setCellStyle(getHeaderCellStyle(workbook));
//        cell.setCellValue("ID");
//        cell = row.createCell(2, CellType.STRING);
//        cell.setCellStyle(getHeaderCellStyle(workbook));
//        cell.setCellValue("����");
//        cell = row.createCell(3, CellType.STRING);
//        cell.setCellStyle(getHeaderCellStyle(workbook));
//        cell.setCellValue("�Ա�");
//        cell = row.createCell(4, CellType.STRING);
//        cell.setCellStyle(getHeaderCellStyle(workbook));
//        cell.setCellValue("����");
//        cell = row.createCell(5, CellType.STRING);
//        cell.setCellStyle(getHeaderCellStyle(workbook));
//        cell.setCellValue("����");
//
//        //����������
//        for(int i = 0; i < users.size(); i++) {
//            row = sheet.createRow(i + 4);
//            cell = row.createCell(1, CellType.NUMERIC);
//            cell.setCellStyle(getBodyCellStyle(workbook));
//            cell.setCellValue(users.get(i).getId());
//            cell = row.createCell(2, CellType.STRING);
//            cell.setCellStyle(getBodyCellStyle(workbook));
//            cell.setCellValue(users.get(i).getName());
//            cell = row.createCell(3, CellType.BOOLEAN);
//            cell.setCellStyle(getBodyCellStyle(workbook));
//            cell.setCellValue(users.get(i).getSex());
//            cell = row.createCell(4, CellType.NUMERIC);
//            cell.setCellStyle(getBodyCellStyle(workbook));
//            cell.setCellValue(users.get(i).getAge());
//            cell = row.createCell(5, CellType.STRING);
//            cell.setCellStyle(getBodyCellStyle(workbook));
//            cell.setCellValue(users.get(i).getBirthday());
//        }
//
//        //��Excel������д�뵽Excel�ļ�
//        FileOutputStream os = new FileOutputStream(excelFile);
//        workbook.write(os);
//        os.flush();
//        os.close();
//    }

    /**
     * ��Excel�ļ���ȡ����
     * @param fileName Ҫ��ȡ��Excel�ļ���ȫ·���ļ�����
     * @return ��Excel�ļ�������������û�����
     */
    public List<shiti> readExcel(String fileName) throws IOException {
        Workbook workbook = null;
        Sheet sheet = null;
        Row row = null;

        //��ȡExcel�ļ�
        File excelFile = new File(fileName.trim());

        InputStream is = new FileInputStream(excelFile);

        //��ȡExcel������
        if (excelFile.getName().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(is);
        } else {
            workbook = new HSSFWorkbook(is);
        }
        if (workbook == null) {
            System.err.println("Excel文件有问题！请检查");
            return null;
        }

        //��ȡExcel��
        sheet = workbook.getSheetAt(0);//��1��ѡ����
        List<shiti> shitis = new ArrayList<shiti>();
        for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            //��ȡһ��
            row = sheet.getRow(rowNum);
            shiti shiti = new shiti();
            shiti.setQuestiontype("选择题");
            shiti.setQuestion(getStringValue(row.getCell(0)));
            if(shiti.getQuestion()==null)continue;
            shiti.setA(getStringValue(row.getCell(1)));
            shiti.setB(getStringValue(row.getCell(2)));
            shiti.setC(getStringValue(row.getCell(3)));
            shiti.setD(getStringValue(row.getCell(4)));
            shiti.setE(getStringValue(row.getCell(5)));
            shiti.setF(getStringValue(row.getCell(6)));
            shiti.setG(getStringValue(row.getCell(7)));
            shiti.setH(getStringValue(row.getCell(8)));
            shiti.setAnswer(getStringValue(row.getCell(9)));
            shiti.setQuestionsnote(getStringValue(row.getCell(10)));
            shitis.add(shiti);
        }
        sheet = workbook.getSheetAt(1);//��2����ѡ��
        for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            //��ȡһ��
            row = sheet.getRow(rowNum);
            shiti shiti = new shiti();
            shiti.setQuestiontype("多选题");
            shiti.setQuestion(getStringValue(row.getCell(0)));
            if(shiti.getQuestion()==null)continue;
            shiti.setA(getStringValue(row.getCell(1)));
            shiti.setB(getStringValue(row.getCell(2)));
            shiti.setC(getStringValue(row.getCell(3)));
            shiti.setD(getStringValue(row.getCell(4)));
            shiti.setE(getStringValue(row.getCell(5)));
            shiti.setF(getStringValue(row.getCell(6)));
            shiti.setG(getStringValue(row.getCell(7)));
            shiti.setH(getStringValue(row.getCell(8)));
            shiti.setAnswer(getStringValue(row.getCell(9)));
            shiti.setQuestionsnote(getStringValue(row.getCell(10)));
            shitis.add(shiti);
        }
        sheet = workbook.getSheetAt(2);//��3���ж���
        for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            //��ȡһ��
            row = sheet.getRow(rowNum);
            shiti shiti = new shiti();
            shiti.setQuestiontype("判断题");
            shiti.setQuestion(getStringValue(row.getCell(0)));
            if(shiti.getQuestion()==null)continue;
            shiti.setAnswer(getStringValue(row.getCell(1)));
            shiti.setQuestionsnote(getStringValue(row.getCell(2)));
            shitis.add(shiti);
        }
        sheet = workbook.getSheetAt(3);//��4�������
        for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            //��ȡһ��
            row = sheet.getRow(rowNum);
            shiti shiti = new shiti();
            shiti.setQuestiontype("填空题");
            shiti.setQuestion(getStringValue(row.getCell(0)));
            if(shiti.getQuestion()==null)continue;
            shiti.setAnswer(getStringValue(row.getCell(1)));
            shiti.setAnswer2(getStringValue(row.getCell(2)));
            shiti.setAnswer3(getStringValue(row.getCell(3)));
            shiti.setAnswer4(getStringValue(row.getCell(4)));
            shiti.setAnswer5(getStringValue(row.getCell(5)));
            shiti.setAnswer6(getStringValue(row.getCell(6)));
            shiti.setQuestionsnote(getStringValue(row.getCell(7)));
            shitis.add(shiti);
        }
        sheet = workbook.getSheetAt(4);//��5���ʴ���
        for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            //��ȡһ��
            row = sheet.getRow(rowNum);
            shiti shiti = new shiti();
            shiti.setQuestiontype("问答题");
            shiti.setQuestion(getStringValue(row.getCell(0)));
            if(shiti.getQuestion()==null)continue;
            shiti.setAnswer(getStringValue(row.getCell(1)));
            shiti.setQuestionsnote(getStringValue(row.getCell(2)));
            shitis.add(shiti);
        }
        is.close();
        return shitis;
    }

    /**
     * ���úϲ���Ԫ��ı߿�
     * @param style Ҫ���õı߿����ʽ
     * @param cellAddresses Ҫ���õĺϲ��ĵ�Ԫ��
     * @param sheet Ҫ���õĺϲ��ĵ�Ԫ�����ڵı�
     */
    private static void setRegionBorderStyle(BorderStyle style, CellRangeAddress cellAddresses, Sheet sheet) {
        RegionUtil.setBorderTop(style, cellAddresses, sheet);
        RegionUtil.setBorderBottom(style, cellAddresses, sheet);
        RegionUtil.setBorderLeft(style, cellAddresses, sheet);
        RegionUtil.setBorderRight(style, cellAddresses, sheet);
    }

    /**
     * ������ͨ��Ԫ��ı߿�
     * @param style Ҫ���õı߿����ʽ
     * @param cellStyle ��Ԫ����ʽ����
     */
    private static void setCellBorderStyle(BorderStyle style, CellStyle cellStyle) {
        cellStyle.setBorderTop(style);
        cellStyle.setBorderBottom(style);
        cellStyle.setBorderLeft(style);
        cellStyle.setBorderRight(style);
    }

    /**
     * ���ñ��ⵥԪ����ʽ
     * @param workbook ����������
     * @return ��Ԫ����ʽ����
     */
    private static CellStyle getTitleCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();

        //��������
        Font font = workbook.createFont();
        font.setFontName("����");
        font.setFontHeightInPoints((short) 24);
        font.setColor((short) 10);
        cellStyle.setFont(font);

        //�������־�����ʾ
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return cellStyle;
    }


    /**
     * ���ñ�ͷ��Ԫ����ʽ
     * @param workbook ����������
     * @return ��Ԫ����ʽ����
     */
    private static CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();

        //��������
        Font font = workbook.createFont();
        font.setFontName("����");
        font.setFontHeightInPoints((short) 20);
        font.setBold(true);
        cellStyle.setFont(font);

        //�������־�����ʾ
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //���õ�Ԫ��ı߿�
        setCellBorderStyle(BorderStyle.THIN, cellStyle);

        return cellStyle;
    }

    /**
     * ���ñ��嵥Ԫ����ʽ
     * @param workbook ����������
     * @return ��Ԫ����ʽ����
     */
    private static CellStyle getBodyCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();

        //��������
        Font font = workbook.createFont();
        font.setFontName("����");
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);

        //�������־�����ʾ
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //���õ�Ԫ��ı߿�
        setCellBorderStyle(BorderStyle.THIN, cellStyle);

        return cellStyle;
    }

    /**
     * ��ȡ��Ԫ���ֵ���ַ���
     * @param cell ��Ԫ�����
     * @return cell��Ԫ���ֵ���ַ���
     */
    private static String getStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                double value = cell.getNumericCellValue();
                return String.valueOf(Math.round(value));
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null;
        }
    }

}