package com.yfh.qqzone.service;

import com.yfh.qqzone.pojo.Topic;
import com.yfh.qqzone.pojo.UserBasic;

import java.util.List;

public interface TopicService {
    // 获取topic列表
    List<Topic> getTopicList(UserBasic userBasic);
}
