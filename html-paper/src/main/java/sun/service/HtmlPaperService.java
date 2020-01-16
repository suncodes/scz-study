package sun.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.docx4j.relationships.Relationship;
import org.jsoup.safety.Whitelist;
import sun.enums.QuestTypeEnum;
import sun.pojo.bo.HtmlPaperBO;
import sun.enums.OrgStageEnum;
import sun.enums.ResourceSubTypeEnum;
import sun.enums.ResourceTypeEnum;
import sun.pojo.po.QuestionsImportPO;
import sun.pojo.po.QuestionsNoRepeatPO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.latextoword.Latex_Word;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;

import javax.xml.bind.JAXBException;

@Service
@Slf4j
public class HtmlPaperService {

    private static final String separator = "∟1∟";

    private static ObjectFactory factory = Context.getWmlObjectFactory();

    /**
     * 解析压缩文件，并进行入库
     *
     * @param htmlZipFile
     * @throws IOException
     */
    public void parseHtmlPaper(File htmlZipFile) throws Exception {

        if (!htmlZipFile.exists() || !htmlZipFile.getName().endsWith("zip")) {
            log.error("文件不存在或文件后缀格式不对");
            return;
        }
        Map<String, byte[]> picMap = new LinkedHashMap<>();
        InputStream htmlStream = readPaperFile(htmlZipFile, picMap);
        if (htmlStream == null) {
            log.error("没有获取到html试卷的流");
            return;
        }
        // 获取对应的json串
        String html = IOUtils.toString(htmlStream, "UTF-8");
        Document document = Jsoup.parse(html);
        List<String> tempClassTopicInfoDTOs = getHtmlContentFromJson(document, "tempClassTopicInfoDTOs");
        List<String> examInfo = getHtmlContentFromJson(document, "examInfo");

        if (CollectionUtils.isEmpty(tempClassTopicInfoDTOs) || CollectionUtils.isEmpty(examInfo)) {
            log.error("获取html内容失败");
            return;
        }

        // 从json中解析出对应的BO
        HtmlPaperBO htmlPaperBO = getHtmlPaperBOList(htmlZipFile, picMap, tempClassTopicInfoDTOs, examInfo);
        // 解析日志表中的PO
        QuestionsImportPO questionsImportPO = parseQuestionsImportPO(htmlPaperBO);
        List<QuestionsNoRepeatPO> questionsNoRepeatPOList = parseQuestionsRepeatPO(questionsImportPO, htmlPaperBO);


        // 打印测试
        System.out.println(Arrays.toString(getNullPropertyNames(htmlPaperBO)));
        System.out.println(Arrays.toString(getNullPropertyNames(questionsImportPO)));
        for (QuestionsNoRepeatPO questionsNoRepeatPO : questionsNoRepeatPOList) {
            System.out.println(Arrays.toString(getNullPropertyNames(questionsNoRepeatPO)));
        }
        // TODO 实际导入之前需要去重
        // 导入试题
//        HBaseUtils hBaseUtils = new HBaseUtils(Constant.ZOOKEEPER);
//        if (IS_IMPORT_HBASE) {
//            QuestInfoBO questInfoBO = new QuestInfoBO(questionsImportPO, questionsNoRepeatPOList);
//            wordAnalysisService.insertToHbase(questInfoBO);
//        }
//        // 图片入库
//        if (IS_UPLOAD_PIC) {
//            Map<String, byte[]> imgMap = htmlPaperBO.getImgMap();
//            for (String rowKey : imgMap.keySet()) {
//                hBaseUtils.insertFileBytes2HBase(Constant.QUEST_IMAGE, rowKey, Constant.COLUMN_FAMILY,
//                        "IMAGE", imgMap.get(rowKey));
//            }
//        }
        // 小word入库
//        if (IS_UPLOAD_SMALL_WORD) {
            Map<String, byte[]> imgMap = htmlPaperBO.getImgMap();
            for (QuestionsNoRepeatPO questionsNoRepeatPO : questionsNoRepeatPOList) {
                String qseq = questionsNoRepeatPO.getQseq();
                String rowKey = questionsNoRepeatPO.getRowKey();
                String content = questionsNoRepeatPO.getContent();
                System.out.println(content);
                byte[] questSmallWord = createSmallWord(imgMap, content);

//                String answer = questionsNoRepeatPO.getAnswer();
//                String analysis = questionsNoRepeatPO.getAnalysis();
//                byte[] answerSmallWord = createSmallWord(imgMap, answer, analysis);
//                File docx1 = new File("F:\\工作记录及文件及成果\\工作记录（2020）\\20200114-html试卷\\" + qseq + "-answer.docx");
//                FileUtils.writeByteArrayToFile(docx1, answerSmallWord, false);
                break;
            }
//        }
    }

    /**
     * 解析出对应的日志表对应的字段
     *
     * @param htmlPaperBO
     * @return
     */
    public QuestionsImportPO parseQuestionsImportPO(HtmlPaperBO htmlPaperBO) {

        List<HtmlPaperBO.HtmlQuestBO> htmlQuestBOList = htmlPaperBO.getHtmlQuestBOList();
        htmlQuestBOList = htmlQuestBOList.stream()
                .sorted((o1, o2) -> Integer.valueOf(o2.getQseq()).compareTo(Integer.valueOf(o1.getQseq())))
                .collect(Collectors.toList());
        HtmlPaperBO.HtmlQuestBO htmlQuestBO = htmlQuestBOList.get(0);
        String qNum = htmlQuestBO.getQseq();
        String path = htmlPaperBO.getPath();
//        OrgStageEnum orgStage = wordAnalysisService.getOrgStage(path);
        // 读取地区、学校信息
//        Map<Integer, List<String>> areaMap = TextbookVersionInfoService.getAreaVersionFromHive();
//        Map<Integer, List<String>> orgMap = TextbookVersionInfoService.getOrgVersionFromHive();
//        PaperTypeReturnBO paperTypeReturnBO = Path2TypeUtil.paperType(path, areaMap, orgMap);

        QuestionsImportPO questionsImportPO = QuestionsImportPO.builder()
                .rowKey(UUID.randomUUID().toString().replace("-", ""))
                .path(path).questNum(qNum).resourceType(String.valueOf(ResourceTypeEnum.MNSJ.getType()))
                .resourceSubType(String.valueOf(ResourceSubTypeEnum.SCHOOL_EXAM.getCode()))
//                .orgStage(String.valueOf(orgStage == null ? null : orgStage.getCode()))
                .resourceName(htmlPaperBO.getResourceName())
                .build();
//        copyPropertiesIgnoreNull(paperTypeReturnBO, questionsImportPO);
        return questionsImportPO;
    }

    /**
     * 解析出对应每道题的BO
     *
     * @param questionsImportPO
     * @param htmlPaperBO
     * @return
     */
    public List<QuestionsNoRepeatPO> parseQuestionsRepeatPO(QuestionsImportPO questionsImportPO, HtmlPaperBO htmlPaperBO) {

        List<QuestionsNoRepeatPO> questionsNoRepeatPOList = new ArrayList<>();
        // 解析合并的填空题
        boolean parseCombineBlank = parseCombineBlank(htmlPaperBO.getHtmlQuestBOList());
        if (!parseCombineBlank) {
            return questionsNoRepeatPOList;
        }

        Map<String, byte[]> imgMap = htmlPaperBO.getImgMap();
        Map<String, byte[]> needImportImageMap = new LinkedHashMap<>();
        StringBuilder quests = new StringBuilder();
        List<HtmlPaperBO.HtmlQuestBO> htmlQuestBOList = htmlPaperBO.getHtmlQuestBOList();
        for (HtmlPaperBO.HtmlQuestBO htmlQuestBO : htmlQuestBOList) {
            // 判断每一道题的题型
            QuestTypeEnum qTypeByAnswer = getQTypeByAnswer(htmlQuestBO.getAnswer());
            // 每一道题去标签，且解析出对应的图片
            parseHtmlLabelAndPic(htmlQuestBO, imgMap, needImportImageMap);

            String rowKey = UUID.randomUUID().toString().replace("-", "");
            QuestionsNoRepeatPO questionsNoRepeatPO = QuestionsNoRepeatPO.builder()
                    .rowKey(rowKey)
                    .resourceType(questionsImportPO.getResourceType())
                    .resourceName(htmlPaperBO.getResourceName())
                    .docName(htmlPaperBO.getResourceName()).pathId(questionsImportPO.getRowKey())
                    .qType(qTypeByAnswer.getCode()).stage(qTypeByAnswer.getDesc())
                    .build();
            copyPropertiesIgnoreNull(htmlQuestBO, questionsNoRepeatPO);
            questionsNoRepeatPOList.add(questionsNoRepeatPO);
            Double score = questionsNoRepeatPO.getScore();
            if (score != null) {
                quests.append(questionsNoRepeatPO.getQseq()).append("-").append(rowKey).append("-").append(score).append(",");
            } else {
                quests.append(questionsNoRepeatPO.getQseq()).append("-").append(rowKey).append(",");
            }
        }
        htmlPaperBO.setImgMap(needImportImageMap);
        questionsImportPO.setQuests(quests.toString());
        return questionsNoRepeatPOList;
    }

    /**
     * 解析html标签和图片
     *
     * @param htmlQuestBO
     * @param imgMap
     * @param needImportImageMap
     */
    public void parseHtmlLabelAndPic(HtmlPaperBO.HtmlQuestBO htmlQuestBO, Map<String, byte[]> imgMap,
                                     Map<String, byte[]> needImportImageMap) {
        String content = htmlQuestBO.getContent();
        // 去标签
        content = getRemoveLabelContent(content, imgMap, needImportImageMap);
        htmlQuestBO.setContent(content);

        String answer = htmlQuestBO.getAnswer();
        // 去标签
        answer = getRemoveLabelContent(answer, imgMap, needImportImageMap);
        htmlQuestBO.setAnswer(answer);


        String analysis = htmlQuestBO.getAnalysis();
        // 去标签
        analysis = getRemoveLabelContent(analysis, imgMap, needImportImageMap);
        htmlQuestBO.setAnalysis(analysis);
    }

    /**
     * 移除标签并替换图片
     *
     * @param content
     * @param imgMap
     * @param needImportImgMap
     * @return
     */
    public String getRemoveLabelContent(String content, Map<String, byte[]> imgMap, Map<String, byte[]> needImportImgMap) {
        // 去标签
        String retStr = Jsoup.clean(content, Whitelist.none().addTags("p", "img", "table", "tr", "td", "th", "<br/>")
                .addAttributes("img", "align", "alt", "height", "src", "title", "width"));
        // 处理公式
        retStr = retStr.replaceAll("(\\\\\\()(.*?)(\\\\\\))", "\\$$2\\$");
        // 处理图片
        Document document = Jsoup.parse(retStr);
        Elements img = document.getElementsByTag("img");
        for (Element element : img) {
            String src = element.attr("src");
            for (String imgPath : imgMap.keySet()) {
                if (FilenameUtils.getBaseName(imgPath).equals(FilenameUtils.getBaseName(src))) {
                    String imgRowKey = UUID.randomUUID().toString().replace("-", "");
                    needImportImgMap.put(imgRowKey, imgMap.get(imgPath));
                    // TODO 文件路径？
                    String imgName = imgRowKey + ".png";
                    element.attr("src", imgName);
                }
            }
        }
        // 去换行符
        System.out.println(document.body().html());
        return document.body().html().replace("\n", "");
    }

    /**
     * 根据答案样式获取对应的试题类型
     *
     * @param answer 每道题的答案
     * @return
     */
    public QuestTypeEnum getQTypeByAnswer(String answer) {
        answer = StringUtils.deleteWhitespace(answer);
        Matcher matcher = Pattern.compile("\\\\\\([A-D]\\\\\\)").matcher(answer);
        // 需要全局匹配
        if (matcher.matches()) {
            return QuestTypeEnum.CHOICE;
        }
        // 解答题开头有‘解：’字
        String cleanAnswer = Jsoup.clean(answer, Whitelist.none());
        Matcher matcher1 = Pattern.compile("^(解)[:：]").matcher(cleanAnswer);
        if (matcher1.find()) {
            return QuestTypeEnum.COMPREHENSIVE;
        }
        return QuestTypeEnum.FILL_IN_BLANK;
    }

    /**
     * 处理合并填空题，
     *
     * @param htmlQuestBOList
     * @return
     */
    public boolean parseCombineBlank(List<HtmlPaperBO.HtmlQuestBO> htmlQuestBOList) {
        Optional<String> max = htmlQuestBOList.stream().map(HtmlPaperBO.HtmlQuestBO::getQseq)
                .max(Comparator.comparing(Integer::valueOf));
        if (!max.isPresent()) {
            log.error("获取最大题的序号为空");
            return false;
        }
        String maxSeq = max.get();
        // 需要拆分的BO
        HtmlPaperBO.HtmlQuestBO blankBO = null;
        // 试题数量不等于最大题号，说明存在待拆解的题号
        if (htmlQuestBOList.size() != Integer.parseInt(maxSeq)) {
            // 需要拆分填空题
            int firstSeq = Integer.parseInt(htmlQuestBOList.get(0).getQseq());
            for (int i = 1; i < htmlQuestBOList.size(); i++) {
                HtmlPaperBO.HtmlQuestBO htmlQuestBO = htmlQuestBOList.get(i);
                String qseq = htmlQuestBO.getQseq();
                if (Integer.parseInt(qseq) - 1 == firstSeq) {
                    firstSeq = Integer.parseInt(qseq);
                } else {
                    blankBO = htmlQuestBOList.get(i - 1);
                    break;
                }
            }

            if (blankBO == null) {
                return false;
            }
            // 进行拆分
            String content = blankBO.getContent();
            List<String> separatorContent = getSeparatorContent(content, htmlQuestBOList, maxSeq);
            String answer = blankBO.getAnswer();
            List<String> separatorAnswer = getSeparatorContent(answer, htmlQuestBOList, maxSeq);
            String analysis = blankBO.getAnalysis();
            List<String> separatorAnalysis = getSeparatorContent(analysis, htmlQuestBOList, maxSeq);

            if (separatorContent.size() != separatorAnswer.size() || separatorAnswer.size() != separatorAnalysis.size()) {
                log.error("分离填空题的时候，分析，内容，答案数量不一致");
                return false;
            }

            double score = blankBO.getScore() / separatorContent.size();
            List<HtmlPaperBO.HtmlQuestBO> htmlQuestBlank = new ArrayList<>();
            for (int i = 0; i < separatorContent.size(); i++) {
                HtmlPaperBO.HtmlQuestBO htmlQuestBO = HtmlPaperBO.HtmlQuestBO.builder()
                        .allContent(blankBO.getAllContent())
                        .content(separatorContent.get(i))
                        .answer(separatorAnswer.get(i))
                        .analysis(separatorAnalysis.get(i))
                        .qseq(String.valueOf(Integer.parseInt(blankBO.getQseq()) + i))
                        .score(score).build();
                htmlQuestBlank.add(htmlQuestBO);
            }
            htmlQuestBOList.addAll(htmlQuestBOList.indexOf(blankBO), htmlQuestBlank);
            htmlQuestBOList.remove(blankBO);
        }
        return true;
    }

    /**
     * 获取合并填空题，对其进行分离成多道题
     *
     * @param content         合并题的内容/解析/答案
     * @param htmlQuestBOList
     * @param maxSeq          最大题号
     * @return
     */
    public List<String> getSeparatorContent(String content, List<HtmlPaperBO.HtmlQuestBO> htmlQuestBOList, String maxSeq) {
        content = content.replace("</p>", "</p>" + separator).replaceAll("<br[ ]*?/>", separator);
        String[] split = content.split(separator);
        List<String> seqContent = new ArrayList<>();
        if (htmlQuestBOList.size() + split.length - 1 == Integer.parseInt(maxSeq)) {
            seqContent.addAll(Arrays.asList(split));
        } else {
            StringBuilder c = new StringBuilder();
            for (String s : split) {
                String cleanContent = Jsoup.clean(s, Whitelist.none());
                Matcher matcher = Pattern.compile("^\\\\\\(\\((\\d+.*?)\\)\\\\\\)", Pattern.DOTALL).matcher(cleanContent.trim());
                if (matcher.find()) {
                    if (StringUtils.isNotBlank(c.toString())) {
                        seqContent.add(c.toString());
                    }
                    c = new StringBuilder(s);
                } else {
                    c.append(s);
                }
            }
            if (StringUtils.isNotBlank(c.toString())) {
                seqContent.add(c.toString());
            }
        }
        return seqContent;
    }

    /**
     * 从json中解析出对应的对象
     *
     * @param htmlZipFile            文件
     * @param picMap                 图片
     * @param tempClassTopicInfoDTOs json list
     * @param examInfo               json
     * @return
     */
    public HtmlPaperBO getHtmlPaperBOList(File htmlZipFile, Map<String, byte[]> picMap,
                                          List<String> tempClassTopicInfoDTOs, List<String> examInfo) {

        List<HtmlPaperBO.HtmlQuestBO> htmlQuestBOList = new ArrayList<>();
        for (String tempClassTopicInfoDTO : tempClassTopicInfoDTOs) {
            HtmlPaperBO.HtmlQuestBO htmlQuestBO = JSONObject.parseObject(tempClassTopicInfoDTO, HtmlPaperBO.HtmlQuestBO.class);
            String allContent = htmlQuestBO.getAllContent();
            HtmlPaperBO.HtmlQuestBO htmlQuestBO1 = JSONObject.parseObject(allContent, HtmlPaperBO.HtmlQuestBO.class);
            copyPropertiesIgnoreNull(htmlQuestBO1, htmlQuestBO);
            htmlQuestBOList.add(htmlQuestBO);
        }
        HtmlPaperBO htmlPaperBO = JSONObject.parseObject(examInfo.get(0), HtmlPaperBO.class);
        htmlPaperBO.setPath(htmlZipFile.getAbsolutePath());
        htmlPaperBO.setImgMap(picMap);
        htmlPaperBO.setHtmlQuestBOList(htmlQuestBOList);
        return htmlPaperBO;
    }

    /**
     * 获取为null的属性
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {

        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 复制属性，忽略源对象为null的属性
     *
     * @param src
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * 解析出html里面的对应的json数组
     *
     * @param document html
     * @param varName  js中var变量的值
     * @return
     */
    public List<String> getHtmlContentFromJson(Document document, String varName) {
        // 获取脚本
        Elements scripts = document.getElementsByTag("script");
        for (Element script : scripts) {
            // 获取指定变量的值
            Matcher matcher = Pattern.compile("var[ ]+" + varName + "[ ]+=(.*);").matcher(script.data());
            if (matcher.find()) {
                String text = matcher.group(1).trim();
                if (JSONObject.isValidArray(text)) {
                    JSONArray jsonArray = JSONObject.parseArray(text);
                    List<String> arrayLists = jsonArray.toJavaList(String.class);
                    if (CollectionUtils.isNotEmpty(arrayLists)) {
                        return arrayLists;
                    }
                }
                if (JSONObject.isValid(text)) {
                    text = StringUtils.removeStart(text, "'");
                    text = StringUtils.removeEnd(text, "'");
                    text = text.replace("{", "{\"").replace("=", "\":\"")
                            .replaceAll(",[ ]*", "\",\"").replace("}", "\"}");
                    String s = JSONObject.parseObject(text).toJavaObject(String.class);
                    if (StringUtils.isNotBlank(s)) {
                        return Collections.singletonList(s);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 读取zip压缩文件
     *
     * @param htmlZipFile 原始文件
     * @param picMap      图片
     * @return
     * @throws IOException
     */
    public InputStream readPaperFile(File htmlZipFile, Map<String, byte[]> picMap) throws IOException {
        InputStream htmlStream = null;
        ZipFile zipFile = new ZipFile(htmlZipFile, Charset.forName("GBK"));
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            if (!zipEntry.isDirectory() && FilenameUtils.getExtension(zipEntry.getName()).equals("html")) {
                htmlStream = inputStream;
            }
            // TODO 暂使用png固定
            if (!zipEntry.isDirectory() && FilenameUtils.getExtension(zipEntry.getName()).equals("png")) {
                picMap.put(zipEntry.getName(), IOUtils.toByteArray(inputStream));
            }
        }
        return htmlStream;
    }


    public byte[] createSmallWord(Map<String, byte[]> imgMap, String... content) throws IOException {

        String text = String.join("\n", content);
        // 匹配公式
        text = text.replaceAll("\\$(.*?)\\$", "<latex>$1</latex>");
        // 需要去掉js中的转义
//        text = StringEscapeUtils.unescapeEcmaScript(text);
        byte[] bytes = HtmlToWord.resolveHtml(text, imgMap);
        File docx = new File("F:\\工作记录及文件及成果\\工作记录（2020）\\20200114-html试卷\\" + 2 + ".docx");
        FileUtils.writeByteArrayToFile(docx, bytes, false);
        return bytes;
    }
}
