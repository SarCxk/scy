package com.scy.controller;

import com.alibaba.fastjson.JSONObject;
import com.scy.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "UserUpdateForDelServlet",urlPatterns = "/UserUpdateForDelServlet")
public class UserUpdateForDelServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受登陆传过来的3个参数
        //1、修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");

        //2、接收前端传过来的参数
        String sfDel=req.getParameter("sfDel");
        String userId=req.getParameter("userId");

        System.out.println("userId = " + userId);
        System.out.println("sfDel = " + sfDel);

        //3、调佣service
        UserService userService = new UserService();
        Map map=userService.updateUserById( Integer.parseInt(sfDel),Integer.parseInt(userId));

        //4、把map变成json
        String s = JSONObject.toJSONString(map);

        //5、使用流输出
        PrintWriter writer = resp.getWriter();
        writer.println(s);
        writer.close();

    }
}
