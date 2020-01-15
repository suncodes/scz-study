//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sun.enums;

public enum GradeEnum {
    PRIMARY_1("21", "一年级"),
    PRIMARY_2("22", "二年级"),
    PRIMARY_3("23", "三年级"),
    PRIMARY_4("24", "四年级"),
    PRIMARY_5("25", "五年级"),
    PRIMARY_6("26", "六年级"),
    JUNIOR_1("31", "七年级"),
    JUNIOR_2("32", "八年级"),
    JUNIOR_3("33", "九年级"),
    SENIOR_1("41", "高一"),
    SENIOR_2("42", "高二"),
    SENIOR_3("43", "高三"),
    QT("00", "其他");

    private String code;
    private String desc;

    private GradeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static GradeEnum getGradeEnumByDesc(String desc) {
        GradeEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            GradeEnum gradeEnum = var1[var3];
            if (gradeEnum.getDesc().equals(desc)) {
                return gradeEnum;
            }
        }

        return QT;
    }

    public static GradeEnum getGradeEnumByCode(String code) {
        GradeEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            GradeEnum gradeEnum = var1[var3];
            if (gradeEnum.getCode().equals(code)) {
                return gradeEnum;
            }
        }

        return QT;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
