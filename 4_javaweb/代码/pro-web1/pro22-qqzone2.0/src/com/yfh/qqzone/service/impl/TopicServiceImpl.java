package com.yfh.qqzone.service.impl;

import com.yfh.qqzone.dao.TopicDAO;
import com.yfh.qqzone.pojo.Reply;
import com.yfh.qqzone.pojo.Topic;
import com.yfh.qqzone.pojo.UserBasic;
import com.yfh.qqzone.service.ReplyService;
import com.yfh.qqzone.service.TopicService;
import com.yfh.qqzone.service.UserBasicService;

import java.util.List;

public class TopicServiceImpl implements TopicService {
    TopicDAO topicDAO = null;
    ReplyService replyService = null;
    UserBasicService userBasicService = null;

    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        return topicDAO.getTopicList(userBasic);
    }

    @Override
    public Topic getTopicById(Integer id) {
        Topic topic = topicDAO.getTopic(id); // 根据用户id获取topic详情
        UserBasic author = userBasicService.getUserBasicById(topic.getAuthor().getId());
        topic.setAuthor(author);
        List<Reply> replyList = replyService.getReplyList(topic);
        topic.setReplyList(replyList);
        return topic;
    }

    @Override
    public void delTopic(Integer id) { // 删除topic,根据topic id查找所有reply，删除replyList-->删除hostReply
        Topic topic = topicDAO.getTopic(id);
        replyService.delReplyList(topic);
        topicDAO.delTopic(topic);
    }
}
