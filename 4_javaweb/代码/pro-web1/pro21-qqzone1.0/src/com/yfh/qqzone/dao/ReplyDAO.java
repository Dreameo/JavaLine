package com.yfh.qqzone.dao;

import com.yfh.qqzone.pojo.Reply;
import com.yfh.qqzone.pojo.Topic;

import java.util.List;

public interface ReplyDAO {
    // 1.查找指定日志的回复列表
    List<Reply> getReplyList(Topic topic);

    // 2. 添加回复
    void addReply(Reply reply);

    // 3. 删除回复
    void delReply(Reply reply);

}
