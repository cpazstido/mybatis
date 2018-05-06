package com.cf.sitemap;

import java.io.IOException;
import java.util.Queue;

public class CrawlerMain {
    /**
     * @param args
     */
    public static void main(String[] args) {
        // 网站的基础地址
        String baseUrl = "http://iscremled.com/";
        // 爬去地址
        HtmlLinkCrawler crawler = new HtmlLinkCrawler(baseUrl);
        // 执行抓取
        crawler.execute();
        // 获取到所有地址
        Queue<String> linkUrlQueue = crawler.getLinkUrlQueue();
        // 打印站内链接地址，并输出到sitemap.txt文件
        String file = "d://sitemap.txt";
        for(String linkUrl : linkUrlQueue){
            System.out.println(linkUrl);
            try {
                FileUtil.writeln(file, linkUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
