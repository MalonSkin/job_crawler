package com.zhangzz.crawler.job.service;

import com.zhangzz.crawler.job.pojo.JobInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author zhangzz
 * @Create 2019/5/12 16:20
 */
public interface JobInfoService {

    /**
     * 保存职位信息
     * @param jobInfo 职位信息
     */
    void save(JobInfo jobInfo);

    /**
     * 查询
     * @param jobInfo 条件
     * @return 职位信息列表
     */
    List<JobInfo> findAll(JobInfo jobInfo);

    /**
     * 分页查询
     * @param page 页码
     * @param size 每页条数
     * @return 职位信息列表
     */
    Page<JobInfo> findByPage(int page, int size);

}
