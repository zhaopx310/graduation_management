package com.zc.dao;

import java.util.List;

import com.zc.entity.Doubt;

/**
 * @date 2021-3-9
 * @author z
 * 
 * 添加学生提出的疑惑
 * 显示某一学生的所有疑惑
 * 
 * @date 2021-3-10
 * 更新疑惑信息
 *
 */
public interface IDoubtDao {
	
	int addDoubt(Doubt doubt);
	List<Doubt> getAllDoubt(int studentId);
	
	int updateDoubt(Doubt doubt);
	
	
}
