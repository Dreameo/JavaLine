package com.yfh.qqzone.dao;

import com.yfh.qqzone.pojo.HostReply;

public interface HostReplyDAO {
    // 根据reply id查询对应的hostreply
    HostReply getHostReplyByReplyId(Integer replyId);

    // 根据 reply id 删除hostreply
    int delHostReplyById(Integer replyId);
}
