package test;

import com.hsy.platform.plugin.LayPage;
import com.hsy.platform.plugin.PageData;
import com.hsy.platform.service.UserService;
import org.junit.Test;

import javax.annotation.Resource;

public class TestUserService  extends BaseTest{

    @Resource
    UserService userService;

    @Test
    public  void list() throws Exception{
        LayPage page = new LayPage();
        PageData pd = new PageData();
        pd.addParam("loginName","super");
        page.setCount(100);
        page.setPd(pd);
        page =  userService.listPage(page);
        System.out.println();
    }
}
