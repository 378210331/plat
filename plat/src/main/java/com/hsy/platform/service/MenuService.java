package com.hsy.platform.service;

import com.hsy.platform.plugin.AllMenuRowMapper;
import com.hsy.platform.plugin.MenuRowMapper;
import com.hsy.platform.plugin.PageData;
import com.hsy.platform.utils.LoginInfoUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MenuService extends BaseService  {


    @Resource
    MenuRowMapper menuMapper;

    @Resource
    AllMenuRowMapper allMenuRowMapper;


    @Override
    public String getMapperName() {
        return "MenuMapper";
    }



    /**
     * 获取人员权限菜单
     * @param userId
     * @param pid
     * @return
     */
    public List<Map<String, Object>> getRoleMenu(String userId, String pid) {
        String sql  = "select m.*" +
                "  from t_sys_menu m, t_sys_menu_pos mg,t_sys_system s" +
                " where m.menu_id = mg.menu_id and m.system_id = s.system_id(+)" +
                "   and mg.gw_dm in (select gw.gw_dm" +
                "                      from t_sys_pos pos, t_sys_user u, t_sys_user_pos ug" +
                "                     where pos.pos_id = ug." +
                "                       and ug.user_id = u.user_id and u.user_id = :userId)" ;
        if(pid == null || "".equalsIgnoreCase(pid)){
            sql += " and  m.pid = '0'";
        }else{
            sql += " and m.pid = :pid ";
        }
        sql  +="  order by m.sort_num  asc";
        Map<String,Object> param = new HashMap<>();
        if(userId==null ||"".equalsIgnoreCase(userId)){
            userId = LoginInfoUtils.getUserId();
        }
        param.put("userId",userId);
        param.put("pid",pid);
        return getJdbcDao().getNamedParameterJdbcTemplate().query(sql,param,menuMapper);
    }

    /**
     * 获取全权限菜单树
     * @param pid
     * @return
     */
    public List<Map<String, Object>> getAllMenu(String pid) {
        String sql  = "select * from t_sys_menu m,t_sys_system s where m.system_id = s.system_id(+) ";
        if(pid == null){
            sql += " and m.pid = '0' ";
        }else {
            sql += " and m.pid = :pid";
        }
        sql  +="  order by m.sort_num  asc";
        Map<String,Object> param = new HashMap<>();
        param.put("pid",pid);
        return getJdbcDao().getNamedParameterJdbcTemplate().query(sql,param,allMenuRowMapper);
    }

    public String createMenuId (){
        String sql  = "select max(menu_id)+1 as id from t_sys_menu";
        return getDao().queryMapBySql(sql).get("id").toString();
    }



    /**
     * 删除菜单
     * @param pd
     * @throws Exception
     */
    @Override
    public void delete(PageData pd) throws Exception {
        getDao().delete(getMapperName()+".deleteGwMenu", pd);
        getDao().delete(getMapperName()+".deleteRoleMenu", pd);
        getDao().delete(getMapperName()+".delete", pd);
    }

}
