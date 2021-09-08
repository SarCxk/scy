package com.scy.controller;


import com.alibaba.fastjson.JSONObject;
import com.scy.service.UserService;
import com.scy.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "SelectAllByServiceServlet" ,urlPatterns = "/SelectAllByServiceServlet")
public class selectAllByServiceServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 修正 编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8"); // 注意 , 别写错

        UserService userService = new UserService();
        Map map = userService.selectAllByService();

        PrintWriter writer = resp.getWriter();
        String s= JSONObject.toJSONString(map);
        writer.println(s);
        writer.close();
    }
}
