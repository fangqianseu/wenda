package com.fq.dao;

import com.fq.model.Agreement;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AgreementDao {
    String TABLE_NAME = " agreement ";
    String INSERT_FIELDS = " user_id, entity_id, entity_type, status, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId}, #{entityId}, #{entityType}, #{status},#{createdDate})"})
    int addAgreement(Agreement agreement);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where user_id= #{userId} and " +
            "entity_id=#{entityId} and entity_type=#{entityType}"})
    Agreement selectAgreement(@Param("userId") int userId,
                              @Param("entityId") int entityId,
                              @Param("entityType") int entityType);

    @Update({"update ", TABLE_NAME, " set status = #{status}, created_date=#{createdDate}  where id = #{id}"})
    int updateAgreementStatus(Agreement agreement);

    @Select({"select count(id) from ", TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType} and status = 0"})
    int getAgreementCount(@Param("entityId") int entityId, @Param("entityType") int entityType);
}
