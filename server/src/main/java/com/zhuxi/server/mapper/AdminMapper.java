package com.zhuxi.server.mapper;

import com.zhuxi.pojo.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    // 插入管理员
    @Insert("INSERT INTO admin (role, name, username, password, phone, gender, id_number, status, create_id, update_id, created_at, updated_at) " +
            "VALUES (#{role}, #{name}, #{username}, #{password}, #{phone}, #{gender}, #{idNumber}, #{status}, #{createId}, #{updateId}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Admin admin);
    
    // 根据ID删除管理员
    @Delete("DELETE FROM admin WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    // 更新管理员
    @Update("UPDATE admin SET role = #{role}, name = #{name}, username = #{username}, password = #{password}, " +
            "phone = #{phone}, gender = #{gender}, id_number = #{idNumber}, status = #{status}, " +
            "create_id = #{createId}, update_id = #{updateId}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Admin admin);
    
    // 根据ID查询管理员
    @Select("SELECT * FROM admin WHERE id = #{id}")
    Admin selectById(@Param("id") Long id);
    
    // 根据用户名查询管理员
    @Select("SELECT * FROM admin WHERE username = #{username}")
    Admin selectByUsername(@Param("username") String username);
    
    // 根据身份证号查询管理员
    @Select("SELECT * FROM admin WHERE id_number = #{idNumber}")
    Admin selectByIdNumber(@Param("idNumber") String idNumber);
    
    // 查询所有管理员
    @Select("SELECT * FROM admin")
    List<Admin> selectAll();
    
    // 根据条件查询管理员列表（复杂查询用XML）
    List<Admin> selectByCondition(Admin admin);
}
