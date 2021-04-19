package com.zc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zc.entity.Student;

/**
 * @date 2021-3-10
 * @author z
 * 
 * 查询学生信息根据id
 * 添加学生
 * 查询全部学生
 * 删除学生
 * 更新学生
 * 查询学生信息根据 编号、姓名、编号和姓名
 * 
 * @date 2021-3-12
 * @author z
 * 根据no查询学生信息
 *
 */
public interface IStudentDao {
	Student selectStudent(int id);
	int addStudent(Student student);
	List<Student> showAllStudent();
	int deleteStudent(int id);
	int updateStudent(Student student);
	
	List<Student> showStudentOne1(String studentNo);
	List<Student> showStudentOne2(String studentName);
	List<Student> showStudentOne3(@Param("studentNo") String studentNo,@Param("studentName") String studentName);
	
	Student getInfoByNo(String studentNo);
	
	
}
