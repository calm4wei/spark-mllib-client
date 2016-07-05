package com.cstor.system;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by FengWei on 2016/5/21.
 */
public class RunShell {

    public static void main(String[] args) {
        Connection conn = new Connection("192.168.6.154");

        try {
            conn.connect();
            if (conn.authenticateWithPassword("root", "cstorfs")) {
                Session session = conn.openSession();

                // 获取hbase目录的大小
                session.execCommand("hdfs dfs -du -s -h /apps/hbase/data");

                BufferedReader br = new BufferedReader(new InputStreamReader(session.getStdout()));

                String line = "";
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }

            }

            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
