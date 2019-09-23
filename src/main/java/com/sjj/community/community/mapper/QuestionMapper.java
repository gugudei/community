package com.sjj.community.community.mapper;

import com.sjj.community.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
     void create(Question question);
    @Select("select * from question")
    List<Question> list();

    @Select("select * from question where creator=#{userId}")
    List<Question> listByUserID(@Param("userId") Integer userId);
    @Select("select * from question where id=#{id}")
    Question getById(@Param("id")Integer id);
    @Update("update question set title =#{title},description=#{description}," +
            "gmt_modified=#{gmt_modified},tag=#{tag} where id=#{id}")
    void update(Question question);
    @Update("update question set view_count = #{viewCount} where id=#{id}")
    void updateView(Question updateQuestion);
}
