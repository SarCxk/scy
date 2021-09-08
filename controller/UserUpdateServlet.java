package com.scy.controller;

import com.alibaba.fastjson.JSONObject;
import com.scy.bean.User;
import com.scy.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "UserUpdateServlet",urlPatterns = "/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修正 编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");
        //2.接受前端的参数
        String is_del = req.getParameter("is_del");
        String modif_time = req.getParameter("modif_time");
        String password = req.getParameter("password");
        String real_name = req.getParameter("real_name");
        String type = req.getParameter("type");
        String username = req.getParameter("username");
        //缺少一个最重要的 参数,即：主键id因为修改是按主键id 来修改。
        String id=req.getParameter("id");
        //把参数赋值成对象
        //在修改之前，先查询出来 前端没有的参数
        //调用service层

        UserService userService=new UserService();
        Map map=userService.selectUserById(Integer.parseInt(id));
        User data= (User) map.get("data");

        User user = new User();
        user.setCreate_time("???");
        user.setImg("??");
        user.setIs_del(Integer.parseInt(is_del));
        user.setModify_time(modif_time);
        user.setPassword(password);
        user.setReal_name(real_name);
        user.setType(Integer.parseInt(type));
        user.setUsername(username);
        user.setId(Integer.parseInt(id));
        Map map1 = userService.updateUser(user);
        String s = JSONObject.toJSONString(map1);
        PrintWriter writer=resp.getWriter();
        writer.println(s);
        writer.close();


        System.out.println("is_del = " + is_del);
        System.out.println("modif_time = " + modif_time);
        System.out.println("password = " + password);
        System.out.println("real_name = " + real_name);
        System.out.println("type = " + type);
        System.out.println("username = " + username);
        System.out.println("id = " + id);

    }
}
