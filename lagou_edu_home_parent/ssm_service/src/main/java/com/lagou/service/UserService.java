package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.*;
import com.mysql.fabric.Response;

import java.util.List;

public interface UserService {
    public PageInfo findAllUserByPage(UserVo userVo);

    public User login(User user) throws Exception;

    public void userContextRole(UserVo userVo);

    public List<Role> findUserRelationRoleById(Integer id);

    public ResponseResult getUserPermissions(Integer userid);
}
