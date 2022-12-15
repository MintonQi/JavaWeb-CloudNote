package org.example.note;

import org.example.note.dao.UserDao;
import org.example.note.po.User;
import org.junit.Test;

import java.sql.SQLException;


public class TestUser {

    @Test
    public void testQueryUserByName() throws SQLException {
        UserDao userDao = new UserDao();
        User user = userDao.queryUserByName("admin");
        System.out.println(user.getUpwd());
    }


}
