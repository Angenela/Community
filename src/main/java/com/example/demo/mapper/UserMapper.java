package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Mapper
@Repository     //该注解是告诉Spring，让Spring创建一个名字叫“UserMapper”的实例
// 当Service需要使用Spring创建的名字叫“UserMapper”的UserMapperImpl实例时，
// 就可以使用@Resource(name = "UserMapper")注解告诉Spring，Spring把创建好的UserMapper注入给Service即可。
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);     //参数为类的对象时会将对象的属性名与#{}中一样的注入值

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);       //非对象时需要@Param
}
