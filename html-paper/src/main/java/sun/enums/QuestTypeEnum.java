//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sun.enums;

public enum QuestTypeEnum {
    CHOICE("1", "选择题"),
    FILL_IN_BLANK("2", "填空题"),
    COMPREHENSIVE("3", "综合题"),
    QT("0", "其他");

    private String code;
    private String desc;

    private QuestTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static QuestTypeEnum getQuestTypeEnumByCode(String code) {
        QuestTypeEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            QuestTypeEnum questTypeEnum = var1[var3];
            if (code.equals(questTypeEnum.getCode())) {
                return questTypeEnum;
            }
        }

        return QT;
    }

    public static QuestTypeEnum getQuestTypeEnumByDesc(String desc) {
        QuestTypeEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            QuestTypeEnum questTypeEnum = var1[var3];
            if (questTypeEnum.getDesc().equals(desc)) {
                return questTypeEnum;
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
