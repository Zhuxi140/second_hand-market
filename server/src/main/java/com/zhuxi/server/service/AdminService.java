package com.zhuxi.server.service;

import com.zhuxi.pojo.entity.Admin;

import java.util.List;

public interface AdminService {
    
    /**
     * 插入管理员
     */
    int insert(Admin admin);
    
    /**
     * 根据ID删除管理员
     */
    int deleteById(Long id);
    
    /**
     * 更新管理员
     */
    int update(Admin admin);
    
    /**
     * 根据ID查询管理员
     */
    Admin selectById(Long id);
    
    /**
     * 根据用户名查询管理员
     */
    Admin selectByUsername(String username);
    
    /**
     * 根据身份证号查询管理员
     */
    Admin selectByIdNumber(String idNumber);
    
    /**
     * 查询所有管理员
     */
    List<Admin> selectAll();
    
    /**
     * 根据条件查询管理员列表
     */
    List<Admin> selectByCondition(Admin admin);
}

