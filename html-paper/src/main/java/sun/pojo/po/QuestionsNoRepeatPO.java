package sun.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 试题实体信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionsNoRepeatPO implements Serializable {
    private static final long serialVersionUID = -1L;

    @Pattern(regexp = "[\\da-z]{32}", message = "试题id应为32位随机字符串")
    private String rowKey;

    /** 资源名称 */
    @NotEmpty
    private String resourceName;

    /** 资源类型 */
    @NotEmpty
    private String resourceType;

    /** 文档名 */
    @NotEmpty
    private String docName;

    /** 导入日志表 rowkey */
    private String pathId;

    /** 阶段，如：一、选择题 */
    private String stage;

    /** 一道题的内容 */
    private String content;

    /** 题号 */
    private String qseq;

    /** @see cn.xxt.hadoop.common.enums.QuestTypeEnum 试题类型 */
    @Size(max = 3)
    private String qType;

    /**  */
    private String point;

    /**  */
    private String topic;

    /**  */
    private String model;

    /** 生成小word试题路径 todo 确认不再用的话可以去掉 */
    private String qPath;

    /** 对应答案 */
    private String answer;

    /** 对应解析 */
    private String analysis;

    /** 试题对应分数 todo 为了使用方便, 题表当中冗余分数字段 */
    private Double score;
}
