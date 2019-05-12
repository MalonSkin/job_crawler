package com.zhangzz.crawler.job.controller;

import com.zhangzz.crawler.job.pojo.JobInfo;
import com.zhangzz.crawler.job.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhangzz
 * @Create 2019/5/12 19:08
 */
@RestController
@RequestMapping("/jobInfo")
public class JobInfoController {

    @Autowired
    private JobInfoService jobInfoService;

    /**
     * 获取所有工作信息
     * @return 工作信息列表
     */
    @RequestMapping("/findAll")
    public Map<String,Object> findAll(int page, int size) {
        if (page < 0) {
            page = 0;
        } else {
            page = page - 1;
        }
        Page<JobInfo> jobInfoPage = jobInfoService.findByPage(page, size);
        Map<String, Object> map = new HashMap<>();
        map.put("total", jobInfoPage.getTotalElements());
        map.put("rows", jobInfoPage.getContent());
        return map;
    }
}
