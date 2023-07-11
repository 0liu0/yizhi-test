package com.yizhi.student.controller;

import java.util.*;

import com.yizhi.common.annotation.Log;
import com.yizhi.common.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.yizhi.student.domain.StudentInfoDO;
import com.yizhi.student.service.StudentInfoService;

/**
 * 生基础信息表
 */

@Controller
@RequestMapping("/student/studentInfo")
public class StudentInfoController {
    @Autowired
    private StudentInfoService studentInfoService;

    //
    @Log("学生信息保存")
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("student:studentInfo:add")
    public R save(StudentInfoDO studentInfoDO) {
        // 校验
        if (studentInfoDO == null) {
            return R.error("参数错误");
        }
        studentInfoService.save(studentInfoDO);
        return R.ok("保存成功！");
    }

    /**
     * 可分页 查询
     */
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("student:studentInfo:studentInfo")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        // 校验
        if (params.isEmpty()) {
            return new PageUtils(null,0);
        }
        // 类型转换
        int currPage = Integer.parseInt((String) params.get("currPage"));
        int pageSize = Integer.parseInt((String) params.get("pageSize"));
        // 分页查询offset
        int offset = (currPage-1)*pageSize;
        params.put("currPage",offset);
        params.put("pageSize",pageSize);
        int total = studentInfoService.count(params);
        List<StudentInfoDO> list = studentInfoService.list(params);
        return new PageUtils(list,total,currPage,pageSize);
    }


    /**
     * 修改
     */
    @Log("学生基础信息表修改")
    @ResponseBody
    @PostMapping("/update")
    @RequiresPermissions("student:studentInfo:edit")
    public R update(StudentInfoDO studentInfo) {
        // 校验
        if (studentInfo==null) {
            return R.error("参数异常");
        }
        System.out.println("studentInfo::::::"+studentInfo);
        studentInfoService.update(studentInfo);
        return R.ok();
    }

    /**
     * 删除
     */
    @Log("学生基础信息表删除")
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("student:studentInfo:remove")
    public R remove(Integer id) {
        // 校验
        if (id==null) {
            R.error("参数异常");
        }
        studentInfoService.remove(id);
        return R.ok();
    }

    /**
     * 批量删除
     */
    @Log("学生基础信息表批量删除")
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("student:studentInfo:batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] ids) {
        // 校验
        if (ids.length==0) {
            return R.error("参数异常");
        }
        System.out.println("ids"+ Arrays.toString(ids));
        studentInfoService.batchRemove(ids);
        return R.ok();
    }


    //前后端不分离 客户端 -> 控制器-> 定位视图

    /**
     * 学生管理 点击Tab标签 forward页面
     */
    @GetMapping()
    @RequiresPermissions("student:studentInfo:studentInfo")
    String StudentInfo() {
        return "student/studentInfo/studentInfo";
    }

    /**
     * 更新功能 弹出View定位
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("student:studentInfo:edit")
    String edit(@PathVariable("id") Integer id, Model model) {
        StudentInfoDO studentInfo = studentInfoService.get(id);
        model.addAttribute("studentInfo", studentInfo);
        return "student/studentInfo/edit";
    }

    /**
     * 学生管理 添加学生弹出 View
     */
    @GetMapping("/add")
    @RequiresPermissions("student:studentInfo:add")
    String add() {
        return "student/studentInfo/add";
    }
}
