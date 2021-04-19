package com.zc.dao;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.zc.BaseTest;
import com.zc.entity.Topic;

public class ITopicDaoTest extends BaseTest{

	private static final Logger log = Logger.getLogger(ITopicDaoTest.class);

	@Autowired
	private ITopicDao topicDao;
	
	@Test
	public void test1() throws Exception{
		List<Topic> topicList = topicDao.showAllTopic();
		log.info(topicList);
	}
	
	@Test
	public void test2() throws Exception{
		Topic t = topicDao.topicByStudentId(9);
		log.info(t);
	}
	
	@Test
	public void test3() throws Exception{
		Topic topic= new Topic();
		topic.setThesisId(4);
		topic.setStudentId(15);
		Date time = new Date();
		topic.setSelectTime(time);
		
		int num = topicDao.addTopic(topic);
		log.info(num);
	}
	
	@Test
	public void test4(){
		int num = topicDao.deleteTopic(9);
		log.info(num);
	}
}
