package com.yfh.qqzone.service;

import com.yfh.qqzone.pojo.Reply;
import com.yfh.qqzone.pojo.Topic;

import java.util.List;

public interface ReplyService {

    //根据topic获取所有的reply
    List<Reply> getReplyList(Topic topic);

    // 添加回复
    void addReply(Reply reply);

    // 删除回复
    void delReply(Integer replyId);

    // 删除Reply列表
    void delReplyList(Topic topic);

}
