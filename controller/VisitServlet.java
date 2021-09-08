package com.scy.controller;

import com.alibaba.fastjson.JSONObject;
import com.scy.service.VisitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "VisitServlet" ,urlPatterns = "/VisitServlet")
public class VisitServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html ; charset=UTF-8");
        //2.接受 2个参数 page limit
        String page = req.getParameter("page");
        String limit = req.getParameter("limit");

        String cust_id = req.getParameter("cust_id");
        String visit_time = req.getParameter("visit_time");
        String create_time = req.getParameter("create_time");
        System.out.println("cust_id = " + cust_id);

        Map map1 = new HashMap();
        map1.put("page",page);
        map1.put("limit",limit);
        map1.put("cust_id",cust_id);
        map1.put("visit_time",visit_time);
        map1.put("create_time",create_time);

        VisitService service = new VisitService();
        Map map = service.selectAllByParam(map1);

        String s = JSONObject.toJSONString(map);
        PrintWriter writer = resp.getWriter();
        writer.println(s);
        writer.close();
    }
}
