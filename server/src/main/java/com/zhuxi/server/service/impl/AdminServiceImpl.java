package com.zhuxi.server.service.impl;

import com.zhuxi.pojo.entity.Admin;
import com.zhuxi.server.helper.AdminMapperHelper;
import com.zhuxi.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    private AdminMapperHelper adminMapperHelper;
    
    /**
     * 插入管理员
     */
    @Override
    public int insert(Admin admin) {
        return adminMapperHelper.insert(admin);
    }
    
    /**
     * 根据ID删除管理员
     */
    @Override
    public int deleteById(Long id) {
        return adminMapperHelper.deleteById(id);
    }
    
    /**
     * 更新管理员
     */
    @Override
    public int update(Admin admin) {
        return adminMapperHelper.update(admin);
    }
    
    /**
     * 根据ID查询管理员
     */
    @Override
    public Admin selectById(Long id) {
        return adminMapperHelper.selectById(id);
    }
    
    /**
     * 根据用户名查询管理员
     */
    @Override
    public Admin selectByUsername(String username) {
        return adminMapperHelper.selectByUsername(username);
    }
    
    /**
     * 根据身份证号查询管理员
     */
    @Override
    public Admin selectByIdNumber(String idNumber) {
        return adminMapperHelper.selectByIdNumber(idNumber);
    }
    
    /**
     * 查询所有管理员
     */
    @Override
    public List<Admin> selectAll() {
        return adminMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询管理员列表
     */
    @Override
    public List<Admin> selectByCondition(Admin admin) {
        return adminMapperHelper.selectByCondition(admin);
    }
}
