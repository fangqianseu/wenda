/*
Date: 04/14,2019, 09:38
*/
package com.fq.controller;

import com.fq.model.EntityType;
import com.fq.model.Feed;
import com.fq.model.Follow;
import com.fq.model.SessionHolder;
import com.fq.service.FeedService;
import com.fq.service.FollowService;
import com.fq.service.UserService;
import com.fq.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {
    private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
    @Autowired
    private SessionHolder sessionHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private FeedService feedService;
    @Autowired
    private FollowService followService;

    @RequestMapping(value = "pullfeeds", method = RequestMethod.GET)
    public String getfeeds(Model model) {
        int localUserId = sessionHolder.getUser() != null ? sessionHolder.getUser().getId() : WendaUtil.ANONYMOUS_USERID;

        ArrayList<Integer> userIdsFollowed = new ArrayList<>();
        if (localUserId != WendaUtil.ANONYMOUS_USERID) {
            List<Follow> followees = followService.getFollowees(localUserId, EntityType.ENTITY_USER);
            for (Follow follow : followees)
                userIdsFollowed.add(follow.getEntityId());

        }

        List<Feed> feeds = null;
        if (localUserId == WendaUtil.ANONYMOUS_USERID || userIdsFollowed.size() == 0) {
            feeds = feedService.selectFeedsNew(0, 10);
        } else {
            feeds = feedService.selectFeedsByUserIds(userIdsFollowed, 0, 10);
        }
        model.addAttribute("feeds", feeds);

        logger.info(feeds.get(0).get("content"));
        return "feeds";
    }

}
