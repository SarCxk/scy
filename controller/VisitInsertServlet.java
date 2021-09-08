package com.scy.controller;

import com.alibaba.fastjson.JSON;
import com.scy.bean.Visit;
import com.scy.service.VisitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "VisitInsertServlet" ,urlPatterns = "/VisitInsertServlet")
public class VisitInsertServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html ; charset=UTF-8");

        //2.接受前端参数
        String user_id = req.getParameter("user_id" );
        String cust_id = req.getParameter("id" );
        String visit_desc = req.getParameter("visit_desc" );
        String visit_time= req.getParameter("visit_time" );
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String create_time= simpleDateFormat.format(new Date());


        //3.赋值到实体类
        Visit visit = new Visit();
       visit.setUser_id(Integer.parseInt(user_id));
        visit.setCust_id(Integer.parseInt(cust_id));
        visit.setVisit_desc(visit_desc );
        visit.setVisit_time(visit_time);
        visit.setCreate_time(create_time);


        //4. 调用service层
        VisitService service = new VisitService();
        Map map = service.addVisit(visit);

        String s = JSON.toJSONString(map);
        PrintWriter writer = resp.getWriter();
        writer.println(s);
        writer.close();
    }
}
