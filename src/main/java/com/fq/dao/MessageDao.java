/*
Date: 04/12,2019, 08:41
*/
package com.fq.dao;

import com.fq.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageDao {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where conversation_id=#{conversationId} order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getMessagebyconversationId(@Param("conversationId") String conversationId,
                                             @Param("offset") int offset,
                                             @Param("limit") int limit);

    @Select({"SELECT", INSERT_FIELDS, ", tt.id FROM " + TABLE_NAME +
            ", (SELECT COUNT(id) AS id ,conversation_id as cid, MAX(created_date) AS max_data " +
            "FROM message WHERE from_id= #{userId} OR to_id= #{userId} GROUP BY conversation_id)tt " +
            "WHERE conversation_id = cid AND created_date  = max_data ORDER BY created_date DESC"})
    List<Message> getConversationListByUserId(@Param("userId") int userId,
                                              @Param("offset") int offset,
                                              @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getMessageUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    @Update({"update ", TABLE_NAME, " set has_read = #{status} where id=#{id}"})
    int updateUnread(@Param("id") int id, @Param("status") int status);
}
