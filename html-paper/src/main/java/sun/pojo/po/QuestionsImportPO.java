package sun.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class  QuestionsImportPO implements Serializable {
    private static final long serialVersionUID = -1L;

    @Pattern(regexp = "[\\da-z]{32}", message = "试卷id格式为32位随机字符串")
    private String rowKey;

    /** 文件导入路径 */
    private String path;

    /** 试题数量 */
    @Size(max = 100, min = 1)
    private String questNum;

    /** questSeq + "-" + rowKey + "-" + questScore + "," */
    private String quests;

    /** @see cn.xxt.quest.imports.pojo.enums.ResourceTypeEnum 资源类型*/
    @NotEmpty
    private String resourceType;

    /** 资源子类型 */
    private String resourceSubType;

    /** 资源名称（word内大标题） */
    @NotEmpty
    private String resourceName;

    /** 年级 */
    private String grade;

    /** 年份 */
    private String year;

    /** 学期 */
    private String term;

    /** 教材版本 */
    private String version;

    /** 试卷类型（Path2TypeUtil.PERIOD） */
    private String period;

    /** 省份 */
    private String province;

    /** 地区 */
    private String area;

    /** 学校 */
    private String org;

    /** @see cn.xxt.quest.imports.pojo.enums.OrgStageEnum */
    private String orgStage;
}
