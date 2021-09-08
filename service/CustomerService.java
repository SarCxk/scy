package com.scy.service;

import com.scy.bean.Customer;
import com.scy.dao.CustomerDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {
    //全查
    public Map selectAllByParam(Map map){

        CustomerDao dao= new CustomerDao();
        List<Map> maps = dao.selectAllByParam(map);
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("data",maps);
        codeMap.put("msg","ok");
        Map countMap = selectAllByParamCount(map);
        int count = (int) countMap.get("data");
        codeMap.put("count",count);
        return  codeMap;
    }
    // 全查总条数 多的
    public Map selectAllByParamCount(Map map){
        Map codeMap = new HashMap();
        CustomerDao dao= new CustomerDao();
        int i= dao.selectAllByParamCount(map);
        codeMap.put("code",0);
        codeMap.put("data",i);
        codeMap.put("msg","ok");
        return  codeMap;
    }
    //增加
    public Map insertCustomer(Customer customer) {

        CustomerDao dao = new CustomerDao();
        int i = dao.insertCustomer(customer);
        Map codeMap = new HashMap();
        if (i == 1) {
            codeMap.put("code", 0);
            codeMap.put("msg", "yes");
        } else {
            codeMap.put("code", 400);
            codeMap.put("msg", "no");
        }
        return codeMap;
    }
}
