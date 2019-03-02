package com.hsy.platform.service;

import com.hsy.platform.dao.JdbcDao;
import com.hsy.platform.plugin.AllMenuRowMapper;
import com.hsy.platform.plugin.MenuRowMapper;
import com.hsy.platform.plugin.PageData;
import com.hsy.platform.utils.LoginInfoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuService extends BaseService {

    @Resource
    JdbcDao jdbcDao;

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
        String sql  = "SELECT" +
                "  m.*,s.ip FROM" +
                "  t_sys_menu m " +
                "    left join t_sys_system s " +
                "      on m.system_id = s.system_id," +
                "  t_sys_menu_pos pos" +
                "  WHERE" +
                "  m.menu_id = pos.menu_id" +
                "  AND pos.pos_id IN (" +
                "  SELECT" +
                "  pos.pos_id" +
                "  FROM" +
                "  t_sys_pos pos," +
                "  t_sys_user u," +
                "  t_sys_user_pos ug" +
                "  WHERE" +
                "  pos.pos_id = ug.pos_id" +
                "  AND ug.user_id = u.user_id" +
                "  AND u.user_id = '89757'" +
                "  )" ;
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
        return jdbcDao.getNamedParameterJdbcTemplate().query(sql,param,menuMapper);
    }

    /**
     * 获取全权限菜单树
     * @param pid
     * @return
     */
    public List<Map<String, Object>> getAllMenu(String pid) {
        String sql  = "select * from t_sys_menu m left join t_sys_system s on m.system_id = s.system_id where 1=1 ";
        if(pid == null){
            sql += " and m.pid = '0' ";
        }else {
            sql += " and m.pid = :pid";
        }
        sql  +="  order by m.sort_num  asc";
        Map<String,Object> param = new HashMap<>();
        param.put("pid",pid);
        return jdbcDao.getNamedParameterJdbcTemplate().query(sql,param,allMenuRowMapper);
    }


    /**
     * 删除菜单
     * @param pd
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(PageData pd) throws Exception {
        getDao().delete(getMapperName()+".deletePosMenu", pd);
        getDao().delete(getMapperName()+".deleteRoleMenu", pd);
        getDao().delete(getMapperName()+".delete", pd);
    }

}
