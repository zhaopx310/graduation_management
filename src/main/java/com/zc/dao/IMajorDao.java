package com.zc.dao;

import java.util.List;

import com.zc.entity.Major;

/**
 * @date 2021-3-10
 * @author z
 * 查询全部的专业；
 * id ==> name
 * name ==> id
 * 根据name获得id
 * 
 */

public interface IMajorDao {
	
	List<Major> getAllMajor();
	
	String getNameByID(int id);
	
	int getIdByName(String name);
	
	
}
