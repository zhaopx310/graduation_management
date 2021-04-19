package com.zc.dao;

import java.util.List;

import com.zc.entity.ThesisPaper;

/**
 * @date 2021-3-18
 * @author z
 * 添加最终论文
 * 查询所有论文
 *
 */

public interface IThesisPaperDao {
	
	int addThesisPaper(ThesisPaper paper);
	
	List<ThesisPaper> getAllInfo();
	
	
}
