/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vit.personalitycheck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;

/**
 *
 * @author Amitabh
 */
public class test {
    public test()
    {
        System.out.println("Created object test");
    }
    public static String removeStopWordsAndStem(String input) throws IOException {
        Set<String> stopWords = new HashSet<String>();
        stopWords.add("a");
        stopWords.add("I");
        stopWords.add("the");
        stopWords.add("an");
        stopWords.add("and");
        stopWords.add("are");
        stopWords.add("as");
        stopWords.add("at");
        stopWords.add("be");
        stopWords.add("but");
        stopWords.add("by");
        stopWords.add("for");
        stopWords.add("if");
        stopWords.add("in");
        stopWords.add("into");
        stopWords.add("is");
        stopWords.add("it");
        stopWords.add("no");
        stopWords.add("not");
        stopWords.add("of");
        stopWords.add("on");
        stopWords.add("or");
        stopWords.add("such");
        stopWords.add("that");
        stopWords.add("their");
        stopWords.add("then");
        stopWords.add("there");
        stopWords.add("these");
        stopWords.add("they");
        stopWords.add("this");
        stopWords.add("to");
        stopWords.add("was");
        stopWords.add("will");
        stopWords.add("with");
        TokenStream tokenStream = new StandardTokenizer(
                Version.LUCENE_30, new StringReader(input));
        tokenStream = new StopFilter(true, tokenStream, stopWords);
        tokenStream = new PorterStemFilter(tokenStream);

        StringBuilder sb = new StringBuilder();
        TermAttribute termAttr = tokenStream.getAttribute(TermAttribute.class);
        while (tokenStream.incrementToken()) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(termAttr.term());
        }
        return sb.toString();
    }

    public static double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word)) {
                result++;
            }
        }
        return result / doc.size();
    }

    public static double idf(List<List<String>> docs, String term) {
        double n = 0;
        for (List<String> doc : docs) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(docs.size() / (n + 1));
    }

    public static double tfIdf(List<String> doc, List<List<String>> docs, String term) {
        return tf(doc, term) * idf(docs, term);

    }
    public static void main(String args[]) throws IOException {
        BufferedReader br = null;
        String paragraph = "";
        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader("C:\\Users\\Amitabh\\Desktop\\hello.txt"));

            while ((sCurrentLine = br.readLine()) != null) {
                //System.out.println(sCurrentLine);
                paragraph = paragraph.concat(sCurrentLine);
            }
            
        } catch (IOException e) {
            e.printStackTrace();

        }
        System.out.println("Input:" + paragraph);
        removeStopWordsAndStem(paragraph);
        
    }
}
