package com.yfh.qqzone.service;

import com.yfh.qqzone.pojo.Topic;
import com.yfh.qqzone.pojo.UserBasic;

import java.util.List;

public interface TopicService {
    // 获取topic列表
    List<Topic> getTopicList(UserBasic userBasic);

    // 根据topic id查找topic详情
    Topic getTopicById(Integer id);

    // 删除topic根据id
    void delTopic(Integer id);


}
