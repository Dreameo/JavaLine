package com.yfh.qqzone.controller;

import com.yfh.qqzone.pojo.HostReply;
import com.yfh.qqzone.pojo.Reply;
import com.yfh.qqzone.pojo.Topic;
import com.yfh.qqzone.pojo.UserBasic;
import com.yfh.qqzone.service.HostReplyService;
import com.yfh.qqzone.service.ReplyService;
import com.yfh.qqzone.service.TopicService;

import javax.servlet.http.HttpSession;
import java.util.List;

public class TopicController {
    TopicService topicService = null;


    /**
     * 1.根据topic id查找topic详情
     * 2.获取所有关联的回复信息
     * 3. 如果某个回复有主人回复，需要查询出来
     */
    public String topicDetail(Integer id, HttpSession session) {
        Topic topic = topicService.getTopicById(id); // 根据topic id 获取topic的详情
        session.setAttribute("topic", topic);
        return "frames/detail";

    }

    /**
     * 删除整条topic
     */

    public String delTopic(Integer topicId) {
        topicService.delTopic(topicId);
        return "redirect:topic.do?operation=topicList";
    }

    /**
     * topicList
     */
    public String topicList(HttpSession session) {
        UserBasic userBasic = (UserBasic) session.getAttribute("UserBasic");
        List<Topic> topicList = topicService.getTopicList(userBasic);
        userBasic.setTopicList(topicList);
        session.setAttribute("friend", userBasic);
        return "/frames/main";
    }
}
