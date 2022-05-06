package com.yfh.qqzone.service;

import com.yfh.qqzone.pojo.HostReply;

public interface HostReplyService {

    // 根据replyid获取hostreply
    HostReply getHostReplyByReplyId(Integer replyId);

    // 根据replyid删除hostreply
    void delHostReplyById(Integer replyId);
}
