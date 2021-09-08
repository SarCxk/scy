package com.scy.dao;

import com.scy.bean.Visit;
import com.scy.util.DBHelper;
import com.scy.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VisitDao {

    // 查询总条数
    public int selectCount(Map map1){

        String cust_id = (String) map1.get("cust_id");
        String visit_time = (String) map1.get("visit_time");
        String visit_time1 = (String) map1.get("create_time");

        int total =0;
        //1. 创建连接
        Connection connection = DBHelper.getConnection();
        //2.书写sql语句
        String sql = "select count(*) total  from visit where   1=1";// where 1=1因为有多余的 a

        if (null !=cust_id  ) {
            sql = sql + " and cust_id = " +cust_id +" ";
        }
        if (null !=visit_time && visit_time.length()>0 && null !=visit_time1 && visit_time1.length()>0) {
            sql = sql + " and visit_time between " +visit_time +" " +" and "+ visit_time1+" ";
        }

        System.out.println("count 的 sql= " + sql);
        PreparedStatement ps = null;
        ResultSet rs = null;
        //3. 预编译sql
        try {
            ps = connection.prepareStatement(sql);
            //4. 执行预编译对象
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return total;
    }
    // 动态的带参数的分页查询     page 是 页数  条数 是limit
    public List<Visit> selectAllByParam(Map map){

        String page = (String) map.get("page");
        String limit = (String) map.get("limit");
        String cust_id = (String) map.get("cust_id");
        String visit_time = (String) map.get("visit_time");
        String visit_time1 = (String) map.get("create_time");

        List<Visit> lists = new ArrayList<>();
        //1.创建连接
        Connection connection = DBHelper.getConnection();
        //2.sql语句
        String sql = "select * from visit where 1=1  ";// where 1=1因为有多余的 and
        if (null !=cust_id  ) {
            sql = sql + " and cust_id = " +cust_id +" ";
        }
        if (null !=visit_time && visit_time.length()>0 ) {
            sql = sql + " and order by" +visit_time +" " +" and "+ visit_time1+" ";
        }
        if (null !=visit_time && visit_time.length()>0 && null !=visit_time1 && visit_time1.length()>0) {
            sql = sql + " and visit_time between " +visit_time +" " +" and "+ visit_time1+" ";
        }
         sql = sql + "limit ? ,?" ;
        System.out.println("dao 的 sql = " + sql);
        //3.编译 sql
        PreparedStatement ps = null;
        ResultSet rs = null;
        PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page),Integer.parseInt(limit));
        try {
            //3. 预编译对象
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getstart());//索引
            ps.setInt(2,Integer.parseInt(limit));
            // 4.执行sql
            rs = ps.executeQuery();
            while (rs.next()) {
              Visit visit = new Visit();
                visit.setId(rs.getInt("id"));
                visit.setUser_id(rs.getInt("user_id"));
                visit.setCust_id(rs.getInt("cust_id"));
                visit.setVisit_desc(rs.getString("visit_desc"));
                visit.setVisit_time(rs.getString("visit_time"));
                visit.setCreate_time(rs.getString("create_time"));

                lists.add(visit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  lists;
    }


    //全查
    public  List<Visit> selectAll() {

        // 步骤1: 创建出 连接对象
        Connection connection = DBHelper.getConnection();
        // 步骤2: 创建出 sql 语句
        String sql = "select * from visit ";

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Visit> list = new ArrayList<>();
        try {
            // 步骤3: 使用连接对象 获取 预编译对象
            ps = connection.prepareStatement(sql);
            //  步骤4: 执行 预编译对象得出结果集
            rs = ps.executeQuery();
            // 步骤5: 遍历结果
            while (rs.next()) {
                Visit visit = new Visit();
                visit.setId(rs.getInt("id"));
                visit.setUser_id(rs.getInt("user_id"));
                visit.setCust_id(rs.getInt("cust_id"));
                visit.setVisit_desc(rs.getString("visit_desc"));
                visit.setVisit_time(rs.getString("visit_time"));
                visit.setCreate_time(rs.getString("create_time"));

                list.add(visit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    //添加
    public  int addVisit(Visit visit){
        Connection connection = DBHelper.getConnection();

        String sql = "insert into visit  values (null,?,?,?,?,?) ";
        PreparedStatement ps = null;
        int i = 0;
        try {
            ps =  connection.prepareStatement(sql);

            ps.setInt(1,visit.getUser_id());
            ps.setInt(2,visit.getCust_id());
            ps.setString(3,visit.getVisit_desc());
            ps.setString(4,visit.getVisit_time());
            ps.setString(5,visit.getCreate_time());


            i =ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
         return i;
    }

    public static void main(String[] args) {
        VisitDao dao = new VisitDao();
        Visit visit = new Visit();

        //全查
//        List<Visit> visits = dao.selectAll();
//        System.out.println("visits = " + visits);

        // 添加
        visit.setUser_id(7);
        visit.setCust_id(1);
        visit.setVisit_desc("对方考虑了我们的要求,并在三天之后做出答复");
        visit.setVisit_time("2021-08-15");
        visit.setCreate_time("2021-08-15");
        int i = dao.addVisit(visit);
        System.out.println("i = " + i);
    }

}
