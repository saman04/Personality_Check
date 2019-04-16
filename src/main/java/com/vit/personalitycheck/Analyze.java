/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vit.personalitycheck;

import static com.vit.personalitycheck.test.removeStopWordsAndStem;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

@Path("/analyze")

public class Analyze {

    ResultSet RS1;
    Statement stm;
    int ctr = 0;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String get(String posts) throws Exception {
        System.out.println("IN ANALYZE");
        String result = "false";
        System.out.println(posts);
        JSONArray inputJsonArr = new JSONArray(posts);
        List<List<String>> docs = new ArrayList<List<String>>();
        int i = 0;
        System.out.println(inputJsonArr.length());
        JSONObject j = new JSONObject();
        while (i < inputJsonArr.length()) {
            j = inputJsonArr.getJSONObject(i);
            System.out.println(j);
            String pos = (String) j.get("post");
            String id = (String) j.get("id");
            System.out.println(id + ")" + pos);
            pos = removeStopWordsAndStem(pos);
            List<String> doc = new ArrayList<String>(Arrays.asList(pos.split(" ")));
            docs.add(doc);
            i++;
        }

        try {
            String url;
            java.sql.Connection con;
            Statement stm;
            ResultSet RS;
            String str;
            String word;
            int id;
            double tfidf;
            BufferedWriter out = null;
            BufferedWriter out1 = null;
            BufferedWriter out2 = null;
            BufferedWriter out3 = null;
            BufferedWriter out4 = null;
            url = "jdbc:mysql://localhost:3306/personality";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, "abc", "Project123");
            str = "Database connection established";
            System.out.println(str);
            stm = con.createStatement();
            String sql = "SELECT * FROM word ";
            System.out.println("----------------------------------------");
            System.out.println("Doc\tWord\tTF-IDF");
            for (i = 0; i < docs.size(); i++) {
                int ctr1 = 0;
                RS = stm.executeQuery(sql);
                String content = new String("");
                content = content.concat(Integer.toString(1));
                while (RS.next()) {
                    word = RS.getString("word");
                    id = RS.getInt("word-id");
                    tfidf = test.tfIdf(docs.get(i), docs, word);
                    //System.out.println((i + 1) + "\t" + word + "\t" + tfidf);
                    if (tfidf > 0) {
                        ctr1++;
                        content = content.concat(((" ".concat(Integer.toString(id)).concat(":"))).concat(Double.toString(tfidf)));
                    }
                }
                if (ctr1 > 0) {
                    try {
                        System.out.println(content);
                        if (i == 0) {
                            out = new BufferedWriter(new FileWriter("/users/Amitabh/Desktop/facebook/windows/o.txt"));
                            out.write(content);
                            out.close();
                            out1 = new BufferedWriter(new FileWriter("/users/Amitabh/Desktop/facebook/windows/c.txt"));
                            out1.write(content);
                            out1.close();
                            out2 = new BufferedWriter(new FileWriter("/users/Amitabh/Desktop/facebook/windows/e.txt"));
                            out2.write(content);
                            out2.close();
                            out3 = new BufferedWriter(new FileWriter("/users/Amitabh/Desktop/facebook/windows/a.txt"));
                            out3.write(content);
                            out3.close();
                            out4 = new BufferedWriter(new FileWriter("/users/Amitabh/Desktop/facebook/windows/n.txt"));
                            out4.write(content);
                            out4.close();
                        } else {

                            out = new BufferedWriter(new FileWriter("/users/Amitabh/Desktop/facebook/windows/o.txt", true));
                            out.newLine();
                            out.write(content);
                            out.close();
                            out1 = new BufferedWriter(new FileWriter("/users/Amitabh/Desktop/facebook/windows/c.txt", true));
                            out1.newLine();
                            out1.write(content);
                            out1.close();
                            out2 = new BufferedWriter(new FileWriter("/users/Amitabh/Desktop/facebook/windows/e.txt", true));
                            out2.newLine();
                            out2.write(content);
                            out2.close();
                            out3 = new BufferedWriter(new FileWriter("/users/Amitabh/Desktop/facebook/windows/a.txt", true));
                            out3.newLine();
                            out3.write(content);
                            out3.close();
                            out4 = new BufferedWriter(new FileWriter("/users/Amitabh/Desktop/facebook/windows/n.txt", true));
                            out4.newLine();
                            out4.write(content);
                            out4.close();
                        }
                        System.out.println("Done");

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            CmdTest.cmd("o.txt", "otrain.txt.model", "oout.txt");
            CmdTest.cmd("c.txt", "ctrain.txt.model", "cout.txt");
            CmdTest.cmd("e.txt", "etrain.txt.model", "eout.txt");
            CmdTest.cmd("a.txt", "atrain.txt.model", "aout.txt");
            CmdTest.cmd("n.txt", "ntrain.txt.model", "nout.txt");
        } catch (ClassNotFoundException | SQLException err) {
            System.out.println("ERROR: " + err);
        }
        result = "true";
        return result;
    }
}
