package utils;

import org.junit.Test;
import org.springframework.util.DigestUtils;

/**
 * 常用工具类，持续更新，，，
 * @author sunchuizhe
 */
public class CommonUtils {

    @Test
    public void digestTest() {
        DigestUtils.md5DigestAsHex("".getBytes());
    }
}
