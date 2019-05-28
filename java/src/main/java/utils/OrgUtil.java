package utils;


import org.apache.commons.lang3.Validate;

public class OrgUtil {

    public static void main(String[] args) {

        // 对于检验失败的场景，抛出异常，message自定义
        // Exception in thread "main" java.lang.NullPointerException: hehe
        // at org.apache.commons.lang3.Validate.notNull(Validate.java:225)
        // at utils.OrgUtil.main(OrgUtil.java:11)
        Validate.notNull(null,"hehe");
    }
}
