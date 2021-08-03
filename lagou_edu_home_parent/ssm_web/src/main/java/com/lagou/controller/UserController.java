package com.lagou.controller;


import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVo;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVo userVo){
        PageInfo allUserByPage = userService.findAllUserByPage(userVo);

        return new ResponseResult(true,200,"分页多条件查询成功",allUserByPage);
    }


    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request) throws Exception {
        User login = userService.login(user);
        if (login != null){
            HttpSession session = request.getSession();
            String access_token = UUID.randomUUID().toString();
            session.setAttribute("access_token",access_token);
            session.setAttribute("user_id",login.getId());

            HashMap<String, Object> map = new HashMap<>();
            map.put("access_token",access_token);
            map.put("user_id",login.getId());
            map.put("user",login);


            return new ResponseResult(true,1,"登陆成功",map);

        }else {
            System.out.println("走这个?");
            return new ResponseResult(true,400,"用户名密码错误",null);
        }
    }

    @RequestMapping("/findUserRoleById")
    public ResponseResult findUserRelationRoleById(Integer id){
        List<Role> list = userService.findUserRelationRoleById(id);

        return new ResponseResult(true,200,"分配角色回显成功",list);
    }


    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVo userVo){
        userService.userContextRole(userVo);

        return new ResponseResult(true,200,"分配角色成功",null);
    }


    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request){
        String header_token = request.getHeader("Authorization");
        String access_token = (String)request.getSession().getAttribute("access_token");
        if (header_token.equals(access_token)){
             Integer user_id = (Integer) request.getSession().getAttribute("user_id");

            ResponseResult userPermissions = userService.getUserPermissions(user_id);
            return userPermissions;
        }else{
            return new ResponseResult(false,400,"获取菜单信息失败",null);
        }
    }
}
