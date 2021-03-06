package com.zc.dao;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zc.BaseTest;
import com.zc.entity.Doubt;
import com.zc.entity.Student;
import com.zc.entity.StudentScore;
import com.zc.entity.StudentTaskBookOpening;
import com.zc.entity.ThesisInformation;
import com.zc.entity.ThesisPaper;

public class IStudentDaoTest extends BaseTest{

	private static final Logger log = Logger.getLogger(IStudentDaoTest.class);

	@Autowired
	private IStudentDao studentDao;
	
	@Autowired
	private IStudentTaskBookOpeningDao studentTaskBookOpeningDao;
	
	@Autowired
	private IThesisInformationDao thesisInformationDao;
	
	@Autowired
	private IStudentScoreDao studentScoreDao;
	
	@Autowired
	private IThesisPaperDao thesisPaperDao;
	
	@Autowired
	private IDoubtDao doubtDao;
	
	@Test
	public void testSelectStudent() {
		Date time = new Date();
		log.info(time);
		
		Student s1 = studentDao.selectStudent(5);
		log.info(s1);
	}
	
	@Test
	public void testAddStudent() {
		Date time = new Date();
		Student s3 = new Student();
		s3.setStudentNo("14004");
		s3.setStudentName("СС");
		s3.setSex("男");
		s3.setMajorId(1);
		s3.setGrade("大四");
		int num = studentDao.addStudent(s3);
		log.info(num);
	}
	
	@Test
	public void test2() {
		List<Student> students = studentDao.showAllStudent();
		log.info(students);
		
	}
	@Test
	public void test3() {
		Date time = new Date();
		Student s3 = new Student();
		s3.setId(12);
		s3.setStudentNo("test01");
		s3.setStudentName("СС");
		s3.setSex("女");
		s3.setMajorId(1);
		s3.setGrade("大四");
		s3.setLastModifyTime(time);
		int num = studentDao.updateStudent(s3);
		log.info(num);
	}
	
	@Test
	public void test4() {
		List<Student> students = studentDao.showStudentOne1("test01");
		List<Student> students1 = studentDao.showStudentOne2("丽丽");
		List<Student> students2 = studentDao.showStudentOne3("142001","张三");
		
		log.info(students);
		log.info(students1);
		log.info(students2);
	}
	@Test
	public void test5() {
		int num = studentDao.deleteStudent(12);
		log.info(num);
		
	}
	@Test
	public void test6() {
		Student s = studentDao.getInfoByNo("test01");
		log.info(s);
		
	}
	
	@Test
	public void test7() {
		int num = studentTaskBookOpeningDao.addInfoByStudentId(9);
		log.info(num);
	}
	
	@Test
	public void test8() {
		// StudentTaskBookOpening stbo = studentTaskBookOpeningDao.showInfoByStudentId(10);
		StudentTaskBookOpening stbo = studentTaskBookOpeningDao.showInfoByStudentId(9);
		log.info(stbo);
	}
	
	@Test
	public void test9() {
		int num = studentTaskBookOpeningDao.uploadTaskBook(9, "E:\\BSM\\admin\\test\\red.jpg");
		log.info(num);
	}
	
	@Test
	public void test10() {
		int num = studentTaskBookOpeningDao.uploadOpening(9, "E:\\BSM\\admin\\test\\red.jpg");
		log.info(num);
	}
	
	@Test
	public void test11() {
		//int num = studentTaskBookOpeningDao.resetTaskBook(9);
		int num = studentTaskBookOpeningDao.resetOpening(9);
		log.info(num);
	}
	
	@Test
	public void test12() {
		//int num = studentTaskBookOpeningDao.resetTaskBook(9);
		StudentTaskBookOpening ss = studentTaskBookOpeningDao.getInfoByTaskBookPath("E:\\BSM\\student\\9pic1.jpg");
		// StudentTaskBookOpening ss = studentTaskBookOpeningDao.getInfoByOpeningPath("E:\\BSM\\student\\9pic1.jpg");
		log.info(ss);
	}
	
	@Test
	public void test13() {
		int num = studentTaskBookOpeningDao.failOpening(9);
		// int num = studentTaskBookOpeningDao.passOpening(9);
		log.info(num);
	}
	
	@Test
	public void test14() {
		ThesisInformation thesis = new ThesisInformation();
		thesis.setStudentId(9);
		thesis.setThesisText("E:\\BSM\\student\\9");
		thesis.setDescription("这是描述");
		
		int num = thesisInformationDao.addThesisInformation(thesis);
		log.info(num);
	}
	
	@Test
	public void test15() {
		
		int num = thesisInformationDao.failThesisInformation(9);
		//int num = thesisInformationDao.passThesisInformation(9);
		// int num = studentTaskBookOpeningDao.passOpening(9);
		log.info(num);
	}
	
	@Test
	public void test16() {
		
		int num = thesisInformationDao.deleteThesisInformation(9);
		//int num = thesisInformationDao.passThesisInformation(9);
		// int num = studentTaskBookOpeningDao.passOpening(9);
		log.info(num);
	}
	
	@Test
	public void test17() {
		
		ThesisInformation thesis = thesisInformationDao.getInfoByStudentId(9);
		log.info(thesis);
	}
	@Test
	public void test18() {
		
		ThesisInformation thesis = thesisInformationDao.getInfoByFilePath("E:\\BSM\\student\\9");
		log.info(thesis);
	}
	
	@Test
	public void test19() {
		StudentScore s = new StudentScore();
		s.setStudentId(9);
		s.setThesisResult(85);
		s.setInputMan("t_01");
		
		int num = studentScoreDao.addStudenScore(s);
		log.info(num);
		
	}
	
	@Test
	public void test20() {
		
		StudentScore s = studentScoreDao.showInfoByStudentId(9);
		log.info(s);
	}
	
	@Test
	public void test21() {
		StudentScore s = new StudentScore();
		s.setStudentId(9);
		s.setThesisResult(95);
		s.setInputMan("t_02");
		
		
		int num = studentScoreDao.modifyStudentScore(s);
		log.info(num);
	}
	
	@Test
	public void test22() {
		ThesisPaper paper = new ThesisPaper();
		paper.setStudentId(9);
		paper.setPaperInfo("E:\\BSM\\teacher\\4\\3");
		int num = thesisPaperDao.addThesisPaper(paper);
		log.info(num);
		
	}
	@Test
	public void test23() {
		Doubt d = new Doubt();
		d.setId(1);
		d.setStudentId(9);
		d.setStudentDoubt("开题报告的格式是什么");
		int num = doubtDao.addDoubt(d);
		log.info(num);
		
		
	}
	
	@Test
	public void test24() {
		List<Doubt> doubtList = doubtDao.getAllDoubt(9);
		log.info(doubtList);
	}
	
	@Test
	public void test25() {
		Doubt d = new Doubt();
		d.setId(7);
		d.setAnswer("这是解决方案");
		int num = doubtDao.updateDoubt(d);
		log.info(num);
	}
}
