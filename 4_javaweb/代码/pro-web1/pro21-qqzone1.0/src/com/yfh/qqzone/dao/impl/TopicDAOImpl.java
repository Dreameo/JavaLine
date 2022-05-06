package com.yfh.qqzone.dao.impl;

import com.yfh.myssm.basedao.BaseDAO;
import com.yfh.qqzone.dao.TopicDAO;
import com.yfh.qqzone.pojo.Topic;
import com.yfh.qqzone.pojo.UserBasic;

import java.util.List;

public class TopicDAOImpl extends BaseDAO<Topic> implements TopicDAO {
    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        String sql = "SELECT * FROM t_topic WHERE author = ?";
        return super.executeQuery(sql, userBasic.getId());
    }

    @Override
    public void addTopic(Topic topic) {

    }

    @Override
    public void delTopic(Topic topic) {

    }

    @Override
    public Topic getTopic(Integer id) {
        return null;
    }
}
