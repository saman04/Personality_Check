/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vit.personalitycheck;

/**
 *
 * @author Amitabh
 */

    import java.io.*;

public class CmdTest {
    public static void cmd(String test,String model,String out) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", "cd \"C:\\Users\\Amitabh\\Desktop\\facebook\\windows\" && svm-predict "+test+" "+model+" "+out);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }
    }
}

