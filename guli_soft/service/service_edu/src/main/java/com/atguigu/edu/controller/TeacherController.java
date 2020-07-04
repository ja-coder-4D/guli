package com.atguigu.edu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.entity.vo.TeacherQuery;
import com.atguigu.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.x509.RDN;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-04-13
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/select")
    public R selectAll(){
         List<Teacher> list = teacherService.list(null);
         return R.ok().data("items",list);
    }

    @ApiOperation(value = "删除讲师")
    @DeleteMapping("/remove/{id}")
    public R deleteById(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "讲师分页查询")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageTeacher(@ApiParam(name = "current",value = "当前页",required = true) @PathVariable long current,
                         @ApiParam(name = "limit",value = "每页个数",required = true) @PathVariable long limit ){
        Page<Teacher> page = new Page<Teacher>(current,limit);
        teacherService.page(page,null);
        long total = page.getTotal();
        List<Teacher> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "讲师多条件分页查询")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current",value = "当前页",required = true) @PathVariable long current,
                         @ApiParam(name = "limit",value = "每页个数",required = true) @PathVariable long limit,
                         @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<Teacher> pageTeacher = new Page<Teacher>(current,limit);
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<Teacher>();
        if (!StringUtils.isEmpty(teacherQuery.getName())) {
            queryWrapper.like("name",teacherQuery.getName());
        }
        if (!StringUtils.isEmpty(teacherQuery.getLevel())) {
            queryWrapper.eq("level",teacherQuery.getLevel());
        }
        if (!StringUtils.isEmpty(teacherQuery.getBegin())) {
            queryWrapper.ge("gmt_create",teacherQuery.getBegin());
        }
        if (!StringUtils.isEmpty(teacherQuery.getEnd())) {
            queryWrapper.le("gmt_create",teacherQuery.getEnd());
        }
        teacherService.page(pageTeacher,queryWrapper);
        long total = pageTeacher.getTotal();
        List<Teacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "讲师添加")
    @PostMapping("/addTeacher")
    public R teacherAdd(@RequestBody Teacher teacher){
        boolean b = teacherService.save(teacher);
        if (b) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "讲师查询通ID")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id) {
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    @ApiOperation(value = "讲师修改")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher){
        boolean b = teacherService.updateById(teacher);
        if (b) {
            return R.ok();
        }else {
            return R.error();
        }
    }

}

