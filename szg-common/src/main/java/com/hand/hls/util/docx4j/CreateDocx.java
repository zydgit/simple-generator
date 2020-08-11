package com.hand.hls.util.docx4j;

import com.hand.hls.exception.BadRequestException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Map;

@Component
public class CreateDocx {

    @Autowired
    TextBookmarkReplaceWithContents textBookmarkReplaceWithContents;

    public void create(String templateWordUrl, List<String> bmList, Map<String, String> map) {
        try {
            File file = new File(templateWordUrl);
            if (file.exists()) {
                //用inputStram输入流读取本地的docx文件
                InputStream inStream = new FileInputStream(file);
//
//                //定义复制模板的filepath,使用uuid拼接于文件末尾，避免备份文件重名覆盖
//                String copyPath = outTemplateWordUrl;
//                this.copyModel(copyPath, inStream);
//                //用输入流读取复制后的模板
//                InputStream modelIs = new FileInputStream(copyPath);
//                File file1 = new File(copyPath);
                WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(inStream);
                MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
                Document wmlDocumentEl = documentPart.getContents();
                //拿到docx文件中的body
                Body body = wmlDocumentEl.getBody();

                textBookmarkReplaceWithContents.replaceBookmarkContents(body.getContent(), bmList, map);
                wordMLPackage.save(file);
            }else {
                throw new BadRequestException("模板文件不存在!!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void copyModel(String filePath, InputStream is) throws IOException {

        FileOutputStream fos = new FileOutputStream(filePath); //复制出一个模板
        int readData;
        byte[] b = new byte[1024];

        while ((readData = is.read(b)) != -1) {
            fos.write(b, 0, readData);
        }

        fos.flush();
        is.close();
        fos.close();
    }
}
