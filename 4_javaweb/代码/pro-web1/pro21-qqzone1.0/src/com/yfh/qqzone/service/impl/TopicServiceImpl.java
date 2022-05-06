package com.yfh.qqzone.service.impl;

import com.yfh.qqzone.dao.TopicDAO;
import com.yfh.qqzone.pojo.Topic;
import com.yfh.qqzone.pojo.UserBasic;
import com.yfh.qqzone.service.TopicService;

import java.util.List;

public class TopicServiceImpl implements TopicService {
    TopicDAO topicDAO = null;

    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        return topicDAO.getTopicList(userBasic);
    }
}
