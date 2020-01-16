package cn.xxt.quest.imports.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.HtmlPaperApplication;
import sun.service.HtmlPaperService;

import java.io.File;
import java.io.IOException;

@SpringBootTest(classes = HtmlPaperApplication.class)
@RunWith(SpringRunner.class)
public class HtmlPaperService1Test {

//    @Autowired
//    private HtmlPaperService1 htmlPaperService1;

    @Autowired
    private HtmlPaperService htmlPaperService;

    @Test
    public void f() throws Exception {
        String path = "F:\\工作记录及文件及成果\\工作记录（2020）\\20200107-word相关\\羊山中学2019-2020学年度七年级上期期末考试.zip";
        File file = new File(path);
        htmlPaperService.parseHtmlPaper(file);
    }

    @Test
    public void f1() throws Exception {
        String path = "羊山中学2019-2020学年度七年级上期期末考试.zip";
        File file = new File(path);
        htmlPaperService.parseHtmlPaper(file);
    }

}