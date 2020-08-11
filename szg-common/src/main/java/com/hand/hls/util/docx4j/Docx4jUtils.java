package com.hand.hls.util.docx4j;

import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.Parts;
import org.docx4j.wml.*;
import java.util.List;

/**
 * Created by FJM on 2017/4/17.
 */
public class Docx4jUtils {
    /**
     * @param partName <b>文档某部分xml的名字</b>
     *                 <p>
     *                 <p>
     *                 word文档一般有以下几种：<br/>
     *                 /docProps/app.xml<br/>
     *                 /docProps/core.xml<br/>
     *                 /word/document.xml: 文档主体内容<br/>
     *                 /word/header1.xml /word/header2.xml /word/header3.xml ： 文档页眉内容
     *                 <br/>
     *                 /word/footer1.xml /word/footer2.xml /word/footer3.xml ：文档页脚内容<br/>
     *                 /word/fontTable.xml<br/>
     *                 /word/settings.xml<br/>
     *                 /word/styles.xml<br/>
     *                 /word/endnotes.xml<br/>
     *                 /word/webSettings.xml<br/>
     *                 /word/theme/theme1.xml<br/>
     *                 </p>
     * @return partName有效且该part存在时返回指定part，否则返回null
     */

    public static Part getNamedPart(WordprocessingMLPackage workPackage, String partName) {
        Parts parts = workPackage.getParts();
        if (parts != null) {
            Part part = null;
            try {
                part = parts.get(new PartName(partName));
            } catch (InvalidFormatException e) {
                throw new RuntimeException(partName + " is not valid format.");
            }
            if (part != null) {
                return part;
            }
        }
        return null;
    }

    /**
     * 获取Part的内容部分
     *
     * @return 仅当Part为ContentAccessor子类时有效，否则返回null
     */
    public static List<Object> getPartContent(Part part) {
        if (part != null && part instanceof ContentAccessor) {
            return ((ContentAccessor) part).getContent();
        }
        return null;
    }

    /**
     * 创建R标签
     *
     * @param value      指定R标签中的value
     * @return
     */
    public static R makeRWithContent(String value) {
        R run = new R();
        Text t = new Text();
        run.getContent().add(t);
        t.setValue(value);
        return run;
    }
}
