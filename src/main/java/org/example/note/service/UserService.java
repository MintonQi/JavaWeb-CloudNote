package org.example.note.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.example.note.dao.UserDao;
import org.example.note.po.User;
import org.example.note.vo.ResultInfo;

import java.sql.SQLException;

public class UserService {

    private UserDao userDao = new UserDao();

    /**
     1. 判断参数是否为空
        如果为空
             设置ResultInfo对象的状态码和提示信息
             返回resultInfo对象
     2. 如果不为空，通过用户名查询用户对象
     3. 判断用户对象是否为空
         如果为空
             设置ResultInfo对象的状态码和提示信息
             返回resultInfo对象
     4. 如果用户对象不为空，将数据库中查询到的用户对象的密码与前台传递的密码作比较 （将密码加密后再比较）
            如果密码不正确
             设置ResultInfo对象的状态码和提示信息
             返回resultInfo对象
     5. 如果密码正确
        设置ResultInfo对象的状态码和提示信息
     6. 返回resultInfo对象
     * @param userName
     * @param userPwd
     * @return
     */
    public ResultInfo<User> userLogin(String userName, String userPwd) throws SQLException {
        ResultInfo<User> resultInfo = new ResultInfo<>();

        // 数据回显
        User u = new User();
        u.setUname(userName);
        u.setUpwd(userPwd);
        resultInfo.setResult(u);


//        1. 判断参数是否为空
        if(StrUtil.isBlank(userName) || StrUtil.isBlank(userPwd)){
            resultInfo.setCode(0);
            resultInfo.setMsg("User Name and Password cannot be empty!");
            return resultInfo;
        }

//        2. 如果不为空，通过用户名查询用户对象
        User user = userDao.queryUserByName(userName);

//        3. 判断用户对象是否为空
        if(user == null){
            resultInfo.setCode(0);
            resultInfo.setMsg("User doesn't exist!");
            return resultInfo;
        }
//        4. 如果用户对象不为空，将数据库中查询到的用户对象的密码与前台传递的密码作比较 （将密码加密后再比较）
        userPwd = DigestUtil.md5Hex(userPwd);
        if(!userPwd.equals(user.getUpwd())){
            resultInfo.setCode(0);
            resultInfo.setMsg("Password incorrect!");
            return resultInfo;
        }

        resultInfo.setCode(1);
        resultInfo.setResult(user);
        return resultInfo;
    }

}
