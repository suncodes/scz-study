package sun.utils;

import sun.enums.GradeEnum;
import sun.pojo.bo.PaperTypeReturnBO;
import sun.enums.ProvinceEnum;
import sun.enums.VersionEnum;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 从文件路径获取试题相关的属性信息
 */
public class Path2TypeUtil {

    /** 试卷年级属性 */
    private static final String GRADE = "七年级,八年级,九年级,高一,高二,高三";

    /** 试卷年份属性 */
    private static final String YEAR = "20\\d{2}";
//    private static final String YEAR2 = "201\\d年";

    /** 试题学期 */
    private static final String TERM = "上册,下册,上学期,下学期,（上）,（下）";

    /** 试题版本 */
    private static final String VERSION = "人教,北师,华师,冀教,鲁教";

    /** 试题类型 */
    private static final String PERIOD = "基础+综合,基础,综合";

    /** 试卷省份 */
    private static final String PROVINCE = "河南，内蒙，北京，天津，河北，山西，辽宁，吉林，黑龙江，上海，江苏，浙江，" +
            "安徽，福建，江西，山东，湖北，湖南，广东，广西，海南，重庆，四川，贵州，云南，西藏，陕西，甘肃，青海，宁夏，" +
            "新疆，台湾，钓鱼岛，香港，澳门";

    /**
     * 将试题的学期和类型信息转化成对应的编码
     * @param word
     * @return
     */
    public static String word2num(String word){
        String term = "";
        if ("基础+综合".equals(word)) {
            term = "3";
        } else if ("上册".equals(word) || "上学期".equals(word) || "（上）".equals(word) || "基础".equals(word)){
            term = "1";
        } else if ("下册".equals(word) || "下学期".equals(word) || "（下）".equals(word) || "综合".equals(word)){
            term = "2";
        }
        return term;
    }


    /**
     * 按照指定的目录规划获取导入试题的年级、版本等信息
     * @param path
     * @return
     */
    public static PaperTypeReturnBO paperType (String path, Map<Integer, List<String>> areaMap,
                                               Map<Integer, List<String>> orgMap) {
        PaperTypeReturnBO paperTypeReturnBO = new PaperTypeReturnBO();
        path = path.replace("八上", "八年级上学期")
                .replace("七上", "七年级上学期")
                .replace("九上", "九年级上学期")
                .replace("八下", "八年级下学期")
                .replace("七下", "七年级下学期")
                .replace("九下", "九年级下学期");

        String[] paperTypes = path.split("\\\\");

        // 获取讲名称
        String name = paperTypes[paperTypes.length-1];
        name = name.replace("一模","九年级上学期").replace("二模", "九年级下学期")
                .replace("三模", "九年级下学期").replace("一检","九年级上学期")
                .replace("二检", "九年级下学期").replace("三检", "九年级下学期")
                .replace("第一次质量检测","九年级上学期").replace("第二次质量检测", "九年级下学期")
                .replace("第三次质量检测", "九年级下学期");

        // 获取年份信息  是否包含重新导入过程中的pathid
        String reimport_id = "【[0-9a-f]{32}】";
        Pattern r_pattern = Pattern.compile(reimport_id);
        Matcher r_matcher = r_pattern.matcher(name);
        boolean isReimport = r_matcher.find();
        if (isReimport) {
            //匹配
            Pattern pattern2 = Pattern.compile(YEAR);
            Matcher matcher2 = pattern2.matcher(path);
           if (matcher2.find()) {
                paperTypeReturnBO.setYear(matcher2.group(0));
            }
        } else {
            //不匹配
            Pattern pattern = Pattern.compile(YEAR);
            Pattern pattern2 = Pattern.compile(YEAR);
            Matcher matcher = pattern.matcher(name);
            Matcher matcher2 = pattern2.matcher(path);
            if (matcher.find()){
                paperTypeReturnBO.setYear(matcher.group(0));
            } else if (matcher2.find()) {
                paperTypeReturnBO.setYear(matcher2.group(0));
            }
        }

        // 年级
        String[] grades = GRADE.split(",");
        for (String grade1 : grades) {
            if (name.contains(grade1)) {
                paperTypeReturnBO.setGrade(GradeEnum.getGradeEnumByDesc(grade1).getCode());
                break;
            } else if (path.contains(grade1)) {
                paperTypeReturnBO.setGrade(GradeEnum.getGradeEnumByDesc(grade1).getCode());
                break;
            }
        }

        // 学期
        String[] terms =  TERM.split(",");
        for (String term : terms){
            if (name.contains(term)) {
                paperTypeReturnBO.setTerm(Path2TypeUtil.word2num(term));
                break;
            } else if (path.contains(term)){
                paperTypeReturnBO.setTerm(Path2TypeUtil.word2num(term));
                break;
            }
        }

        // 版本
        String[] versions = VERSION.split(",");
        for (String v : versions){
            if (name.contains(v)){
                paperTypeReturnBO.setVersion(VersionEnum.getByDesc(v, null).getCode().toString());
                break;
            } else if (path.contains(v)){
                paperTypeReturnBO.setVersion(VersionEnum.getByDesc(v, null).getCode().toString());
                break;
            }
        }

        // 类型
        String[] periods = PERIOD.split(",");
        for (String p : periods){
//            if (name.contains(p)){
//                paperTypeReturnBO.setPeriod(Path2TypeUtil.word2num(p));
//                break;
//            } else

            if (path.contains(p)){
                paperTypeReturnBO.setPeriod(Path2TypeUtil.word2num(p));
                break;
            }
        }

        //省份
        String[] provinces = PROVINCE.split("，");
        for (String pro : provinces) {
            if(name.contains(pro)){
                paperTypeReturnBO.setProvince(ProvinceEnum.getByProvince(pro, null).getCode().toString());
                break;
            } else if (path.contains(pro)){
                paperTypeReturnBO.setProvince(ProvinceEnum.getByProvince(pro, null).getCode().toString());
                break;
            }
        }

        //地区
        for (int i = 1; i <= areaMap.size(); i++) {
            // 地区名称
            if (name.contains(areaMap.get(i).get(0))) {
                // 地区编码
                paperTypeReturnBO.setArea(areaMap.get(i).get(1));
                // 省份编码
                paperTypeReturnBO.setProvince(areaMap.get(i).get(2));
                break;
            } else if (path.contains(areaMap.get(i).get(0))) {
                paperTypeReturnBO.setArea(areaMap.get(i).get(1));
                paperTypeReturnBO.setProvince(areaMap.get(i).get(2));
                break;
            }
        }

        //学校
        String temp = "";
        for (int i = 1; i <= orgMap.size(); i++){
            // 学校名称
            if(name.contains(orgMap.get(i).get(1))){
                // 学校标识编码
                if (orgMap.get(i).get(0).length() > temp.length()) {
                    temp = orgMap.get(i).get(0);
                    // 学校编码
                    paperTypeReturnBO.setOrg(orgMap.get(i).get(0));
                    if (orgMap.get(i).size() >= 4) {
                        // 所在地区
                        paperTypeReturnBO.setArea(orgMap.get(i).get(2));
                        // 所在省份
                        paperTypeReturnBO.setProvince(orgMap.get(i).get(3));
                    }
                }
            } else if (handleOrgName(name).contains(orgMap.get(i).get(1))){
                if (orgMap.get(i).get(0).length() > temp.length()) {
                    temp = orgMap.get(i).get(0);
                    paperTypeReturnBO.setOrg(orgMap.get(i).get(0));
                    if (orgMap.get(i).size() >= 4) {
                        paperTypeReturnBO.setArea(orgMap.get(i).get(2));
                        paperTypeReturnBO.setProvince(orgMap.get(i).get(3));
                    }
                }
            } else if (path.contains(orgMap.get(i).get(1))){
                if (orgMap.get(i).get(0).length() > temp.length()) {
                    temp = orgMap.get(i).get(0);
                    paperTypeReturnBO.setOrg(orgMap.get(i).get(0));
                    if (orgMap.get(i).size() >= 4) {
                        paperTypeReturnBO.setArea(orgMap.get(i).get(2));
                        paperTypeReturnBO.setProvince(orgMap.get(i).get(3));
                    }
                }
            }
        }

        return paperTypeReturnBO;
    }

    /**
     * 将文件名中的学校名简称替换为系统内的学校名称（常导入试题的12所学校中部分学校的简称）
     * @param path 文件名
     * @return 替换简称后的文件名
     */
    private static String handleOrgName (String path) {
        path = path.replace("信阳市第九中学", "河南省信阳市第九中学")
                .replace("郑州市外国语中学", "郑州外国语中学")
                .replace("郑州市外国语", "郑州外国语中学")
                // todo 暂时替换，后续需要完善
                .replace("郑州一中", "郑州市第一中学")
                .replace("郑州枫杨外国语", "郑州枫杨外国语学校")
                .replace("郑州市枫杨外国语", "郑州枫杨外国语学校")
                .replace("郑州枫杨外国语中学", "郑州枫杨外国语学校")
                .replace("河南省实验", "河南省实验中学")
                .replace("石家庄市玉城中学", "石家庄市行唐县玉城中学");
        return path;
    }
}
