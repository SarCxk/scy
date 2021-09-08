package com.scy.dao;


import com.scy.bean.Customer;
import com.scy.util.DBHelper;
import com.scy.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDao {


    // 1. 带参数的全查( 2 个表的)  // 作业1:
        // t_user 和 customer 表
        //  select * from t_customer c join t_user  u  on c.user_id  = u.id  where 后面要带参数!!

        //思考1 : 问题: 多个表联查,  返回值是什么 ????    Map
        // 思考2 : 问题: 多个表联查,  后面要带的参数 肯定是 多个表中的属性都有, 那么 用什么传参 ?   Map
        public List<Map>  selectAllByParam(Map map){
            List lists= new ArrayList();
            String page = (String) map.get("page"); // 接收前段的参数,放入到 map中, 这里直接获取就可以了
            String limit = (String) map.get("limit");


            String cname = (String) map.get("cname");
            String lookTime = (String) map.get("lookTime");
            String phoneNumber = (String) map.get("phoneNumber");
            String sex = (String) map.get("sex");
            String uname = (String) map.get("uname");




            //1. 开链接
            Connection connection = DBHelper.getConnection();
            // 2. 写sql
            String sql ="select c.* , t.username as username , t.password as password ,  t.real_name as real_name , t.type as type   from t_customer c  join t_user  t  on c.user_id  = t.id  where 1=1  ";
            sql = sql + " and t.is_del=1   ";


            if (null!=cname&&cname.length()>0){
                sql = sql + " and   c.cust_name   like  '%"+cname+"%'   ";
            }
            if (null!=lookTime&&lookTime.length()>0){
                sql = sql + " and  c.modify_time   =  "+lookTime+"   ";
            }
            if (null!=phoneNumber&&phoneNumber.length()>0){
                sql = sql + " and  c.cust_phone   =  "+phoneNumber+"   ";
            }
            if (null!=sex&&sex.length()>0){
                sql = sql + " and  c.cust_sex   =  "+sex+"   ";
            }
            if (null!=uname&&uname.length()>0){
                sql = sql + " and  t.username   like  '%"+uname+"%'   ";
            }


            sql = sql + " limit  ? ,  ?  ";
            System.out.println(" dao de  sql = " + sql);
            PreparedStatement ps=null;
            ResultSet rs=null;
            PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page),Integer.parseInt(limit));
            // 3. 编译sql
            try{
                ps = connection.prepareStatement(sql);
                ps.setInt(1,pageBeanUtil.getstart());  // 这是索引
                ps.setInt(2,Integer.parseInt(limit));
                //4, 执行sql
                rs = ps.executeQuery();
                while (rs.next()){
                    Map dataMap=new HashMap();
                    dataMap.put("id",rs.getInt("id"));
                    dataMap.put("cust_name",rs.getString("cust_name"));
                    dataMap.put("cust_company",rs.getString("cust_company"));
                    dataMap.put("cust_position",rs.getString("cust_position"));
                    dataMap.put("cust_phone",rs.getString("cust_phone"));
                    dataMap.put("cust_birth",rs.getString("cust_birth"));
                    dataMap.put("cust_sex",rs.getInt("cust_sex"));
                    dataMap.put("user_id",rs.getInt("user_id"));
                    dataMap.put("create_time",rs.getString("create_time"));
                    dataMap.put("modify_time",rs.getString("modify_time"));
                    dataMap.put("username",rs.getString("username"));
                    dataMap.put("password",rs.getString("password"));
                    dataMap.put("real_name",rs.getString("real_name"));
                    dataMap.put("type",rs.getInt("type"));
                    lists.add(dataMap);
                }
            }catch(SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return lists;
        }


        // 2. 带参数的查总条数 (2个表的) // 作业2:
        //  select count(*) from t_customer c join t_user  u  on c.user_id  = u.id  where 后面要带参数!!
        public int selectAllByParamCount(Map map){
            int total=0;
            // 1. 开链接
            Connection connection = DBHelper.getConnection();
            //2. 写sql
            String sql ="select  count(*) as total from t_customer c  join t_user  t  on c.user_id  = t.id  where 1=1  ";
            //3. 编译
            PreparedStatement ps=null;
            ResultSet rs=null;
            try {
                ps = connection.prepareStatement(sql);
                // 4. zhixing
                rs = ps.executeQuery();
                if (rs.next()){
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

    //增加
    public int insertCustomer(Customer customer){
            //开链接
        Connection connection = DBHelper.getConnection();
        //写sql
        String sql ="insert into t_customer values(?,?,?,?,?,?,?,?,?,?)";
        //预编译
        PreparedStatement ps = null;
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,customer.getCust_name());
            ps.setString(2,customer.getCust_company());
            ps.setString(3,customer.getCust_psoition());
            ps.setString(4,customer.getCust_phone());
            ps.setString(5,customer.getCust_birth());
            ps.setInt(6,customer.getCust_sex());
            ps.setString(7,customer.getCust_desc());
            ps.setInt(8,customer.getUser_id());
            ps.setString(9,customer.getCreate_time());
            ps.setString(10,customer.getModify_time());

            //4.执行预编译对象
            i = ps.executeUpdate();

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

        // 测试

       public static void main(String[] args) {
              CustomerDao dao = new CustomerDao();
               Map paramMap = new HashMap();
           //    paramMap.put("page","1");
           //    paramMap.put("limit","5");
           //    paramMap.put("phoneNumber","13300120019");


           //    // 全查
           //    List<Map> maps = customerDAO.selectAllByParam(paramMap);
           //    System.out.println("maps = " + maps);
           //    System.out.println("maps.size() = " + maps.size());

           //    //int i = customerDAO.selectAllByParamCount(paramMap);
           //    //  System.out.println("i = " + i);
           //}
           Customer customer = new Customer();
           customer.setCust_name("程序员");
           customer.setCust_company("厚朴");
           customer.setCust_position("Java开发");
           customer.setCust_phone("110120");
           customer.setCust_birth("2011-12-12");
           customer.setCust_sex(2);
           customer.setCust_desc("每天就是敲代码,秃头也没事 加油");
           customer.setUser_id(38);
           customer.setCreate_time("2020-08-08");
           customer.setModify_time("2020-08-08");
          int i =dao.addCustomer(customer);
           System.out.println("i = " + i);

       }

    private int addCustomer(Customer customer) {
        return 0;
    }
}
