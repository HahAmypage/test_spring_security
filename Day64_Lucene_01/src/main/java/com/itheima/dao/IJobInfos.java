package com.itheima.dao;

import com.itheima.entity.JobInfos;

import java.util.List;

public interface IJobInfos {
    /**
     * 查询全部招聘信息
     */
    List<JobInfos> findAll();
}
