package com.zhangzz.crawler.job.dao;

import com.zhangzz.crawler.job.pojo.JobInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 工作信息持久层接口
 * @Author zhangzz
 * @Create 2019/5/12 16:19
 */
public interface JobInfoDAO extends JpaRepository<JobInfo,Long> {
}
