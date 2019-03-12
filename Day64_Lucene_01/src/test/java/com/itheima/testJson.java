package com.itheima;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.entity.JobInfos;
import com.sun.deploy.model.Resource;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoader;

import java.io.*;

public class testJson {
    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("F:\\learning\\Java\\EmploymentClass\\MyCode\\Day64_Lucene_01\\src\\main\\resources\\java_jobs_20181015_111559.json");
            char[] buffer = new char[1024];
            int len = 0;
            String jsonString="";
            while((len = reader.read(buffer))!=-1){
                String json = new String(buffer,0,len);
                System.out.println(json);
                jsonString=json;
                ObjectMapper objectMapper = new ObjectMapper();
                JobInfos jobInfos = objectMapper.readValue(jsonString,JobInfos.class);
                System.out.println(jobInfos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
