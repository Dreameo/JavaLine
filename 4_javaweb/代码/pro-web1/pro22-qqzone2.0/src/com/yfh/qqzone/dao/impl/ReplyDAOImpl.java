package com.yfh.qqzone.dao.impl;

import com.yfh.myssm.basedao.BaseDAO;
import com.yfh.qqzone.dao.ReplyDAO;
import com.yfh.qqzone.pojo.Reply;
import com.yfh.qqzone.pojo.Topic;

import java.util.List;

public class ReplyDAOImpl extends BaseDAO<Reply> implements ReplyDAO {
    @Override
    public List<Reply> getReplyList(Topic topic) {
        return super.executeQuery("SELECT * FROM t_reply WHERE topic = ?", topic.getId());
    }

    @Override
    public int addReply(Reply reply) {
        return super.executeUpdate("INSERT INTO t_reply(id, content, replyDate, author, topic) VALUES(0,?,?,?,?)",reply.getContent(),
                reply.getReplyDate(), reply.getAuthor().getId(), reply.getTopic().getId());
    }

    @Override
    public int delReply(Integer replyId) {
        return super.executeUpdate("DELETE FROM t_reply WHERE id = ?", replyId);
    }

    @Override
    public Reply getReplyById(Integer replyId) {
        return super.load("SELECT * FROM t_reply WHERE id = ?", replyId);
    }
}
