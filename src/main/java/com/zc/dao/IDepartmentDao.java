package com.zc.dao;

import java.util.List;

import com.zc.entity.Department;

/**
 * @date 2021-3-10
 * @author z
 * 查询全部的院系
 * id ==> name
 * name ==> id
 * 
 */
public interface IDepartmentDao {
	
	List<Department> allDepartment();
	String getNameById(int id);
	int getIdByName(String name);
}
