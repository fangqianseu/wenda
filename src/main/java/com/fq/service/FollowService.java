/*
Date: 04/12,2019, 15:07
*/
package com.fq.service;

import com.fq.dao.FollowDao;
import com.fq.model.Follow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FollowService {
    private static Logger logger = LoggerFactory.getLogger(FollowService.class);

    @Autowired
    private FollowDao followDao;


    @Transactional(rollbackFor = Exception.class)
    public void follow(Follow follow) {
        Follow temp = followDao.selectFollow(follow.getUserId(), follow.getEntityId(), follow.getEntityType());
        if (temp == null) {
            followDao.addFollow(follow);
        } else {
            follow.setId(temp.getId());
            followDao.updateFollowStatus(follow);
        }
    }

    public boolean isFollow(int userId, int entityId, int entityType) {
        Follow follow = followDao.selectFollow(userId, entityId, entityType);
        if (follow == null || follow.getStatus() == 1)
            return false;
        else
            return true;
    }

    /**
     * 返回 实体 的粉丝数
     *
     * @param entityId
     * @param entityType
     * @return
     */
    public int getFollowersCount(int entityId, int entityType) {
        return followDao.getFollowersCount(entityId, entityType);
    }

    /**
     * 返回 实体 的 粉丝
     *
     * @param entityId
     * @param entityType
     * @return
     */
    public List<Follow> getFollowers(int entityId, int entityType) {
        return followDao.selectFollowers(entityId, entityType);
    }

    /**
     * 返回用户 关注的 实体数
     *
     * @param userId
     * @param entityType
     * @return
     */
    public int getFolloweesCount(int userId, int entityType) {
        return followDao.getFolloweesCount(userId, entityType);
    }

    /**
     * 返回 用户 关注的 实体
     *
     * @param userId
     * @param entityType
     * @return
     */
    public List<Follow> getFollowees(int userId, int entityType) {
        return followDao.selectFollowees(userId, entityType);
    }
}
