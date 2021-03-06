package com.zc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zc.dao.IAnnouncementDao;
import com.zc.dao.IDoubtDao;
import com.zc.dao.IStudentDao;
import com.zc.dao.IStudentScoreDao;
import com.zc.dao.IStudentTaskBookOpeningDao;
import com.zc.dao.ITeacherDao;
import com.zc.dao.ITeacherProgressDao;
import com.zc.dao.ITeacherTaskBookOpeningDao;
import com.zc.dao.IThesisInformationDao;
import com.zc.dao.IThesisPaperDao;
import com.zc.dao.IThesisTitleDao;
import com.zc.dao.ITopicDao;
import com.zc.dao.IZhiyuanDao;
import com.zc.entity.Announcement;
import com.zc.entity.Doubt;
import com.zc.entity.Student;
import com.zc.entity.StudentScore;
import com.zc.entity.StudentTaskBookOpening;
import com.zc.entity.Teacher;
import com.zc.entity.TeacherProgress;
import com.zc.entity.TeacherTaskBookOpening;
import com.zc.entity.ThesisInformation;
import com.zc.entity.ThesisPaper;
import com.zc.entity.ThesisTitle;
import com.zc.entity.Topic;
import com.zc.entity.Zhiyuan;
import com.zc.service.ITeacherService;

@Service
public class TeacherServiceImpl implements ITeacherService {

	private static final Logger log = Logger.getLogger(TeacherServiceImpl.class);

	@Autowired
	private ITeacherDao teacherDao;
	
	@Autowired
	private IThesisTitleDao thesisTitleDao;
	
	@Autowired
	private IZhiyuanDao zhiyuanDao;
	
	@Autowired
	private ITeacherTaskBookOpeningDao teacherTaskBookOpeningDao;
	
	@Autowired
	private ITopicDao topicDao;
	
	@Autowired
	private IStudentDao studentDao;
	
	@Autowired
	private IStudentTaskBookOpeningDao studentTaskBookOpeningDao;
	
	@Autowired
	private ITeacherProgressDao teacherProgressDao;
	
	@Autowired
	private IAnnouncementDao announcementDao;
	
	@Autowired
	private IThesisInformationDao thesisInformationDao;
	
	@Autowired
	private IStudentScoreDao studentScoreDao;
	
	@Autowired
	private IThesisPaperDao thesisPaperDao;
	
	@Autowired
	private IDoubtDao doubtDao;
	
	public int addTeacher(Teacher teacher) {
		int addNum = teacherDao.addTeacher(teacher);
		return addNum;
	}


	public List<Teacher> showAllTeacher() {
		List<Teacher> teachers = teacherDao.showAllTeacher();
		return teachers;
	}


	public int deleteTeacher(int id) {
		int num = teacherDao.deleteTeacher(id);
		return num;
	}


	public Teacher getTeacherByid(int id) {
		Teacher teacher = teacherDao.selectTeacher(id);
		return teacher;
	}


	public int updateTeacher(Teacher teacher) {
		int num = teacherDao.updateTeacher(teacher);
		return num;
	}


	public List<Teacher> showTeacherOne1(String teacherNo) {
		// TODO Auto-generated method stub
		List<Teacher> teachers = teacherDao.showTeacherOne1(teacherNo);
		
		return teachers;
	}


	public List<Teacher> showTeacherOne2(String teacherName) {
		// TODO Auto-generated method stub
		List<Teacher> teachers = teacherDao.showTeacherOne2(teacherName);
		return teachers;
	}


	public List<Teacher> showTeacherOne3(String teacherNo, String teacherName) {
		// TODO Auto-generated method stub
		List<Teacher> teachers = teacherDao.showTeacherOne3(teacherNo, teacherName);
		return teachers;
	}


	public Teacher showInfoByNo(String teacherNo) {
		// TODO Auto-generated method stub
		Teacher teacher = teacherDao.teacherInfoByNo(teacherNo);
		return teacher;
	}


	public int uploadThesisTitle(ThesisTitle thesisTitle) {
		// TODO Auto-generated method stub
		int num = thesisTitleDao.addThesisTitle(thesisTitle);
		return num;
	}


	public List<ThesisTitle> showAllThesisTitle(int teacherId) {
		// TODO Auto-generated method stub
		List<ThesisTitle> thesisList = thesisTitleDao.showAllThesisTitle(teacherId);
		return thesisList;
	}
	
	public List<Map> showAllZhiyuan(int teacherId) {
		// TODO Auto-generated method stub
		List<Map> zhiyuanList = zhiyuanDao.showAllZhiyuan(teacherId);
		return zhiyuanList;
	}


	public ThesisTitle getThesisById(int id) {
		// TODO Auto-generated method stub
		ThesisTitle thesisTitle = thesisTitleDao.getThesisById(id);
		return thesisTitle;
	}


	public int updateThesisTitle(ThesisTitle thesisTitle) {
		// TODO Auto-generated method stub
		int num = thesisTitleDao.updateThesisTitle(thesisTitle);
		return num;
	}


	public int deleteThesisTitle(int id) {
		// TODO Auto-generated method stub
		int num = thesisTitleDao.deleteThesisTitle(id);
		return num;
	}


	public List<ThesisTitle> showAllThesisTitle() {
		// TODO Auto-generated method stub
		List<ThesisTitle> thesisList = thesisTitleDao.showAllThesisTitleAd();
		return thesisList;
	}


	public int agreeThesisTitle(int id) {
		// TODO Auto-generated method stub
		int num = thesisTitleDao.agreeThesis(id);
		return num;
	}


	public int disagreeThesisTitle(int id) {
		// TODO Auto-generated method stub
		int num = thesisTitleDao.disagreeThesis(id);
		return num;
	}

	
	public List<ThesisTitle> showAllThesisTitleBeChecked(int teacherId) {
		// TODO Auto-generated method stub
		List<ThesisTitle> allThesisList = thesisTitleDao.showAllThesisTitle(teacherId);
		for(int i=0;i<allThesisList.size();i++) {
			if(allThesisList.get(i).getStatus() != 2 ) {
				allThesisList.remove(i);
			}
		}
		return allThesisList;
	}
	

	public int uploadTaskBook(TeacherTaskBookOpening teacherTaskBookOpening) {
		// TODO Auto-generated method stub
		
		TeacherTaskBookOpening dbTTBO = teacherTaskBookOpeningDao.showInfo(teacherTaskBookOpening.getThesisTitleId());
		if(dbTTBO == null || "".equals(dbTTBO)) {
			int num = teacherTaskBookOpeningDao.addTaskBook(teacherTaskBookOpening);
			return num;
		}else {
			int num = teacherTaskBookOpeningDao.uploadTaskBook(teacherTaskBookOpening);
			return num;
		}
		
	}


	public int uploadOpening(TeacherTaskBookOpening teacherTaskBookOpening) {
		TeacherTaskBookOpening dbTTBO = teacherTaskBookOpeningDao.showInfo(teacherTaskBookOpening.getThesisTitleId());
		if(dbTTBO == null || "".equals(dbTTBO)) {
			int num = teacherTaskBookOpeningDao.addOpening(teacherTaskBookOpening);
			return num;
		}else {
			int num = teacherTaskBookOpeningDao.uploadOpening(teacherTaskBookOpening);
			return num;
		}
	}
	
	public int uploadShuJuKu(TeacherTaskBookOpening teacherTaskBookOpening) {
		TeacherTaskBookOpening dbTTBO = teacherTaskBookOpeningDao.showInfo(teacherTaskBookOpening.getThesisTitleId());
		if(dbTTBO == null || "".equals(dbTTBO)) {
			int num = teacherTaskBookOpeningDao.addShuJuKu(teacherTaskBookOpening);
			return num;
		}else {
			int num = teacherTaskBookOpeningDao.uploadShuJuKu(teacherTaskBookOpening);
			return num;
		}
	}
	
	public int uploadKeXingXing(TeacherTaskBookOpening teacherTaskBookOpening) {
		TeacherTaskBookOpening dbTTBO = teacherTaskBookOpeningDao.showInfo(teacherTaskBookOpening.getThesisTitleId());
		if(dbTTBO == null || "".equals(dbTTBO)) {
			int num = teacherTaskBookOpeningDao.addKeXingXing(teacherTaskBookOpening);
			return num;
		}else {
			int num = teacherTaskBookOpeningDao.uploadKeXingXing(teacherTaskBookOpening);
			return num;
		}
	}
	
	public int uploadXuQiu(TeacherTaskBookOpening teacherTaskBookOpening) {
		TeacherTaskBookOpening dbTTBO = teacherTaskBookOpeningDao.showInfo(teacherTaskBookOpening.getThesisTitleId());
		if(dbTTBO == null || "".equals(dbTTBO)) {
			int num = teacherTaskBookOpeningDao.addXuQiu(teacherTaskBookOpening);
			return num;
		}else {
			int num = teacherTaskBookOpeningDao.uploadXuQiu(teacherTaskBookOpening);
			return num;
		}
	}
	
	public int uploadGaiYao(TeacherTaskBookOpening teacherTaskBookOpening) {
		TeacherTaskBookOpening dbTTBO = teacherTaskBookOpeningDao.showInfo(teacherTaskBookOpening.getThesisTitleId());
		if(dbTTBO == null || "".equals(dbTTBO)) {
			int num = teacherTaskBookOpeningDao.addGaiYao(teacherTaskBookOpening);
			return num;
		}else {
			int num = teacherTaskBookOpeningDao.uploadGaiYao(teacherTaskBookOpening);
			return num;
		}
	}


	public Map<String, String> uploadTaskBookOpeningResult(int teacherId) {
		// TODO Auto-generated method stub
		
		List<TeacherTaskBookOpening> uploadFileList = teacherTaskBookOpeningDao.showTeacherTaskBookOpeningById(teacherId);
		Map<String, String> fileMap = new HashMap<String, String>();
		for(int i=0;i<uploadFileList.size();i++) {
			String filePath = uploadFileList.get(i).getTaskBook();
			if(filePath == null || "".equals(filePath)) {
				
			}else {
				String[] str = uploadFileList.get(i).getTaskBook().split("\\\\");
				String fileName = str[str.length-1].toString();
				fileMap.put(fileName, filePath);
			}
			
			String filePath1 = uploadFileList.get(i).getOpeningReport();
			if(filePath1 == null || "".equals(filePath1)) {
				
			}else {
				String[] str1 = uploadFileList.get(i).getOpeningReport().split("\\\\");
				String fileName1 = str1[str1.length-1].toString();
				fileMap.put(fileName1, filePath1);
			}
			
			String filePath2 = uploadFileList.get(i).getKeXingXing();
			if(filePath2 == null || "".equals(filePath2)) {
				
			}else {
				String[] str = uploadFileList.get(i).getKeXingXing().split("\\\\");
				String fileName = str[str.length-1].toString();
				fileMap.put(fileName, filePath2);
			}
			
			String filePath3 = uploadFileList.get(i).getXuQiu();
			if(filePath3 == null || "".equals(filePath3)) {
				
			}else {
				String[] str1 = uploadFileList.get(i).getXuQiu().split("\\\\");
				String fileName1 = str1[str1.length-1].toString();
				fileMap.put(fileName1, filePath3);
			}
			
			String filePath4 = uploadFileList.get(i).getGaiYao();
			if(filePath4 == null || "".equals(filePath4)) {
				
			}else {
				String[] str1 = uploadFileList.get(i).getGaiYao().split("\\\\");
				String fileName1 = str1[str1.length-1].toString();
				fileMap.put(fileName1, filePath4);
			}
			
			String filePath5 = uploadFileList.get(i).getShuJuKu();
			if(filePath5 == null || "".equals(filePath5)) {
				
			}else {
				String[] str1 = uploadFileList.get(i).getShuJuKu().split("\\\\");
				String fileName1 = str1[str1.length-1].toString();
				fileMap.put(fileName1, filePath5);
			}
		}
		return fileMap;
	}


	public String getThesisTitleByFilePath(String filePath) {
		// TODO Auto-generated method stub
		String[] str = filePath.split("\\\\");
		String thesisTitleId = str[str.length-2].toString();
		return thesisTitleId;
	}


	public TeacherTaskBookOpening getTaskOpeningByThesisId(int thesisTitleId) {
		// TODO Auto-generated method stub
		TeacherTaskBookOpening tt = teacherTaskBookOpeningDao.showInfoByThesisId(thesisTitleId);
		return tt;
	}


	public int deleteTaskBookOpening(int thesisTitleId) {
		// TODO Auto-generated method stub
		int num = teacherTaskBookOpeningDao.deleteInfo(thesisTitleId);
		log.info(num);
		return num;
	}

	public TeacherTaskBookOpening getThesisIdByTask(String taskBook) {
		// TODO Auto-generated method stub
		TeacherTaskBookOpening tt =teacherTaskBookOpeningDao.getTheisIdByTask(taskBook);
		
		return tt;
	}


	public TeacherTaskBookOpening getThesisIdByOpening(String opening) {
		// TODO Auto-generated method stub
		TeacherTaskBookOpening tt = teacherTaskBookOpeningDao.getTheisIdByOpening(opening);
		return tt;
	}


	public int resetTask(int thesisTitleId) {
		// TODO Auto-generated method stub
		int num = teacherTaskBookOpeningDao.resetTaskBook(thesisTitleId);
		
		return num;
	}


	public int resetOpening(int thesisTitleId) {
		// TODO Auto-generated method stub
		int num = teacherTaskBookOpeningDao.resetOpening(thesisTitleId);
		return num;
	}


	public List<Student> getAllStudentInfo(int teacherId) {
		// TODO Auto-generated method stub
		// thesisList2Db ?????????????????????????????????????????????????????????
		List<ThesisTitle> thesisList2Db = thesisTitleDao.showAllThesisTitle(teacherId);
		
		// thesisIdList ?????????????????????????????????????????????id
		List<Integer> thesisIdList = new ArrayList<Integer>();
		for(int i=0;i<thesisList2Db.size();i++) {
			if(thesisList2Db.get(i).getStatus()==2) {
				int thesisId = thesisList2Db.get(i).getId();
				thesisIdList.add(thesisId);
			}
		}
		
		// topicList ???????????????id
		List<Integer> topicList = new ArrayList<Integer>();
		for(int i=0;i<thesisIdList.size();i++) {
			Topic topic = topicDao.getInfoByThesisId(thesisIdList.get(i));
			if(topic==null || "".equals(topic)) {
				
			}else {
				int studentId = topic.getStudentId();
				topicList.add(studentId);
			}
		}
		
		List<Student> studentList = new ArrayList<Student>();
		for(int i=0;i<topicList.size();i++) {
			Student student = studentDao.selectStudent(topicList.get(i));
			studentList.add(student);
		}
		
		return studentList;
	}


	public Topic getTopicInfoByStudentId(int studentId) {
		// TODO Auto-generated method stub
		Topic topic = topicDao.topicByStudentId(studentId);
		return topic;
	}


	public ThesisTitle getThesisInfoByThesisId(int thesisId) {
		// TODO Auto-generated method stub
		ThesisTitle thesisTitle = thesisTitleDao.getThesisById(thesisId);
		
		return thesisTitle;
	}


	public StudentTaskBookOpening getStudentOpeningByStudentId(int studentId) {
		// TODO Auto-generated method stub
		
		StudentTaskBookOpening STBO = studentTaskBookOpeningDao.showInfoByStudentId(studentId);
		return STBO;
	}


	public int passOpening(String studentNo,String openscore) {
		// TODO Auto-generated method stub
		Student student = studentDao.getInfoByNo(studentNo);
		int studentId = student.getId();
		int num = studentTaskBookOpeningDao.passOpening(studentId,openscore);
		return num;
	}
	
	public int passKexing(String studentNo,String kexingscore) {
		// TODO Auto-generated method stub
		Student student = studentDao.getInfoByNo(studentNo);
		int studentId = student.getId();
		int num = studentTaskBookOpeningDao.passKexing(studentId,kexingscore);
		return num;
	}
	
	public int passXuqiu(String studentNo,String xuqiuscore) {
		// TODO Auto-generated method stub
		Student student = studentDao.getInfoByNo(studentNo);
		int studentId = student.getId();
		int num = studentTaskBookOpeningDao.passXuqiu(studentId,xuqiuscore);
		return num;
	}
	
	public int passGaiyao(String studentNo,String gaiyaoscore) {
		// TODO Auto-generated method stub
		Student student = studentDao.getInfoByNo(studentNo);
		int studentId = student.getId();
		int num = studentTaskBookOpeningDao.passGaiyao(studentId,gaiyaoscore);
		return num;
	}
	
	public int passShujuku(String studentNo,String shujukuscore) {
		// TODO Auto-generated method stub
		Student student = studentDao.getInfoByNo(studentNo);
		int studentId = student.getId();
		int num = studentTaskBookOpeningDao.passShujuku(studentId,shujukuscore);
		return num;
	}


	public int failOpening(String studentNo) {
		// TODO Auto-generated method stub
		Student student = studentDao.getInfoByNo(studentNo);
		int studentId = student.getId();
		int num = studentTaskBookOpeningDao.failOpening(studentId);
		return num;
	}


	public int addTeacherProgress(TeacherProgress teacherProgress) {
		// TODO Auto-generated method stub
		int num = teacherProgressDao.addTeacherProgress(teacherProgress);
		return num;
	}


	public List<TeacherProgress> getTeacherProgressByTeacherId(int teacherId) {
		// TODO Auto-generated method stub
		List<TeacherProgress> t = teacherProgressDao.getInfoByTeacherId(teacherId);
		
		return t;
	}


	public Student getStudentInfoByStudentId(int studentId) {
		// TODO Auto-generated method stub
		Student s = studentDao.selectStudent(studentId);
		
		return s;
	}


	public int passProgress(int id) {
		// TODO Auto-generated method stub
		int num = teacherProgressDao.passTeacherProgress(id);
		
		return num;
	}


	public int failProgress(int id) {
		// TODO Auto-generated method stub
		int num = teacherProgressDao.failTeacherProgress(id);
		return num;
	}


	public List<Announcement> showAllAnnouncement() {
		// TODO Auto-generated method stub
		List<Announcement> list = announcementDao.showAllAnnouncement();
		
		return list;
	}


	public ThesisInformation getInfoByStudentId(int studentId) {
		// TODO Auto-generated method stub
		ThesisInformation thesisInfo = thesisInformationDao.getInfoByStudentId(studentId);
		
		return thesisInfo;
	}


	public int passThesisInformation(int studentId) {
		// TODO Auto-generated method stub
		int num = thesisInformationDao.passThesisInformation(studentId);
		return num;
	}


	public int failThesisInformation(int studentId) {
		// TODO Auto-generated method stub
		int num = thesisInformationDao.failThesisInformation(studentId);
		return num;
	}


	public int addStudentScore(StudentScore score) {
		// TODO Auto-generated method stub
		int num = studentScoreDao.addStudenScore(score);
		
		return num;
	}


	public int modifyStudentScore(StudentScore score) {
		// TODO Auto-generated method stub
		int num = studentScoreDao.modifyStudentScore(score);
		return num;
	}


	public StudentScore showInfoByStudentId(int studentId) {
		// TODO Auto-generated method stub
		StudentScore s  = studentScoreDao.showInfoByStudentId(studentId);
		
		return s;
	}


	public int uploadThesis4Pass(ThesisPaper paper) {
		// TODO Auto-generated method stub
		int num = thesisPaperDao.addThesisPaper(paper);
		
		return num;
	}


	public List<ThesisPaper> getAllPaper() {
		// TODO Auto-generated method stub
		
		List<ThesisPaper> paperList = thesisPaperDao.getAllInfo();
		return paperList;
	}


	public int updateDoubt(Doubt doubt) {
		// TODO Auto-generated method stub
		int num = doubtDao.updateDoubt(doubt);
		return num;
	}


	public List<Doubt> getAllDoubtByStudentId(int studentId) {
		// TODO Auto-generated method stub
		List<Doubt> doubtList = doubtDao.getAllDoubt(studentId);
		
		return doubtList;
	}


	@Override
	public int deleteZhiyuan(int id) {
		// TODO Auto-generated method stub
		int num = zhiyuanDao.deleteZhiyuan(id);
		return num;
	}


	@Override
	public List<Map> getAllScores(int teacherId) {
		List<Map> allScores = studentTaskBookOpeningDao.showAllScores(teacherId);
		return allScores;
	}


	@Override
	public List<StudentTaskBookOpening> showAllInfo() {
		List<StudentTaskBookOpening> scores = studentTaskBookOpeningDao.showAllInfo();
		return scores;
	}
	




	

}
