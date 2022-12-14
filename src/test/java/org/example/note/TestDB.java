package org.example.note;

import org.example.note.util.DBUtil;
import org.junit.Test;

import java.sql.SQLException;

public class TestDB {
    @Test
    public void testDB() throws SQLException {
        System.out.println(DBUtil.getConnection());
    }
}
