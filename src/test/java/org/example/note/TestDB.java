package org.example.note;

import org.example.note.util.DBUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class TestDB {

    private Logger logger = LoggerFactory.getLogger(TestDB.class);

    @Test
    public void testDB() throws SQLException {
        System.out.println(DBUtil.getConnection());

        logger.info("获取数据库连接：" + DBUtil.getConnection());
    }
}
