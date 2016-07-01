package com.cstor.hive;

import org.apache.hive.jdbc.HiveStatement;

import java.sql.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Connection con = DriverManager.getConnection("jdbc:hive2://192.168.6.154:10000/test", "hive", "");
        HiveStatement stmt = (HiveStatement) con.createStatement();
        String tableName = "invites";
        String sql = "select count(*) as cos from partition_test"; // select count(*) from invites group by foo
        ResultSet res = stmt.executeQuery(sql);

//        String token = "Submitting tokens for job: ";
//        String regex = token;
//        Pattern pattern = Pattern.compile(regex);
//
//        List<String> logs = stmt.getQueryLog();
//        System.out.println("================log============");
//        for (String log : logs) {
//            Matcher m = pattern.matcher(log);
//            if (m.find()) {
//                String jobid = log.substring(log.indexOf(token) + token.length(), log.length());
//                // job替换为application
//                System.out.println("*************************************" + jobid + " ===============> " + jobid.replaceAll("job", "application"));
//            }
//            System.out.println(log);
//        }

        ResultSetMetaData metaData = res.getMetaData();
        int columnCounts = metaData.getColumnCount();
        for (int i = 1; i <= columnCounts; i++) {
            System.out.println("column " + i + " : " + metaData.getColumnName(i));
        }


        while (res.next()) {
            System.out.println(res.getString(1));
        }
        long t2 = System.currentTimeMillis();

        res.close();
        stmt.close();
        con.close();
        System.out.println("time = " + (t2 - t1));

    }

    public String regexJobId(String log, String regex) {

        return null;
    }
}
