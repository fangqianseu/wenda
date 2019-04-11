/*
Date: 04/11,2019, 09:59
*/
package com.fq.dao;

import com.fq.model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

@Mapper
public interface LoginTicketDao {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = " user_id, expired, status, ticket ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({" insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values(#{userId}, #{expired}, #{status}, #{ticket})"})
    int addTicket(LoginTicket loginTicket);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket = #{ticket}"})
    LoginTicket getTicket(String ticket);

    @Update({"update ", TABLE_NAME, " set status = #{status} where ticket = #{ticket}"})
    int setStaut(@Param("ticket") String ticket,@Param("status") int status);
}
