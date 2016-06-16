package com.cstor.hive;

import java.sql.*;

/**
 * Created on 2016/6/15
 *
 * @author feng.wei
 */
public class HiveJdbc {

    private static String driverName =
            "org.apache.hive.jdbc.HiveDriver";


    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        long t1 = System.currentTimeMillis();
        Connection con = DriverManager.getConnection("jdbc:hive2://datacube154:10000/default", "cstor", "");
        Statement stmt = con.createStatement();
        String tableName = "invites";
        String sql = "select * from invites"; // select count(*) from invites group by foo
        ResultSet res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1) + ", " + res.getString(2));
        }
        long t2 = System.currentTimeMillis();
        System.out.println("time = " + (t2 - t1));


    }
}
