package com.hand.hls.util.docx4j;

import org.docx4j.TraversalUtil;
import org.docx4j.finders.RangeFinder;
import org.docx4j.wml.CTBookmark;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * 书签替换文本插件
 */
@Component
public class TextBookmarkReplaceWithContents {

    /**
     * 把docx文件中的body的上下文和书签值（data中key为书签名，value为书签的值）传入方法中进行替换
     *
     * @param paragraphs (正文中的段落)
     * @param data
     */
    public void replaceBookmarkContents(List<Object> paragraphs, List<String> bmList, Map<String, String> map) {
        //当段落为空时,直接返回
        if (paragraphs == null || paragraphs.size() < 1) {
            return;
        }
        // 提取书签并创建书签的游标
        //CTBookmark表示一个书签标签的起点，CTMarkupRange表示一个书签标签的终点
        RangeFinder rt = new RangeFinder("CTBookmark", "CTMarkupRange");
        new TraversalUtil(paragraphs, rt);
        // 遍历书签
        for (CTBookmark bm : rt.getStarts()) {
            // 先判断书签的名字是否为空
            if (bm.getName() == null) {
                continue;
            }

            // 替换文本内容
            try {
                for (String bmName : bmList) {
                    if (bm.getName().equals(bmName)) {
                        Tool.replaceText(bm, map.get(bm.getName()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

