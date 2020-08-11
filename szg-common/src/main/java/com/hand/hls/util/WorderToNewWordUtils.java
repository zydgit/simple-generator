package com.hand.hls.util;

import java.io.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 通过word模板生成新的word工具类
 *
 * @author wenhaonan
 */
public class WorderToNewWordUtils {
    // 导出成功标志
    private static boolean EXPORT_SUCCESS = true;

    /**
     * 根据模板生成新word文档
     * 判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
     *
     * @param inputUrl 模板存放地址
     * @param textMap  需要替换的信息集合
     * @param tableMap 需要插入的表格信息集合
     * @return 成功返回true, 失败返回false
     */
    public static boolean changWord(HttpServletResponse res, String inputUrl,
                                    Map<String, String> textMap, Map<Integer, List<String[]>> tableMap) {
        try {
            // 解析成xwpf对象
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(inputUrl);
            if(inputStream == null) {
               return false;
            }
            XWPFDocument document = new XWPFDocument(inputStream);
            // 替换书签
            WorderToNewWordUtils.changeText(document, textMap);
            // 替换表格
            WorderToNewWordUtils.changeTable(document, textMap, tableMap);

            // 返回文件
            WorderToNewWordUtils.downFile(res, document);
        } catch (Exception e) {
            e.printStackTrace();
            EXPORT_SUCCESS = false;
        }

        return EXPORT_SUCCESS;
    }

    /**
     * 替换段落文本
     *
     * @param document docx解析对象
     * @param textMap  需要替换的信息集合
     */
    private static void changeText(XWPFDocument document, Map<String, String> textMap) {
        //获取段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        for (XWPFParagraph paragraph : paragraphs) {
            //判断此段落时候需要进行替换
            String text = paragraph.getText();
            if (checkText(text)) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    //替换模板原来位置
                    run.setText(changeValue(run.toString(), textMap), 0);
                }
            }
        }

    }

    /**
     * 替换表格对象方法
     *
     * @param document docx解析对象
     * @param textMap  需要替换的信息集合
     * @param tableMap 需要插入的表格信息集合
     */
    private static void changeTable(XWPFDocument document, Map<String, String> textMap,
                                    Map<Integer, List<String[]>> tableMap) {
        //获取表格对象集合
        List<XWPFTable> tables = document.getTables();
        for (int i = 0; i < tables.size(); i++) {
            //只处理行数大于等于2的表格，且要插入的数据不为空, 且不循环表头
            XWPFTable table = tables.get(i);
            if (table.getRows().size() > 1 && tableMap.get(i) != null && tableMap.get(i).size() != 0) {
                //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
                if (checkText(table.getText())) {
                    List<XWPFTableRow> rows = table.getRows();
                    //遍历表格,并替换模板
                    eachTable(rows, textMap);
                } else {
                    insertTable(table, tableMap.get(i));
                }
            }
        }
    }


    /**
     * 遍历表格
     *
     * @param rows    表格行对象
     * @param textMap 需要替换的信息集合
     */
    private static void eachTable(List<XWPFTableRow> rows, Map<String, String> textMap) {
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                //判断单元格是否需要替换
                if (checkText(cell.getText())) {
                    List<XWPFParagraph> paragraphs = cell.getParagraphs();
                    for (XWPFParagraph paragraph : paragraphs) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            run.setText(changeValue(run.toString(), textMap), 0);
                        }
                    }
                }
            }
        }
    }

    /**
     * 为表格插入数据，行数不够添加新行
     *
     * @param table     需要插入数据的表格
     * @param tableList 插入数据集合
     */
    private static void insertTable(XWPFTable table, List<String[]> tableList) {
        //创建行,根据需要插入的数据添加新行，不处理表头
        for (int i = 1; i < tableList.size(); i++) {
            table.createRow();
        }
        //遍历表格插入数据
        try {
            List<XWPFTableRow> rows = table.getRows();
            for (int i = 1; i < rows.size(); i++) {
                XWPFTableRow newRow = table.getRow(i);
                List<XWPFTableCell> cells = newRow.getTableCells();
                for (int j = 0; j < cells.size(); j++) {
                    XWPFTableCell cell = cells.get(j);
                    cell.setText(tableList.get(i - 1)[j]);
                    // 首行缩进
                    cell.getParagraphArray(0).setFirstLineIndent(0);
                    // 首行特殊缩进 !!! 就是这个搞我心态, 他非得默认缩进两个!
                    cell.getParagraphArray(0).setNumID(new BigInteger("0"));
                    // 垂直对齐方式
                    cell.getParagraphArray(0).setVerticalAlignment(TextAlignment.CENTER);
                    // 行间距
                    cell.getParagraphArray(0).setSpacingBetween(1, LineSpacingRule.AUTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            EXPORT_SUCCESS = false;
        }

    }


    /**
     * 判断文本中时候包含$
     *
     * @param text 文本
     * @return 包含返回true, 不包含返回false
     */
    private static boolean checkText(String text) {
        boolean check = false;
        if (text.contains("$")) {
            check = true;
        }
        return check;

    }

    /**
     * 匹配传入信息集合与模板
     *
     * @param value   模板需要替换的区域
     * @param textMap 传入信息集合
     * @return 模板需要替换区域信息集合对应值
     */
    private static String changeValue(String value, Map<String, String> textMap) {
        Set<Entry<String, String>> textSets = textMap.entrySet();
        for (Entry<String, String> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = "${" + textSet.getKey() + "}";
            if (value.contains(key)) {
                value = textSet.getValue();
            }
        }
        //模板未匹配到区域替换为空
        if (checkText(value)) {
            value = "";
        }
        return value;
    }

    /**
     * 导出文件流到response
     *
     * @param response 响应体
     * @param document wepf对象
     */
    private static void downFile(HttpServletResponse response, XWPFDocument document) {
        try {
            OutputStream outs = response.getOutputStream();
            //获取文件 输出IO流
            BufferedOutputStream bouts = new BufferedOutputStream(outs);
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            //设 置response内容的类型
            response.setHeader("Content-disposition", "attachment");
            document.write(bouts);
            bouts.flush();
            outs.close();
            bouts.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
