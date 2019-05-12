package com.zhangzz.crawler.job.service.impl;

import com.zhangzz.crawler.job.dao.JobInfoDAO;
import com.zhangzz.crawler.job.pojo.JobInfo;
import com.zhangzz.crawler.job.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author zhangzz
 * @Create 2019/5/12 16:22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoDAO jobInfoDAO;

    @Override
    public void save(JobInfo jobInfo) {
        JobInfo param = new JobInfo();
        param.setUrl(jobInfo.getUrl());
        param.setTime(jobInfo.getTime());
        List<JobInfo> list = findAll(param);
        if (list.isEmpty()) {
            jobInfoDAO.saveAndFlush(jobInfo);
        }
    }

    @Override
    public List<JobInfo> findAll(JobInfo jobInfo) {
        Example<JobInfo> example = Example.of(jobInfo);
        return jobInfoDAO.findAll(example);
    }

    @Override
    public Page<JobInfo> findByPage(int page, int size) {
        return jobInfoDAO.findAll(PageRequest.of(page, size));
    }
}
