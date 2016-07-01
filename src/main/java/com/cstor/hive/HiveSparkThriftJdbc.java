package com.cstor.hive;

import org.apache.hive.jdbc.HiveStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2016/6/15
 *
 * @author feng.wei
 */
public class HiveSparkThriftJdbc {

    private static String driverName =
            "org.apache.hive.jdbc.HiveDriver";


    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        long t1 = System.currentTimeMillis();
        Connection con = DriverManager.getConnection("jdbc:hive2://datacube151:10001/default", "spark", "");
        HiveStatement stmt = (HiveStatement) con.createStatement();
        String tableName = "pokes";
        String sql = "select foo,bar from pokes ORDER by bar ASC "; // select count(*) from invites group by foo
        ResultSet res = stmt.executeQuery(sql);

        String token = "Submitting tokens for job";
        String regex = token;
        Pattern pattern = Pattern.compile(regex);

        List<String> logs = stmt.getQueryLog();
        System.out.println("================log============");
        for (String log : logs) {
            Matcher m = pattern.matcher(log);
            if (m.find()) {
                String jobid = log.substring(log.indexOf(token) + token.length(),log.length());
                System.out.println("*************************************" + jobid);
            }
            System.out.println(log);
        }
        while (res.next()) {
            System.out.println(res.getString(1));
        }
        long t2 = System.currentTimeMillis();
        stmt.close();
        System.out.println("time = " + (t2 - t1));

    }

    public String regexJobId(String log, String regex) {

        return null;
    }
}
