package com.yfh.qqzone.controller;

import com.yfh.qqzone.pojo.Topic;
import com.yfh.qqzone.pojo.UserBasic;
import com.yfh.qqzone.service.TopicService;
import com.yfh.qqzone.service.UserBasicService;

import javax.servlet.http.HttpSession;
import java.util.List;

public class UserController {
    private UserBasicService userBasicService = null;
    private TopicService topicService = null;

    // 登录操作
    public String login(String loginId, String pwd, HttpSession session) {
        UserBasic userBasic = userBasicService.login(loginId, pwd);
        if (userBasic != null) {
            List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
            List<Topic> topicList = topicService.getTopicList(userBasic);
            userBasic.setFriendList(friendList);
            userBasic.setTopicList(topicList);
            /**
             * UserBasic和friendList保存的key一致，那么进入的是自己的空间，不然是进入好友的空间
             */
            session.setAttribute("UserBasic", userBasic);
            session.setAttribute("friend", userBasic);
//            session.setAttribute("friendList", friendList);
//            session.setAttribute("topicList", topicList);
            return "index";
        } else {
            return "login";
        }
    }

    /**
     * 进入别人的空间
     * 1. 根据id找到用户信息
     * 2.
     *
     * @return
     */
    public String friend(Integer id, HttpSession session) {
        UserBasic currFriend = userBasicService.getUserBasicById(id);
        List<Topic> friendTopic = topicService.getTopicList(currFriend);
        List<UserBasic> curFriFriends = userBasicService.getFriendList(currFriend);
        currFriend.setTopicList(friendTopic);
        currFriend.setFriendList(curFriFriends);
        session.setAttribute("friend", currFriend);
        return "index";

    }
}
