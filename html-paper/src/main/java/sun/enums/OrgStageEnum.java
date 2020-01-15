package sun.enums;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author mxy
 * @date 2018/11/16
 * 学校考试子类型
 */
public enum  OrgStageEnum {
    //学校考试子类型
    KXCS(1, "开学测试卷"),
    M1E(2, "第一次月考"),
    ME(3, "期中考试"),
    M2E(4, "第二次月考"),
    LE(5, "期末考试"),
    YJ(6, "一检"),
    EJ(7, "二检"),
    SJ(8, "三检"),
    M3E(9, "第三次月考"),
    WEEKE(10, "周测"),
    ZXXL(50, "专项"),
    ZK(51, "中考"),
    DYCS(52, "单元测"),
    MNKS(53, "模拟考"),
    JS(54, "竞赛"),
    GK(55, "高考"),
    YK(56, "月考"),
    LK(57, "联考"),
    QT(99, "其他");

    /** 学校考试子类型编码 */
    private Integer code;

    /** 学校考试子类型说明 */
    private String desc;

    OrgStageEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据学校考试阶段描述信息获取编码
     * @param desc
     * @param defaultNull
     * @return
     */
    public static OrgStageEnum getByDesc(String desc, OrgStageEnum defaultNull) {
        for (OrgStageEnum orgStageEnum : OrgStageEnum.values()) {
            if (orgStageEnum.getDesc().equals(desc)) {
                return orgStageEnum;
            }
        }
        return defaultNull;
    }

    /**
     * 根据学校考试子类型描述信息获取详细信息
     * @param code
     * @param defaultNull
     * @return
     */
    public static OrgStageEnum getByCode(Integer code, OrgStageEnum defaultNull) {
        for (OrgStageEnum orgStageEnum : OrgStageEnum.values()) {
            if (orgStageEnum.getCode().equals(code)) {
                return orgStageEnum;
            }
        }
        return defaultNull;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
