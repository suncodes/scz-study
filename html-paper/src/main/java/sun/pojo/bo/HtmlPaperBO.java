package sun.pojo.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * 属性名不可随意更改，部分需要和入库的PO字段对应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HtmlPaperBO {

    /** 文件路径 */
    private String path;

    /** 考试（资源）名称 */
    @JSONField(name = "examName")
    private String resourceName;

    /** 考试时间 */
    @JSONField(name = "examCreateTime")
    private String examData;

    /** 图片 */
    private Map<String, byte[]> imgMap;

    private List<HtmlQuestBO> htmlQuestBOList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HtmlQuestBO {

        private String rowKey;

        /** 对应整道题所有内容 */
        @JSONField(name = "analysis")
        private String allContent;

        /** 题号 */
        @JSONField(name = "disTitleNumber")
        private String qseq;

        /** 试题内容 */
        @JSONField(name = "contentHtml")
        private String content;

        /** 答案 */
        @JSONField(name = "answerHtml")
        private String answer;

        /** 解析 */
        @JSONField(name = "analysisHtml")
        private String analysis;

        /** 分值 */
        @JSONField(name = "standardScore")
        private Double score;
    }
}
