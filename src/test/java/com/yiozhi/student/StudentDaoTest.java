package com.yiozhi.student;

import com.yizhi.student.dao.StudentInfoDao;
import com.yizhi.student.domain.StudentInfoDO;
import com.yizhi.yizhiApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 刘彻
 * @Date 2023/7/10 21:10
 * @PackageName: com.yiozhi.student
 * @ClassName: StudentDaoTest
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = yizhiApplication.class)
public class StudentDaoTest {
    @Autowired
    private StudentInfoDao studentInfoDao;

    @Test
    public void testGet() {
        StudentInfoDO studentInfoDO = studentInfoDao.get(5);
        System.out.println(studentInfoDO);
    }
    @Test
    public void testList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","大");
//        map.put("tocollegeId","2");
        map.put("currPage",1);
        map.put("pageSize",10);
        List<StudentInfoDO> list = studentInfoDao.list(map);
        System.out.println("List<StudentInfoDO> list = studentInfoDao.list(map);");
        System.out.println(list);
    }

    @Test
    public void testCount() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("classId","1");
        map.put("tocollegeId","2");
        int count = studentInfoDao.count(map);
        System.out.println("=====================count::::"+count);
    }
}
