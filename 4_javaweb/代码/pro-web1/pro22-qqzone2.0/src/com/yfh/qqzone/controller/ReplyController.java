package com.yfh.qqzone.controller;

import com.yfh.qqzone.pojo.Reply;
import com.yfh.qqzone.pojo.Topic;
import com.yfh.qqzone.pojo.UserBasic;
import com.yfh.qqzone.service.ReplyService;

import javax.servlet.http.HttpSession;
import java.util.Date;

public class ReplyController {
    private ReplyService replyService;
    
    public String addReply(String content, Integer topicId, HttpSession session) {
        Object author = session.getAttribute("UserBasic");
        Reply reply = new Reply(content, new Date(), (UserBasic) author, new Topic(topicId));
        replyService.addReply(reply);
        // detail页面--> 重新查一次
        return "redirect:topic.do?operation=topicDetail&id="+topicId;

    }

    public String delReply(Integer replyId, Integer topicId) {
        /**
         * 如果存在主人回复，先删除主人回复，再删除回复内容
         * 如果不存在主人回复，那么直接删除
         */
        replyService.delReply(replyId);

        return "redirect:topic.do?operation=topicDetail&id=" + topicId;

    }
}
