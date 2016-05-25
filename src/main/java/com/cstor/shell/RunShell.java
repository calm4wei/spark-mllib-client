package com.cstor.shell;

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
        Connection conn = new Connection("192.168.107.202");

        try {
            conn.connect();
            if (conn.authenticateWithPassword("root", "root")) {
                Session session = conn.openSession();

                session.execCommand("/root/monitor.sh");

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
