package com.yfh.qqzone.service.impl;

import com.yfh.qqzone.dao.HostReplyDAO;
import com.yfh.qqzone.pojo.HostReply;
import com.yfh.qqzone.service.HostReplyService;

public class HostReplyServiceImpl implements HostReplyService {
    private HostReplyDAO hostReplyDAO = null;
    @Override
    public HostReply getHostReplyByReplyId(Integer replyId) {
        return hostReplyDAO.getHostReplyByReplyId(replyId);
    }

    @Override
    public void delHostReplyById(Integer id) {
        hostReplyDAO.delHostReplyById(id);
    }
}
