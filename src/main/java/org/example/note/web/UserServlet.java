package org.example.note.web;

import org.example.note.po.User;
import org.example.note.service.UserService;
import org.example.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // accept user action
        String actionName = req.getParameter("actionName");

        if("login".equals(actionName)){
            try {
                userLogin(req, resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    /**
     1. 获取参数 （姓名、密码）
     2. 调用Service层的方法，返回ResultInfo对象
     3. 判断是否登录成功
     如果失败
         将resultInfo对象设置到request作用域中
         请求转发跳转到登录页面
     如果成功
        将用户信息设置到session作用域中
        判断用户是否选择记住密码（rem的值是1）
            如果是，将用户姓名与密码存到cookie中，设置失效时间，并响应给客户端
            如果否，清空原有的cookie对象
        重定向跳转到index页面
     * @param req
     * @param resp
     */
    private void userLogin(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {

        String userName = req.getParameter("userName");
        String userPwd = req.getParameter("userPwd");

        ResultInfo<User> resultInfo = userService.userLogin(userName, userPwd);

        if(resultInfo.getCode() == 1){
            req.getSession().setAttribute("user", resultInfo.getResult());

            String rem = req.getParameter("rem");
            if("1".equals(rem)){
                Cookie cookie = new Cookie("user", userName+"-"+userPwd);
                cookie.setMaxAge(3*24*60*60);
                resp.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie("user", null);
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
            resp.sendRedirect("index.jsp");


        } else{
            req.setAttribute("resultInfo", resultInfo);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

    }
}
