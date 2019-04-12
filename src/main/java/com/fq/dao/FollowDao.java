package com.fq.dao;

import com.fq.model.Follow;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FollowDao {
    String TABLE_NAME = " follows ";
    String INSERT_FIELDS = " user_id, entity_id, entity_type, status, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId}, #{entityId}, #{entityType}, #{status},#{createdDate})"})
    int addFollow(Follow follow);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where user_id= #{userId} and " +
            "entity_id=#{entityId} and entity_type=#{entityType}"})
    Follow selectFollow(@Param("userId") int userId,
                        @Param("entityId") int entityId,
                        @Param("entityType") int entityType);

    @Update({"update ", TABLE_NAME, " set status = #{status}, created_date=#{createdDate}  where id = #{id}"})
    int updateFollowStatus(Follow follow);

    //粉丝数
    @Select({"select count(id) from ", TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType} and status = 0"})
    int getFollowersCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    //关注数
    @Select({"select count(id) from ", TABLE_NAME,
            " where user_id=#{userId} and entity_type=#{entityType} and status = 0"})
    int getFolloweesCount(@Param("userId") int userId, @Param("entityType") int entityType);


    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where " +
            "entity_id=#{entityId} and entity_type=#{entityType} and status = 0"})
    List<Follow> selectFollowers(@Param("entityId") int entityId,
                                 @Param("entityType") int entityType);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where " +
            "user_id=#{userId} and entity_type=#{entityType} and status = 0"})
    List<Follow> selectFollowees(@Param("userId") int userId,
                                 @Param("entityType") int entityType);



}
