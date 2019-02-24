/*
package com.hsy.platform.common;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;

public class ExcelView extends AbstractXlsView {


    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Font  font=    workbook.createFont();
        font.setFontHeightInPoints((short)12);            //设置字体的大小
        font.setFontName("微软雅黑");                        //设置字体的样式，如：宋体、微软雅黑等
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + "导出文件");
        OutputStream ouputStream = httpServletResponse.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }
}
*/
