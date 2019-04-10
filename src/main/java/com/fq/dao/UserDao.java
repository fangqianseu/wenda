package com.fq.dao;

import com.fq.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    String TABLE_NAME = "user";
    String INSET_FIELDS = " name, password, salt ";
    String SELECT_FIELDS = " id, name, password, salt";

    @Insert({"insert ", TABLE_NAME, " (", INSET_FIELDS, ") values " +
            "(#{name},#{password},#{salt})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id = {#id}"})
    User selectUserById(int id);


}
