package sun.enums;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @auther mxy
 * @date 2018/8/17
 */
public enum ProvinceEnum {

    //省份名称及对应编码
    HE_NAN(1 , "河南"),
    NEI_MENG_GU(2 , "内蒙"),
    BEI_JING(3 , "北京"),
    TIAN_JIN(4 , "天津"),
    HE_BEI(5 , "河北"),
    SHAN_XI(6 , "山西"),
    LIAO_NING(7 , "辽宁"),
    JI_LIN(8 , "吉林"),
    HEI_LONG_JIANG(9, "黑龙江"),
    SHANG_HAI(10 , "上海"),
    JIANG_SU(11 , "江苏"),
    ZHE_JIANG(12, "浙江"),
    AN_HUI(13 , "安徽"),
    FU_JIAN(14 , "福建"),
    JIANG_XI(15 , "江西"),
    SHAN_DONG(16 , "山东"),
    HU_BEI(17 , "湖北"),
    HU_NAN(18 , "湖南"),
    GUANG_DONG(19 , "广东"),
    GUANG_XI(20, "广西"),
    HAI_NAN(21 , "海南"),
    CHONG_QING(22 , "重庆"),
    SI_CHUAN(23 , "四川"),
    GUI_ZHOU(24 , "贵州"),
    YUN_NAN(25 , "云南"),
    XI_ZHANG(26 , "西藏"),
    SHAN_XI_3(27, "陕西"),
    GAN_SU(28 , "甘肃"),
    QING_HAI(29 , "青海"),
    NING_XIA(30 , "宁夏"),
    XIN_JIANG(31 , "新疆"),
    TAI_WAN(32, "台湾"),
    XINAG_GANG(33, "香港"),
    AO_MEN(34, "澳门"),
    GUO_WAI(35, "国外"),
    DIAO_YU_DAO(36, "钓鱼岛");

    /** 省份编码 */
    private Integer code;
    /** 省份名 */
    private String province;

    ProvinceEnum(Integer code, String province) {
        this.code = code;
        this.province = province;
    }

    /**
     * 根据资源类型编码，获取资源类型枚举值
     * @param province
     * @param defaultIfNull
     * @return
     */
    public static ProvinceEnum getByProvince(String province, ProvinceEnum defaultIfNull) {
        for (ProvinceEnum provinceEnum : ProvinceEnum.values()) {
            if (provinceEnum.getProvince().equals(province)) {
                return provinceEnum;
            }
        }
        return defaultIfNull;
    }

    /**
     * 根据资源类型编码，获取资源类型枚举值
     * @param code
     * @param defaultIfNull
     * @return
     */
    public static ProvinceEnum getByCode(Integer code, ProvinceEnum defaultIfNull) {
        for (ProvinceEnum provinceEnum : ProvinceEnum.values()) {
            if (provinceEnum.getCode().equals(code)) {
                return provinceEnum;
            }
        }
        return defaultIfNull;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Integer getCode() {
        return code;
    }

    public String getProvince() {
        return province;
    }
}
