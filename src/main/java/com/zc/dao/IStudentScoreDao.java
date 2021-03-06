package com.zc.dao;

import com.zc.entity.StudentScore;

/**
 * @date 2021-3-18
 * @author z
 * 添加学生成绩
 * 根据学生id查找学生成绩信息
 * 修改学生成绩
 * 
 *
 */

public interface IStudentScoreDao {
	
	int addStudenScore(StudentScore score);
	
	StudentScore showInfoByStudentId(int studentId);
	
	int modifyStudentScore(StudentScore score);
}
