package com.scy.service;

import com.scy.bean.Visit;
import com.scy.dao.VisitDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitService {


    // 带参数的分页查询
    public Map selectAllByParam(Map map1){
        VisitDao dao = new VisitDao();
        List<Visit> visits = dao.selectAllByParam(map1);
        int i = dao.selectCount(map1);

        Map map = new HashMap();
        //  map.put("code",0); //必须和layui的json 返回的格式一样
        map.put("code",0);
        map.put("msg","数据查询成功");
        map.put("count",i);
        map.put("data",visits);
        return map;
    }

    // 增加
    public Map addVisit(Visit visit){
        VisitDao dao = new VisitDao();
        int i = dao.addVisit(visit);
        System.out.println("i = " + i);

        Map codeMap = new HashMap();
        if (i == 1) {
            codeMap.put("code",0);
            codeMap.put("msg","添加成功");
        }else{
            codeMap.put("code",400);
            codeMap.put("msg","添加失败");
        }
        return codeMap;
    }

    public Map VisitdeleteById(int parseInt) {


        return null;
    }
}
