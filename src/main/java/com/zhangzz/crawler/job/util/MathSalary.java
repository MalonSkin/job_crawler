package com.zhangzz.crawler.job.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 51job 将工资字符串转为数字
 * @Author zhangzz
 * @Create 2019/5/12 17:47
 */
public class MathSalary {

    /**
     * 获取薪资范围
     * @param salaryStr 薪资字符串
     * @return
     */
    public static Integer[] getSalary(String salaryStr) {
        // 声明存放薪资范围的数组
        Integer[] salary = new Integer[2];
        if (StringUtils.isBlank(salaryStr)) {
            return salary;
        }

        // 500/天
        // 0.8-1.2万/月
        // 5-8千/月
        // 5-6万/年
        String date = salaryStr.substring(salaryStr.length() - 1, salaryStr.length());
        // 如果是按天，则乘以240进行计算
        if (!"月".equals(date) && !"年".equals(date)) {
            salaryStr = salaryStr.substring(0, salaryStr.length() - 2);
            salary[0] = salary[1] = str2Num(salaryStr, 240);
            return salary;
        }

        String unit = salaryStr.substring(salaryStr.length() - 3, salaryStr.length() - 2);
        String[] salarys = salaryStr.substring(0, salaryStr.length() - 3).split("-");

        salary[0] = mathSalary(date, unit, salarys[0]);
        salary[1] = mathSalary(date, unit, salarys[1]);

        return salary;
    }

    /**
     * 根据条件计算薪资
     * @param date 日期单位
     * @param unit 金额单位
     * @param salaryStr 薪资字符串
     * @return
     */
    private static Integer mathSalary(String date, String unit, String salaryStr) {
        Integer salary = 0;
        // 判断单位是否为万
        if ("万".equals(unit)) {
            salary = str2Num(salaryStr, 10000);
        } else {
            salary = str2Num(salaryStr, 1000);
        }

        // 判断时间是否为月
        if ("月".equals(date)) {
            salary = str2Num(salary.toString(), 12);
        }
        return salary;
    }

    private static Integer str2Num(String salaryStr, int num) {
        // 用number接收，否则会有精度丢失的问题
        try {
            Number result = Float.parseFloat(salaryStr) * num;
            return result.intValue();
        } catch (Exception e) {
            return 0;
        }
    }
}
