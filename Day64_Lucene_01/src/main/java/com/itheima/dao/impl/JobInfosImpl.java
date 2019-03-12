package com.itheima.dao.impl;

import com.itheima.dao.IJobInfos;
import com.itheima.entity.JobInfos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobInfosImpl implements IJobInfos {
    //创建结果结合
    List<JobInfos> jobInfosList = new ArrayList<>();
    /**
     * 查询全部招聘信息
     */

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    @Override
    public List<JobInfos> findAll() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/51job_info","root","chanyx123");
            String sql = "select * from jobs_test";
            ps =  connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                JobInfos jobInfos = new JobInfos();
                jobInfos.setDbid(rs.getInt("dbid"));
                jobInfos.setId(rs.getString("id"));
                jobInfos.setDatasource(rs.getString("datasource"));
                jobInfos.setKeyword(rs.getString("keyword"));
                jobInfos.setJobid(rs.getString("jobid"));
                jobInfos.setJobname(rs.getString("jobname"));
                jobInfos.setJobsalary(rs.getString("jobsalary"));
                jobInfos.setArea(rs.getString("area"));
                jobInfos.setExperience(rs.getString("experience"));
                jobInfos.setEducation(rs.getString("education"));
                jobInfos.setPulishdte(rs.getString("pulishdte"));
                jobInfos.setOthercondition_1(rs.getString("othercondition_1"));
                jobInfos.setOthercondition_2(rs.getString("othercondition_2"));
                jobInfos.setOthercondition_3(rs.getString("othercondition_3"));
                jobInfos.setOthercondition_4(rs.getString("othercondition_4"));
                jobInfos.setJobinfo(rs.getString("jobinfo"));
                jobInfos.setCompanyname(rs.getString("companyname"));
                jobInfos.setCompanypropery(rs.getString("companypropery"));
                jobInfos.setCompanyscale(rs.getString("companyscale"));
                jobInfos.setCompanyindustry(rs.getString("companyindustry"));

                jobInfosList.add(jobInfos);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (connection!=null) connection.close();
                if (ps!=null) ps.close();
                if (rs!=null) rs.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return jobInfosList;
    }
}
