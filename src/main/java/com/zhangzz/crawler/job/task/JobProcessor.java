package com.zhangzz.crawler.job.task;

import com.zhangzz.crawler.job.pojo.JobInfo;
import com.zhangzz.crawler.job.util.MathSalary;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @Author zhangzz
 * @Create 2019/5/12 16:33
 */
@Component
public class JobProcessor implements PageProcessor {

    @Autowired
    private JobPipeline jobPipeline;
    private String url = "https://search.51job.com/list/000000,000000,0000,00,9,99,java,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
    private Site site = Site.me()
            .setCharset("gbk")
            .setTimeOut(10 * 1000)
            .setRetrySleepTime(3000)
            .setRetryTimes(3);

    @Override
    public void process(Page page) {
        // 解析页面，获取招聘详情的url地址
        Html html = page.getHtml();
        List<Selectable> list = html.css("div#resultList div.el").nodes();
        if (list.isEmpty()) {
            // 为空，是招聘详情页
            saveJobInfo(page);
        } else {
            // 不为空，列表页，解析出url地址
            for (Selectable selectable : list) {
                String jobInfoUrl = selectable.links().toString();
                // 将url放入任务队列
                page.addTargetRequest(jobInfoUrl);
            }
            // 获取下一页的url
            String bkUrl = html.css("div#resultList div.dw_page li.bk").nodes().get(1).links().toString();
            page.addTargetRequest(bkUrl);
        }
    }

    /**
     * 解析招聘详情页，保存招聘信息详情
     * @param page 页面信息
     */
    private void saveJobInfo(Page page) {
        Html html = page.getHtml();
        JobInfo jobInfo = new JobInfo();
        jobInfo.setCompanyName(html.css("a.catn", "text").toString());
        String s = html.css("body > div.tCompanyPage > div.tCompany_center.clearfix > div.tHeader.tHjob > div > div.cn > p.msg.ltype", "title").toString();
        jobInfo.setCompanyAddr(s.substring(0, s.indexOf("|") - 2));
        jobInfo.setCompanyInfo(html.xpath("/html/body/div[3]/div[2]/div[3]/div[3]/div/text()").toString());
        jobInfo.setJobName(html.css("body > div.tCompanyPage > div.tCompany_center.clearfix > div.tHeader.tHjob > div > div.cn > h1", "title").toString());
        jobInfo.setJobAddr(html.xpath("/html/body/div[3]/div[2]/div[3]/div[2]/div/p/text()").toString());
        jobInfo.setJobInfo(Jsoup.parse(html.css("body > div.tCompanyPage > div.tCompany_center.clearfix > div.tCompany_main > div:nth-child(1) > div").toString()).text());
        Integer[] salary = MathSalary.getSalary(html.css("div.cn strong", "text").toString());
        jobInfo.setSalaryMin(salary[0]);
        jobInfo.setSalaryMax(salary[1]);
        jobInfo.setUrl(page.getUrl().toString());
        jobInfo.setTime(s.substring(s.lastIndexOf("|") + 3, s.length() - 2));

        // 保存结果
        page.putField("jobInfo", jobInfo);
    }

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * initialDelay 当任务启动后，等待多久执行方法
     * fixedDelay 没隔多久执行一次方法
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 100 * 1000)
    public void process() {
        Spider.create(new JobProcessor())
                .addUrl(url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(100)
                .addPipeline(jobPipeline)
                .run();
    }
}
