package com.hsy.platform.controller;

import com.hsy.platform.plugin.Page;
import com.hsy.platform.plugin.PageData;
import com.hsy.platform.service.PosService;
import com.hsy.platform.service.UserService;
import com.hsy.platform.utils.LoginInfoUtils;
import com.hsy.platform.utils.PropertiesUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class LoginController extends BaseController {

    static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Resource
    UserService userService;

    @Resource
    PosService PosService;

    @RequestMapping("/")
    public  ModelAndView root() throws Exception{
      if( "true".equalsIgnoreCase(PropertiesUtils.getValueByKey("debugMode"))){
          autoLogin();
      };
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            ModelAndView mv = new ModelAndView("redirect:/index");
            return mv;
        }
        ModelAndView mv = new ModelAndView("security/login");
        return mv;
    }



    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String,Object> login(){
        PageData PageData = this.getPageData();
        String username = PageData.getString("loginName");
        String password = PageData.getString("password");
        String rememberMe = PageData.getString("rememberMe");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        if(StringUtils.isNotBlank(rememberMe)){
            token.setRememberMe(true);
        }
        try {
            subject.login(token);
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            PageData user = userService.getUserByLoginName(currentUser.getPrincipal().toString());
            session.setAttribute("userName",user.getString("userName"));
            session.setAttribute("userId",user.getString("userId"));
            session.setAttribute("deptId",user.getString("deptId"));
            log.info("{}登录成功",user.getString("userName"));
        } catch (AuthenticationException e) {
            return this.getResultMap(false,"密码错误");
        }catch (Exception e2){
            log.error("登录异常:{}",e2.getMessage());
            return this.getResultMap(false,"内部异常");
        }
        log.debug(LoginInfoUtils.getUserName()+"登录成功");
        return this.getResultMap(true,"登录成功");
    }

    @RequestMapping("/logout")
    public String  logout() throws IOException {
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        subject.logout();
        log.debug("{}登出",username);
        return "redirect:/";
    }

    @RequestMapping("home")
    public ModelAndView home(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.addObject("username",LoginInfoUtils.getUserName());
        mv.setViewName("home");
        return mv;
    }

    @RequestMapping("/testUserExist")
    @ResponseBody
    public Map<String,Object> testUserExist () throws Exception {
        PageData PageData = this.getPageData();
        String username = PageData.getString("username");
        String testType = PageData.getString("testType");
        PageData pddata = userService.getUserByLoginName(username);
        if("login".equalsIgnoreCase(testType)){//d登录类型:存在返回true
            return pddata==null?getResultMap(false,""):getResultMap(true,"");
        }else{//注册类型:不存在返回true
            return pddata==null?getResultMap(true,""):getResultMap(false,"");
        }
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    @RequestMapping("unLock")
    @ResponseBody
    public Map<String,Object> unLock() throws Exception{
       PageData pd = userService.getById(LoginInfoUtils.getUserId());
       if(ObjectUtils.equals(pd.get("loginPassword"),getRequest().getParameter("password"))){
           return getResultMap(true,"");
       }else {
           return getResultMap(false,"密码不正确");
       }
    }



    @Override
    public ModelAndView init() throws Exception {
        return null;
    }

    @Override
    public ModelAndView onEdit() throws Exception {
        return null;
    }

    @Override
    public Page list(Page page) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> delete() throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> update() throws Exception{
        return null;
    }

    @Override
    public Map<String, Object> save() throws Exception{
        return null;
    }

    private void autoLogin() throws Exception {
      UsernamePasswordToken token = new UsernamePasswordToken("super", "1b3231655cebb7a1f783eddf27d254ca");
        Subject subject = SecurityUtils.getSubject();
         subject.login(token);
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        PageData user = userService.getUserByLoginName(currentUser.getPrincipal().toString());
        session.setAttribute("userName",user.getString("userName"));
        session.setAttribute("userId",user.getString("userId"));
        session.setAttribute("deptDm",user.getString("jgDm"));
    }
}
