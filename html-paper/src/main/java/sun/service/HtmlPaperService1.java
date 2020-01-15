//package sun.service;
//
//import org.apache.commons.collections4.ListUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.jsoup.safety.Whitelist;
//import sun.pojo.bo.HtmlPaperBO;
//import sun.pojo.bo.PaperTypeReturnBO;
//import sun.enums.OrgStageEnum;
//import sun.enums.ResourceSubTypeEnum;
//import sun.enums.ResourceTypeEnum;
//import sun.pojo.po.QuestionsImportPO;
//import sun.pojo.po.QuestionsNoRepeatPO;
//import sun.utils.Path2TypeUtil;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.BeanWrapperImpl;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.Charset;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;
//
//@Service
//@Slf4j
//public class HtmlPaperService1 {
//
//    /**
//     * 判断试题子类型的关键字
//     */
//    private static final String KXCS = "开学测试";
//    private static final String KXCE = "开学考试";
//    private static final String ZK_SX_MN = "中考数学模拟";
//    private static final String ZZ_ER_JIAN = "郑州二检";
//    private static final String ZZ_YI_JIAN = "郑州一检";
//    private static final String SCHOOL_EXAM = "名校试卷";
//    private static final String SCHOOL_EXAM_2 = "第一次月考";
//    private static final String SCHOOL_EXAM_3 = "期中";
//    private static final String SCHOOL_EXAM_4 = "第二次月考";
//    private static final String SCHOOL_EXAM_5 = "期末";
//    private static final String SCHOOL_EXAM_6 = "第三次月考";
//    private static final String SCHOOL_EXAM_7 = "周测";
//    private static final String SCHOOL_EXAM_8 = "周考";
//    private static final String EXAM_YJ = "一检";
//    private static final String EXAM_YM = "一模";
//    private static final String EXAM_YC = "第一次质量检测";
//    private static final String EXAM_EJ = "二检";
//    private static final String EXAM_EM = "二模";
//    private static final String EXAM_EC = "第二次质量检测";
//    private static final String EXAM_SJ = "三检";
//    private static final String EXAM_SM = "三模";
//    private static final String EXAM_SC = "第三次质量检测";
//    private static final String AREA_EXAM = "地区考试";
//    private static final String ZXSJ = "众享试卷";
//    private static final String DYCS = "单元测";
//    private static final String YK = "月考";
//
//    private static final String separator = "∟1∟";
//
//    public void parseHtmlPaper(File htmlZipFile) throws IOException {
//
//        if (!htmlZipFile.exists() || !htmlZipFile.getName().endsWith("zip")) {
//            log.error("文件不存在或文件后缀格式不对");
//            return;
//        }
//        Map<String, byte[]> picMap = new LinkedHashMap<>();
//        InputStream htmlStream = readPaperFile(htmlZipFile, picMap);
//        if (htmlStream == null) {
//            log.error("没有获取到html试卷的流");
//            return;
//        }
//        String html = IOUtils.toString(htmlStream, "UTF-8");
//        Document document = Jsoup.parse(html);
//        List<String> tempClassTopicInfoDTOs = getHtmlContentFromJson(document, "tempClassTopicInfoDTOs");
//        List<String> examInfo = getHtmlContentFromJson(document, "examInfo");
//
//        if (CollectionUtils.isEmpty(tempClassTopicInfoDTOs) || CollectionUtils.isEmpty(examInfo)) {
//            log.error("获取html内容失败");
//            return;
//        }
//
//        List<HtmlPaperBO> htmlPaperBOList = getHtmlPaperBOList(htmlZipFile, tempClassTopicInfoDTOs, examInfo);
//        QuestionsImportPO questionsImportPO = parseQuestionsImportPO(htmlPaperBOList);
//        List<QuestionsNoRepeatPO> questionsNoRepeatPOList = parseQuestionsRepeatPO(questionsImportPO, htmlPaperBOList);
//
//        System.out.println(questionsImportPO);
//        for (QuestionsNoRepeatPO questionsNoRepeatPO : questionsNoRepeatPOList) {
//            System.out.println(questionsNoRepeatPO);
//        }
//
//    }
//
//    public List<QuestionsNoRepeatPO> parseQuestionsRepeatPO(QuestionsImportPO questionsImportPO, List<HtmlPaperBO> htmlPaperBOList) {
//
//        List<QuestionsNoRepeatPO> questionsNoRepeatPOList = new ArrayList<>();
//        boolean parseCombineBlank = parseCombineBlank(htmlPaperBOList);
//        if (!parseCombineBlank) {
//            return questionsNoRepeatPOList;
//        }
//
//        // 去除html标签 且图片解析出来
//        // TODO
//        StringBuilder quests = new StringBuilder();
//        for (HtmlPaperBO htmlPaperBO : htmlPaperBOList) {
//            String rowKey = UUID.randomUUID().toString().replace("-", "");
//            QuestionsNoRepeatPO questionsNoRepeatPO = QuestionsNoRepeatPO.builder()
//                    .rowKey(rowKey)
//                    .resourceType(questionsImportPO.getResourceType())
//                    .docName(htmlPaperBO.getResourceName()).pathId(questionsImportPO.getRowKey())
//                    .build();
//            copyPropertiesIgnoreNull(htmlPaperBO, questionsNoRepeatPO);
//            questionsNoRepeatPOList.add(questionsNoRepeatPO);
//            Double score = questionsNoRepeatPO.getScore();
//            if (score != null) {
//                quests.append(questionsNoRepeatPO.getQseq()).append("-").append(rowKey).append("-").append(score).append(",");
//            } else {
//                quests.append(questionsNoRepeatPO.getQseq()).append("-").append(rowKey).append(",");
//            }
//        }
//        questionsImportPO.setQuests(quests.toString());
//        return questionsNoRepeatPOList;
//    }
//
//    public boolean parseCombineBlank(List<HtmlPaperBO> htmlPaperBOList) {
//        Optional<String> max = htmlPaperBOList.stream().map(HtmlPaperBO::getQseq).max(Comparator.comparing(Integer::valueOf));
//        if (max.isEmpty()) {
//            log.error("获取最大题的序号为空");
//            return false;
//        }
//        String maxSeq = max.get();
//        HtmlPaperBO blankBO = null;
//        if (htmlPaperBOList.size() != Integer.parseInt(maxSeq)) {
//            // 需要拆分填空题
//            int firstSeq = Integer.parseInt(htmlPaperBOList.get(0).getQseq());
//            for (int i = 1; i < htmlPaperBOList.size(); i++) {
//                HtmlPaperBO htmlPaperBO = htmlPaperBOList.get(i);
//                String qseq = htmlPaperBO.getQseq();
//                if (Integer.parseInt(qseq) - 1 == firstSeq) {
//                    firstSeq = Integer.parseInt(qseq);
//                } else {
//                    blankBO = htmlPaperBOList.get(i - 1);
//                    break;
//                }
//            }
//
//            if (blankBO == null) {
//                return false;
//            }
//            String content = blankBO.getContent();
//            List<String> separatorContent = getSeparatorContent(content, htmlPaperBOList, maxSeq);
//            String answer = blankBO.getAnswer();
//            List<String> separatorAnswer = getSeparatorContent(answer, htmlPaperBOList, maxSeq);
//            String analysis = blankBO.getAnalysis();
//            List<String> separatorAnalysis = getSeparatorContent(analysis, htmlPaperBOList, maxSeq);
//
//            if (separatorContent.size() != separatorAnswer.size() || separatorAnswer.size() != separatorAnalysis.size()) {
//                log.error("解析填空题出错");
//                return false;
//            }
//
//            double score = blankBO.getScore() / separatorContent.size();
//            List<HtmlPaperBO> htmlPaperBlank = new ArrayList<>();
//            for (int i = 0; i < separatorContent.size(); i++) {
//                HtmlPaperBO htmlPaperBO = HtmlPaperBO.builder().build();
//                copyPropertiesIgnoreNull(blankBO, htmlPaperBO);
//                htmlPaperBO.setContent(separatorContent.get(i));
//                htmlPaperBO.setAnalysis(separatorAnalysis.get(i));
//                htmlPaperBO.setAnswer(separatorAnswer.get(i));
//                htmlPaperBO.setScore(score);
//                htmlPaperBO.setQseq(String.valueOf(Integer.parseInt(blankBO.getQseq()) + i));
//                htmlPaperBlank.add(htmlPaperBO);
//            }
//            htmlPaperBOList.addAll(htmlPaperBOList.indexOf(blankBO), htmlPaperBlank);
//            htmlPaperBOList.remove(blankBO);
//        }
//        return true;
//    }
//
//    public List<String> getSeparatorContent(String content, List<HtmlPaperBO> htmlPaperBOList, String maxSeq) {
//        // 去标签，可以分离出来，后续使用
//        content = content.replace("</p>", "</p>" + separator).replaceAll("<br[ ]*?/>", separator);
//        String cleanContent = Jsoup.clean(content, Whitelist.none());
//        String[] split = cleanContent.split(separator);
//        List<String> seqContent = new ArrayList<>();
//        if (htmlPaperBOList.size() + split.length - 1 == Integer.parseInt(maxSeq)) {
//            seqContent.addAll(Arrays.asList(split));
//        } else {
//            StringBuilder c = new StringBuilder();
//            for (String s : split) {
//                // TODO 是否需要进一步匹配判断？
//                Matcher matcher = Pattern.compile("^\\\\\\(\\((\\d+.*?)\\)\\\\\\)", Pattern.DOTALL).matcher(s.trim());
//                if (matcher.find()) {
//                    if (StringUtils.isNotBlank(c.toString())) {
//                        seqContent.add(c.toString());
//                    }
//                    c = new StringBuilder(s);
//                } else {
//                    c.append(s);
//                }
//            }
//            if (StringUtils.isNotBlank(c.toString())) {
//                seqContent.add(c.toString());
//            }
//        }
//        return seqContent;
//    }
//
//
//    public QuestionsImportPO parseQuestionsImportPO(List<HtmlPaperBO> htmlPaperBOList) {
//
//        htmlPaperBOList = htmlPaperBOList.stream()
//                .sorted((o1, o2) -> Integer.valueOf(o2.getQseq()).compareTo(Integer.valueOf(o1.getQseq())))
//                .collect(Collectors.toList());
//        HtmlPaperBO htmlPaperBO = htmlPaperBOList.get(0);
//        String qNum = htmlPaperBO.getQseq();
//        String path = htmlPaperBO.getPath();
//        OrgStageEnum orgStage = getOrgStage(path);
//        // 读取地区、学校信息
////        Map<Integer, List<String>> areaMap = TextbookVersionInfoService.getAreaVersionFromHive();
////        Map<Integer, List<String>> orgMap = TextbookVersionInfoService.getOrgVersionFromHive();
//        PaperTypeReturnBO paperTypeReturnBO = new PaperTypeReturnBO();
//
//        QuestionsImportPO questionsImportPO = QuestionsImportPO.builder()
//                .rowKey(UUID.randomUUID().toString().replace("-", ""))
//                .path(path).questNum(qNum).resourceType(String.valueOf(ResourceTypeEnum.MNSJ.getType()))
//                .resourceSubType(String.valueOf(ResourceSubTypeEnum.SCHOOL_EXAM.getCode()))
//                .orgStage(String.valueOf(orgStage == null ? null : orgStage.getCode()))
//                .resourceName(htmlPaperBO.getResourceName())
//                .build();
//        copyPropertiesIgnoreNull(paperTypeReturnBO, questionsImportPO);
//        return questionsImportPO;
//    }
//
//    public List<HtmlPaperBO> getHtmlPaperBOList(File htmlZipFile, List<String> tempClassTopicInfoDTOs, List<String> examInfo) {
//        List<HtmlPaperBO> htmlPaperBOList = new ArrayList<>();
//        for (String tempClassTopicInfoDTO : tempClassTopicInfoDTOs) {
//            HtmlPaperBO htmlPaperBO = JSONObject.parseObject(tempClassTopicInfoDTO, HtmlPaperBO.class);
//            HtmlPaperBO htmlPaperBO1 = JSONObject.parseObject(examInfo.get(0), HtmlPaperBO.class);
//            copyPropertiesIgnoreNull(htmlPaperBO1, htmlPaperBO);
//            String allContent = htmlPaperBO.getAllContent();
//            HtmlPaperBO htmlPaperBO2 = JSONObject.parseObject(allContent, HtmlPaperBO.class);
//            copyPropertiesIgnoreNull(htmlPaperBO2, htmlPaperBO);
//            htmlPaperBO.setPath(htmlZipFile.getAbsolutePath());
//            htmlPaperBOList.add(htmlPaperBO);
//        }
//
//        return htmlPaperBOList;
//    }
//
//    public static String[] getNullPropertyNames(Object source) {
//
//        final BeanWrapper src = new BeanWrapperImpl(source);
//        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
//
//        Set<String> emptyNames = new HashSet<>();
//        for (java.beans.PropertyDescriptor pd : pds) {
//            Object srcValue = src.getPropertyValue(pd.getName());
//            if (srcValue == null) emptyNames.add(pd.getName());
//        }
//        String[] result = new String[emptyNames.size()];
//        return emptyNames.toArray(result);
//    }
//
//    public static void copyPropertiesIgnoreNull(Object src, Object target) {
//        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
//    }
//
//    public List<String> getHtmlContentFromJson(Document document, String varName) {
//        // 获取脚本
//        Elements scripts = document.getElementsByTag("script");
//        for (Element script : scripts) {
//            // 获取指定变量的值
//            Matcher matcher = Pattern.compile("var[ ]+" + varName + "[ ]+=(.*);").matcher(script.data());
//            if (matcher.find()) {
//                String text = matcher.group(1).trim();
//                if (JSONObject.isValidArray(text)) {
//                    JSONArray jsonArray = JSONObject.parseArray(text);
//                    List<String> arrayLists = jsonArray.toJavaList(String.class);
//                    if (CollectionUtils.isNotEmpty(arrayLists)) {
//                        return arrayLists;
//                    }
//                }
//                if (JSONObject.isValid(text)) {
//                    text = StringUtils.removeStart(text, "'");
//                    text = StringUtils.removeEnd(text, "'");
//                    text = text.replace("{", "{\"").replace("=", "\":\"")
//                            .replaceAll(",[ ]*", "\",\"").replace("}", "\"}");
//                    String s = JSONObject.parseObject(text).toJavaObject(String.class);
//                    if (StringUtils.isNotBlank(s)) {
//                        return Collections.singletonList(s);
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//    public InputStream readPaperFile(File htmlZipFile, Map<String, byte[]> picMap) throws IOException {
//        InputStream htmlStream = null;
//        ZipFile zipFile = new ZipFile(htmlZipFile, Charset.forName("GBK"));
//        Enumeration<? extends ZipEntry> entries = zipFile.entries();
//        while (entries.hasMoreElements()) {
//            ZipEntry zipEntry = entries.nextElement();
//            InputStream inputStream = zipFile.getInputStream(zipEntry);
//            if (!zipEntry.isDirectory() && FilenameUtils.getExtension(zipEntry.getName()).equals("html")) {
//                htmlStream = inputStream;
//            }
//            // TODO 暂使用png固定
//            if (!zipEntry.isDirectory() && FilenameUtils.getExtension(zipEntry.getName()).equals("png")) {
//                picMap.put(zipEntry.getName(), IOUtils.toByteArray(inputStream));
//            }
//        }
//        return htmlStream;
//    }
//
//    public OrgStageEnum getOrgStage(String filePath) {
//        if (filePath.contains(KXCE) || filePath.contains(KXCS)) {
//            return OrgStageEnum.KXCS;
//        } else if (filePath.contains(SCHOOL_EXAM_2)) {
//            return OrgStageEnum.M1E;
//        } else if (filePath.contains(SCHOOL_EXAM_3)) {
//            return OrgStageEnum.ME;
//        } else if (filePath.contains(SCHOOL_EXAM_4)) {
//            return OrgStageEnum.M2E;
//        } else if (filePath.contains(SCHOOL_EXAM_5)) {
//            return OrgStageEnum.LE;
//        } else if (filePath.contains(EXAM_YJ) || filePath.contains(EXAM_YM) || filePath.contains(EXAM_YC)) {
//            return OrgStageEnum.YJ;
//        } else if (filePath.contains(EXAM_EJ) || filePath.contains(EXAM_EM) || filePath.contains(EXAM_EC)) {
//            return OrgStageEnum.EJ;
//        } else if (filePath.contains(EXAM_SJ) || filePath.contains(EXAM_SM) || filePath.contains(EXAM_SC)) {
//            return OrgStageEnum.SJ;
//        } else if (filePath.contains(SCHOOL_EXAM_6)) {
//            return OrgStageEnum.M3E;
//        } else if (filePath.contains(SCHOOL_EXAM_7) || filePath.contains(SCHOOL_EXAM_8)) {
//            return OrgStageEnum.WEEKE;
//        } else if (filePath.contains(DYCS)) {
//            return OrgStageEnum.DYCS;
//        } else if (filePath.contains(YK)) {
//            return OrgStageEnum.YK;
//        } else {
//            return null;
//        }
//    }
//}
