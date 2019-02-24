package test;

import com.hsy.platform.plugin.Page;
import com.hsy.platform.plugin.PageData;
import com.hsy.platform.service.UserService;
import org.junit.Test;

import javax.annotation.Resource;

public class TestUserService  extends BaseTest{

    @Resource
    UserService userService;

    @Test
    public  void list() throws Exception{
        Page page = new Page();
        PageData pd = new PageData();
        pd.addParam("loginName","super");
        page.setTotal(100);
        page.setPd(pd);
        page =  userService.listPage(page);
        System.out.println();
    }
}
