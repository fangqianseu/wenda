/*
Date: 04/14,2019, 10:14
*/
package com.fq.service;

import com.fq.dao.FeedDao;
import com.fq.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FeedService {
    @Autowired
    private FeedDao feedDao;

    public boolean addFeed(Feed feed) {
        feedDao.addFeed(feed);
        return feed.getId() > 0;
    }

    public List<Feed> selectFeedsByUserIds(List<Integer> userIds, int offset, int limit) {
        return feedDao.selectFeedsByUserIds(userIds, offset, limit);
    }

    public List<Feed> selectFeedsNew(int offset, int limit) {
        return feedDao.selectFeedsNew(offset, limit);
    }
}
