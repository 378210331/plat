package com.hsy.platform.plugin;

import com.hsy.platform.service.MenuService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AllMenuRowMapper implements RowMapper {

    @Resource
    MenuService menuService;

    @Override
    public Map<String,Object> mapRow(ResultSet resultSet, int i) throws SQLException {
        Map<String,Object> result = new HashMap<>();
        String menuId = resultSet.getString("menu_id");
        String menuName = resultSet.getString("menu_name");
        String menuJc = resultSet.getString("menu_jc");
        String menuUrl = resultSet.getObject("menu_url")==null?"":resultSet.getString("menu_url");
        String ip = resultSet.getObject("ip")==null?"":resultSet.getString("ip");
        String menuTyle = resultSet.getString("menu_type");//菜单类型  1 菜单 2功能
        if("1".equalsIgnoreCase(menuTyle)){
            List<Map<String,Object>> child = menuService.getAllMenu(menuId);
            if(child.size()>0){
                result.put("child",child);
            }
        }
        result.put("code", menuId);
        result.put("title", menuName);
        result.put("menuUrl",menuUrl);
        result.put("ip",ip);
        result.put("icon", resultSet.getObject("menu_icon")==null?"":resultSet.getString("menu_icon"));
        return result;
    }

}
