package com.zc.dao;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.zc.BaseTest;
import com.zc.entity.Announcement;
import com.zc.entity.User;

public class IUserDaoTest extends BaseTest{

	private static final Logger log = Logger.getLogger(IUserDaoTest.class);

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IDepartmentDao departmentDao;
	
	@Autowired
	private IMajorDao majorDao;
	
	@Autowired
	private IAnnouncementDao announcementDao;
	
	@Test
	public void testQuery() throws Exception{
		int id = 8;
		User user = userDao.queryById(id);
		log.info(user);
	}
	
	@Test
	public void testLogin() {
		User user = new User();
		user.setUserNo("14001");
		user.setPassword("123456");
		user.setPermission(1);
		User currentUser = userDao.login(user);
		log.info(currentUser.getId());
	}
	
	@Test
	public void testGetPassword() {
		
		String  userNo = "admin";
		User user = userDao.getPassword(userNo);
		log.info(user);
	}
	@Test
	public void getDepartmentName() {
		String name = departmentDao.getNameById(45);
		log.info(name);
	}
	
	@Test
	public void getId() {
		int id = departmentDao.getIdByName("机电工程学院");
		log.info(id);
	}
	@Test
	public void testMajorName() throws Exception{
		String name = majorDao.getNameByID(2);
		log.info(name);
	}
	@Test
	public void testMajorId() throws Exception{
		int id = majorDao.getIdByName("机械工程专业");
		log.info(id);
	}
	
	@Test
	public void test1() {
		
		Announcement announcement = new Announcement();
		announcement.setContext("测试公告");
		announcement.setInputMan("admin");
		Date time = new Date();
		announcement.setLastModifyTime(time);
		
		int num = announcementDao.addAnnouncement(announcement);
		log.info(num);
		
	}
	
	@Test
	public void test2() {
		int num = announcementDao.deleteAnnouncement(1);
		log.info(num);
	}
	
	@Test
	public void test3() {
		
		
		List<Announcement> ann = announcementDao.showAllAnnouncement();
		log.info(ann);
		
	}
	
	
}
