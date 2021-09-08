package com.scy.controller;

import com.alibaba.fastjson.JSONObject;
import com.scy.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LoginServlet",urlPatterns ="/LoginServlet" )
public class LoginServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //要接受 登录传过来的三个参数


        // 1. 修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html ; charset=UTF-8");

        // 2. 接受 前端过来的三个参数
        String username =  req.getParameter("username");
        String password =  req.getParameter("password");
        String code =  req.getParameter("code");

        // 3.第一次登录
        HttpSession session = req.getSession();
        String codeFromSession = (String )session.getAttribute("code");
        if (!codeFromSession.equals(code)) {
            //验证码
            PrintWriter writer =resp.getWriter();
            Map map = new HashMap<>();
            map.put("code",400);
            map.put("msg","验证码不正确");
            //给一个账户 给前端渲染
            map.put("username",username);

            //把map 变为 json
            String jsonString = JSONObject.toJSONString(map);
            writer.println(jsonString);
            writer.close();
        }else {
            // 验证码正确  继续判断账户密码
            System.out.println("----------------------");

            UserService service = new UserService();
            Map map = service.login(username,password,req);
            String json =JSONObject.toJSONString(map);
            PrintWriter writer = resp.getWriter();
            writer.println(json);
            writer.close();
        }
    }
}
