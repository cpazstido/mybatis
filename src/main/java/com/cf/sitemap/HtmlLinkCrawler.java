package com.cf.sitemap;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.LinkedList;
import java.util.Queue;

public class HtmlLinkCrawler {
    // htmlParser解析器
    private Parser parser = new Parser();
    // 链接Filter过滤器
    private NodeClassFilter linkFilter = new NodeClassFilter(LinkTag.class);
    // 网站地图容器
    private Queue<String> queue = new LinkedList<String>();
    // 网址
    private String baseUrl;

    public HtmlLinkCrawler(String baseUrl){
        this.baseUrl = baseUrl;
        // 添加到容器
        this.queue.offer(baseUrl);
    }

    public void execute(){
        // 从首页地址开始抓取
        crawl(baseUrl);
    }

    /**
     * 根据URL抓取
     *
     * @param url
     */
    private void crawl(String url){
        try {
            parser.setURL(url);
            // 解析器解析
            NodeList list = parser.parse(linkFilter);
            // 遍历器
            NodeIterator iter = list.elements();
            while(iter.hasMoreNodes()){
                // 获取到页面链接标签
                LinkTag linkTag = (LinkTag) iter.nextNode();
                // 页面链接地址
                String linkUrl =linkTag.getLink();
                if(checkStationUrl(linkUrl) && noContains(linkUrl)){
                    queue.offer(linkUrl);
                    this.crawl(linkUrl);
                }
            }

        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否为站内地址
     *
     * @param link
     * @return
     */
    private boolean checkStationUrl(String link){
        return link!=null ? link.startsWith(this.baseUrl) : false;
    }


    /**
     * 是否包含该地址
     *
     * @param link
     * @return
     */
    private boolean noContains(String link){
        // 如果链接最后有 "#" 符号出现
        if(link.endsWith("#")){
            // 去掉#号
            link = link.substring(0, link.length()-1);
        }
        return !queue.contains(link);
    }

    /**
     * 获取到所有的站内容链接地址
     *
     * @return
     */
    public Queue<String> getLinkUrlQueue(){
        return this.queue;
    }
}
