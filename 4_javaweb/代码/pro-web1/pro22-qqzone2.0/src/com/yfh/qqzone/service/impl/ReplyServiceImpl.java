package com.yfh.qqzone.service.impl;

import com.yfh.qqzone.dao.ReplyDAO;
import com.yfh.qqzone.pojo.HostReply;
import com.yfh.qqzone.pojo.Reply;
import com.yfh.qqzone.pojo.Topic;
import com.yfh.qqzone.pojo.UserBasic;
import com.yfh.qqzone.service.HostReplyService;
import com.yfh.qqzone.service.ReplyService;
import com.yfh.qqzone.service.UserBasicService;

import java.util.List;

public class ReplyServiceImpl implements ReplyService {
    private ReplyDAO replyDAO = null;
    HostReplyService hostReplyService = null;
    UserBasicService userBasicService = null;

    @Override
    public List<Reply> getReplyList(Topic topic) {
        List<Reply> replyList = replyDAO.getReplyList(topic);
        for (int i = 0; i < replyList.size(); i++) {
            Reply reply = replyList.get(i);
            UserBasic author = userBasicService.getUserBasicById(reply.getAuthor().getId());
            reply.setAuthor(author);
            HostReply hostReply = hostReplyService.getHostReplyByReplyId(reply.getId());
            reply.setHostReply(hostReply);
        }
        return replyList;
    }

    @Override
    public void addReply(Reply reply) {
        replyDAO.addReply(reply);
    }

    @Override
    public void delReply(Integer replyId) {
        // 1. 先根据id获取到reply
        // 2. 如果reply中hostReply不为空，则先要删除hostReply
        // 3. 删除reply
        Reply reply = replyDAO.getReplyById(replyId);
        HostReply hostReply = hostReplyService.getHostReplyByReplyId(replyId);
        if (hostReply != null)
            hostReplyService.delHostReplyById(hostReply.getId());


        replyDAO.delReply(replyId);
    }

    @Override
    public void delReplyList(Topic topic) {
        List<Reply> replyList = replyDAO.getReplyList(topic);
        for (Reply reply : replyList) {
            delReply(reply.getId());
        }
    }
}
