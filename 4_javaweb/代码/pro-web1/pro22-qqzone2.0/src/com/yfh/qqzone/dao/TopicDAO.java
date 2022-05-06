package com.yfh.qqzone.dao;

import com.yfh.qqzone.pojo.Topic;
import com.yfh.qqzone.pojo.UserBasic;

import java.util.List;

public interface TopicDAO {

    // 获取指定用户的日志列表
    List<Topic> getTopicList(UserBasic userBasic);

    // 添加日志
    void addTopic(Topic topic);

    // 删除日志
    int delTopic(Topic topic);

    // 获取特定的日志信息
    Topic getTopic(Integer id);
}
