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
import com.zc.dao.IStudentTaskBookOpeningDao;
import com.zc.dao.ITeacherProgressDao;
import com.zc.dao.ITeacherTaskBookOpeningDao;
import com.zc.dao.IThesisInformationDao;
import com.zc.dao.IThesisTitleDao;
import com.zc.dao.ITopicDao;
import com.zc.dao.IZhiyuanDao;
import com.zc.entity.Announcement;
import com.zc.entity.Doubt;
import com.zc.entity.Student;
import com.zc.entity.StudentTaskBookOpening;
import com.zc.entity.TeacherProgress;
import com.zc.entity.TeacherTaskBookOpening;
import com.zc.entity.ThesisInformation;
import com.zc.entity.ThesisTitle;
import com.zc.entity.Topic;
import com.zc.entity.Zhiyuan;
import com.zc.service.IStudentService;

@Service
public class StudentServiceImpl implements IStudentService{

	private static final Logger log = Logger.getLogger(StudentServiceImpl.class);

	@Autowired
	private IStudentDao studentDao;
	
	@Autowired
	private IThesisTitleDao thesisTitleDao;
	
	@Autowired
	private ITopicDao topicDao;
	
	@Autowired
	private IZhiyuanDao zhiyuanDao;
	
	@Autowired
	private ITeacherTaskBookOpeningDao teacherTaskBookOpeningDao;
	
	@Autowired
	private IStudentTaskBookOpeningDao studentTaskBookOpeningDao;
	
	@Autowired
	private ITeacherProgressDao teacherProgressDao;
	
	@Autowired
	private IAnnouncementDao announcementDao;
	
	@Autowired
	private IThesisInformationDao thesisInformationDao;
	
	@Autowired
	private IDoubtDao doubtDao;
	
	public int addStudent(Student student) {
		int addNum = studentDao.addStudent(student);
		return addNum;
	}


	public List<Student> showAllStudent() {
		// TODO Auto-generated method stub
		List<Student> students = studentDao.showAllStudent();
		return students;
	}


	public Student getStudentNameById(int id) {
		// TODO Auto-generated method stub
		Student student = studentDao.selectStudent(id);
		
		return student;
	}


	public int updateStudent(Student student) {
		// TODO Auto-generated method stub
		int num = studentDao.updateStudent(student);
		return num;
	}


	public int deleteStudent(int id) {
		// TODO Auto-generated method stub
		int num = studentDao.deleteStudent(id);
		return num;
		
	}


	public List<Student> showStudentOne1(String studentNo) {
		// TODO Auto-generated method stub
		List<Student> students = studentDao.showStudentOne1(studentNo);
		return students;
	}


	public List<Student> showStudentOne2(String studentName) {
		// TODO Auto-generated method stub
		List<Student> students = studentDao.showStudentOne2(studentName);
		return students;
	}


	public List<Student> showStudentOne3(String studentNo, String studentName) {
		// TODO Auto-generated method stub
		List<Student> students = studentDao.showStudentOne3(studentNo, studentName);
		return students;
	}


	public Student getStudentByNO(String studentNo) {
		// TODO Auto-generated method stub
		Student student =studentDao.getInfoByNo(studentNo);
		return student;
	}


	public List<ThesisTitle> availableTopic() {
		// TODO Auto-generated method stub
		
		// ?????????????????????List ?????? ?????????????????????????????????????????????
		List<ThesisTitle> thesisList = thesisTitleDao.showAllThesisTitleAd();
		//log.info("?????????List??????"+thesisList);
		//????????????????????????????????????
		
		for(int i=0;i<thesisList.size();i++) {
			if(thesisList.get(i).getStatus() != 2) {
				thesisList.remove(i);
			}
		}
		//log.info("????????????List??????"+thesisList);
		//??????thesisTitle???????????????
		List<Topic> topicList = topicDao.showAllTopic();
		List<ThesisTitle> showList = new ArrayList<ThesisTitle>();
		for(int i=0;i<thesisList.size();i++) {
			for(int j=0;j<topicList.size();j++) {
				if(thesisList.get(i).getId() == topicList.get(j).getThesisId()) {
					break;
				}else if(j == topicList.size()-1) {
					showList.add(thesisList.get(i));
				}else {
					
				}
			}
			
		}
		log.info(showList);
		return showList;
	}


	public Topic chosenThesisTitle(int studentId) {
		// TODO Auto-generated method stub
		Topic topic = topicDao.topicByStudentId(studentId);
		
		return topic;
	}
	
	public Zhiyuan chosenZhiyuan(int studentId) {
		// TODO Auto-generated method stub
		Zhiyuan zhiyuan = zhiyuanDao.zhiyuanByStudentId(studentId);
		
		return zhiyuan;
	}


	public int addTopicToDb(Topic topic) {
		// TODO Auto-generated method stub
		int num = topicDao.addTopic(topic);
		return num;
	}
	
	public int addZhiyuanToDb(Zhiyuan zhiyuan) {
		// TODO Auto-generated method stub
		int num = zhiyuanDao.addZhiyuan(zhiyuan);
		return num;
	}


	public int deleteTopic(int studentId) {
		// TODO Auto-generated method stub
		int num = topicDao.deleteTopic(studentId);
		return num;
	}


	public TeacherTaskBookOpening getFilePathByThesisId(int thesisId) {
		// TODO Auto-generated method stub
		TeacherTaskBookOpening teacherTaskBookOpening = teacherTaskBookOpeningDao.showInfo(thesisId);
		return teacherTaskBookOpening;
	}


	public int uploadTaskBook(int studentId, String filePath) {
		// TODO Auto-generated method stub
		StudentTaskBookOpening dbInfo = studentTaskBookOpeningDao.showInfoByStudentId(studentId);
		if(dbInfo == null || "".equals(dbInfo)) {
			studentTaskBookOpeningDao.addInfoByStudentId(studentId);
		}
		int num = studentTaskBookOpeningDao.uploadTaskBook(studentId, filePath);
		return num;
	}


	public int uploadOpening(int studentId, String filePath) {
		// TODO Auto-generated method stub
		StudentTaskBookOpening dbInfo = studentTaskBookOpeningDao.showInfoByStudentId(studentId);
		if(dbInfo == null || "".equals(dbInfo)) {
			studentTaskBookOpeningDao.addInfoByStudentId(studentId);
		}
		int num = studentTaskBookOpeningDao.uploadOpening(studentId, filePath);
		return num;
	}
	
	public int uploadKexing(int studentId, String filePath) {
		// TODO Auto-generated method stub
		StudentTaskBookOpening dbInfo = studentTaskBookOpeningDao.showInfoByStudentId(studentId);
		if(dbInfo == null || "".equals(dbInfo)) {
			studentTaskBookOpeningDao.addInfoByStudentId(studentId);
		}
		int num = studentTaskBookOpeningDao.uploadKeXingXing(studentId, filePath);
		return num;
	}
	
	public int uploadXuqiu(int studentId, String filePath) {
		// TODO Auto-generated method stub
		StudentTaskBookOpening dbInfo = studentTaskBookOpeningDao.showInfoByStudentId(studentId);
		if(dbInfo == null || "".equals(dbInfo)) {
			studentTaskBookOpeningDao.addInfoByStudentId(studentId);
		}
		int num = studentTaskBookOpeningDao.uploadXuQiu(studentId, filePath);
		return num;
	}
	
	public int uploadGaiyao(int studentId, String filePath) {
		// TODO Auto-generated method stub
		StudentTaskBookOpening dbInfo = studentTaskBookOpeningDao.showInfoByStudentId(studentId);
		if(dbInfo == null || "".equals(dbInfo)) {
			studentTaskBookOpeningDao.addInfoByStudentId(studentId);
		}
		int num = studentTaskBookOpeningDao.uploadGaiYao(studentId, filePath);
		return num;
	}
	
	public int uploadShujuku(int studentId, String filePath) {
		// TODO Auto-generated method stub
		StudentTaskBookOpening dbInfo = studentTaskBookOpeningDao.showInfoByStudentId(studentId);
		if(dbInfo == null || "".equals(dbInfo)) {
			studentTaskBookOpeningDao.addInfoByStudentId(studentId);
		}
		int num = studentTaskBookOpeningDao.uploadShuJuKu(studentId, filePath);
		return num;
	}


	public StudentTaskBookOpening getSTBOInfoById(int studentId) {
		// TODO Auto-generated method stub
		StudentTaskBookOpening STBO = studentTaskBookOpeningDao.showInfoByStudentId(studentId);
		
		return STBO;
	}

    //???????????? ????????????????????????
	public Map<String, String> getTaskBookOpeningToMap(int studentId) {
		// TODO Auto-generated method stub
		Map<String, String> fileList = new HashMap<String, String>();
		StudentTaskBookOpening STBO = studentTaskBookOpeningDao.showInfoByStudentId(studentId);
		if(STBO == null || "".equals(STBO)) {
			return fileList;
		}else {
			String taskBookPath = STBO.getStudentTaskBook();
			if(taskBookPath==null || "".equals(taskBookPath)) {

			}else {
				String[] str1 = taskBookPath.split("\\\\");
				String fileName1 = str1[str1.length-1].toString();
				fileList.put(fileName1, taskBookPath);
			}
			
			String openingPath= STBO.getStudentOpeningResport();
			if(openingPath ==null ||"".equals(openingPath)) {
				
			}else {
				String[] str2 = openingPath.split("\\\\");
				String fileName2 = str2[str2.length-1].toString();
				fileList.put(fileName2, openingPath);
			}
			
			String kexingPath= STBO.getStudentKeXingXing();
			if(kexingPath ==null ||"".equals(kexingPath)) {
				
			}else {
				String[] str2 = kexingPath.split("\\\\");
				String fileName2 = str2[str2.length-1].toString();
				fileList.put(fileName2, kexingPath);
			}
			
			String xuqiuPath= STBO.getStudentXuQiu();
			if(xuqiuPath ==null ||"".equals(xuqiuPath)) {
				
			}else {
				String[] str2 = xuqiuPath.split("\\\\");
				String fileName2 = str2[str2.length-1].toString();
				fileList.put(fileName2, xuqiuPath);
			}
			
			String gaiyaoPath= STBO.getStudentGaiYao();
			if(gaiyaoPath ==null ||"".equals(gaiyaoPath)) {
				
			}else {
				String[] str2 = gaiyaoPath.split("\\\\");
				String fileName2 = str2[str2.length-1].toString();
				fileList.put(fileName2, gaiyaoPath);
			}
			
			String shujukuPath= STBO.getStudentShuJuKu();
			if(shujukuPath ==null ||"".equals(shujukuPath)) {
				
			}else {
				String[] str2 = shujukuPath.split("\\\\");
				String fileName2 = str2[str2.length-1].toString();
				fileList.put(fileName2, shujukuPath);
			}
			
			return fileList;
		}
		
	}


	public int resetTaskBook(int studentId) {
		// TODO Auto-generated method stub
		int num = studentTaskBookOpeningDao.resetTaskBook(studentId);
		
		return num;
	}


	public int resetOpening(int studentId) {
		// TODO Auto-generated method stub
		int num = studentTaskBookOpeningDao.resetOpening(studentId);
		return num;
	}


	public StudentTaskBookOpening getInfoByTaskBookPath(String taskBookPath) {
		// TODO Auto-generated method stub
		StudentTaskBookOpening STBO = studentTaskBookOpeningDao.getInfoByTaskBookPath(taskBookPath);
		
		return STBO;
	}


	public StudentTaskBookOpening getInfoByOpeningPath(String openingPath) {
		// TODO Auto-generated method stub
		StudentTaskBookOpening STBO = studentTaskBookOpeningDao.getInfoByOpeningPath(openingPath);
		return STBO;
	}


	public ThesisTitle getThesisInfoByid(int thesisId) {
		// TODO Auto-generated method stub
		ThesisTitle thesis = thesisTitleDao.getThesisById(thesisId);
		
		return thesis;
	}


	public List<TeacherProgress> getTeacherProgressByStudentId(int studentId) {
		// TODO Auto-generated method stub
		List<TeacherProgress> t = teacherProgressDao.getInfoByStudentId(studentId);
		
		return t;
	}


	public List<Announcement> showAllAnnouncement() {
		// TODO Auto-generated method stub
		List<Announcement> list = announcementDao.showAllAnnouncement();
		
		return list;
	}


	public int uploadThesisInformation(int studentId, String filePath) {
		// TODO Auto-generated method stub
		ThesisInformation thesis = new ThesisInformation();
		thesis.setStudentId(studentId);
		thesis.setThesisText(filePath);
		
		int num = thesisInformationDao.addThesisInformation(thesis);
		
		
		return num;
	}

	//???????????? ??????????????????
	public List<ThesisInformation> getInfoByStudentId(int studentId) {
		List<ThesisInformation> thesis = thesisInformationDao.getInfoByStudentIdList(studentId);
		return thesis;
	}


	public ThesisInformation getInfoByFilePath(String filePath) {
		// TODO Auto-generated method stub
		ThesisInformation thesis = thesisInformationDao.getInfoByFilePath(filePath);
		
		return thesis;
	}


	public int deleteThesisInformation(int studentId) {
		// TODO Auto-generated method stub
		
		int num = thesisInformationDao.deleteThesisInformation(studentId);
		return num;
	}


	public List<ThesisInformation> getThesisInforInfoByStudentId(int studentId) {
		// TODO Auto-generated method stub
		List<ThesisInformation> thesisInfo = thesisInformationDao.getInfoByStudentIdList(studentId);
		
		return thesisInfo;
	}


	public int addDoubt(Doubt doubt) {
		// TODO Auto-generated method stub
		int num = doubtDao.addDoubt(doubt);
		
		return num;
	}


	public List<Doubt> getAllDoubtByStudent(int studentId) {
		// TODO Auto-generated method stub
		List<Doubt> doubtList = doubtDao.getAllDoubt(studentId);
		return doubtList;
	}


	public String getThesisDesc(int thesisId) {
		// TODO Auto-generated method stub
		
		ThesisTitle thesis = thesisTitleDao.getThesisById(thesisId);
		String desc = thesis.getDescription();
		return desc;
	}

}
