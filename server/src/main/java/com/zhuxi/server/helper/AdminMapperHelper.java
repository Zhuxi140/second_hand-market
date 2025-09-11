package com.zhuxi.server.helper;

import com.zhuxi.pojo.entity.Admin;
import com.zhuxi.server.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminMapperHelper {
    
    @Autowired
    private AdminMapper adminMapper;
    
    /**
     * 插入管理员
     */
    public int insert(Admin admin) {
        return adminMapper.insert(admin);
    }
    
    /**
     * 根据ID删除管理员
     */
    public int deleteById(Long id) {
        return adminMapper.deleteById(id);
    }
    
    /**
     * 更新管理员
     */
    public int update(Admin admin) {
        return adminMapper.update(admin);
    }
    
    /**
     * 根据ID查询管理员
     */
    public Admin selectById(Long id) {
        return adminMapper.selectById(id);
    }
    
    /**
     * 根据用户名查询管理员
     */
    public Admin selectByUsername(String username) {
        return adminMapper.selectByUsername(username);
    }
    
    /**
     * 根据身份证号查询管理员
     */
    public Admin selectByIdNumber(String idNumber) {
        return adminMapper.selectByIdNumber(idNumber);
    }
    
    /**
     * 查询所有管理员
     */
    public List<Admin> selectAll() {
        return adminMapper.selectAll();
    }
    
    /**
     * 根据条件查询管理员列表
     */
    public List<Admin> selectByCondition(Admin admin) {
        return adminMapper.selectByCondition(admin);
    }
}
