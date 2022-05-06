package com.yfh.qqzone.dao.impl;

import com.yfh.myssm.basedao.BaseDAO;
import com.yfh.qqzone.dao.HostReplyDAO;
import com.yfh.qqzone.pojo.HostReply;

public class HostReplyDAOImpl extends BaseDAO<HostReply> implements HostReplyDAO {


    @Override
    public HostReply getHostReplyByReplyId(Integer replyId) {
        return super.load("SELECT * FROM t_host_reply WHERE reply = ?", replyId);
    }

    @Override
    public int delHostReplyById(Integer id) {
        return super.executeUpdate("DELETE FROM t_host_reply WHERE id=?", id);
    }


}
