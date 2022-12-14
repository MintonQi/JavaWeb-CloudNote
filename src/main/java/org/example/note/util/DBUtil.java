package org.example.note.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtil {

    private static Properties properties = new Properties();

    static {
        try {
            // 加载配置文件（输入流）
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            // load()方法将输入流的内容加载到配置文件中
            properties.load(in);
            // get name of driver and load it
            Class.forName(properties.getProperty("jdbcName"));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get db connection
     * @return connection
     */
    public static Connection getConnection() throws SQLException {
        Connection connection;

        // get info of connection
        String dbUrl = properties.getProperty("dbUrl");
        String dbName = properties.getProperty("dbName");
        String dbPwd = properties.getProperty("dbPwd");
        // get connection
        connection = DriverManager.getConnection(dbUrl, dbName, dbPwd);
        return connection;
    }

    /**
     * close resources
     * @param resultSet
     * @param preparedStatement
     * @param connection
     * @throws SQLException
     */
    public static void close(ResultSet resultSet,
                             PreparedStatement preparedStatement,
                             Connection connection) throws SQLException {
        if(resultSet != null){
            resultSet.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        if(connection != null){
            connection.close();
        }
    }
}
