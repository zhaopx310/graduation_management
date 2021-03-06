package com.zc.web;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zc.entity.Announcement;
import com.zc.entity.Doubt;
import com.zc.entity.Student;
import com.zc.entity.StudentScore;
import com.zc.entity.StudentTaskBookOpening;
import com.zc.entity.TeacherProgress;
import com.zc.entity.TeacherTaskBookOpening;
import com.zc.entity.ThesisInformation;
import com.zc.entity.ThesisTitle;
import com.zc.entity.Topic;
import com.zc.entity.User;
import com.zc.entity.Zhiyuan;
import com.zc.service.IMajorService;
import com.zc.service.IStudentService;
import com.zc.service.ITeacherService;

/**
 * 
 * @author z
 * addStudentForm() 主页跳转到添加界面
 * addStudent() 添加学生   以废除
 * studentInfo() 学生修改个人信息跳转
 * 
 * @date 2021-3-6
 * @author z
 * @desc 都是跳转页面
 * studentMainForm() 跳转学生管理主页
 * studentMofidyInfoForm() 跳转学生 修改个人信息页
 * studentMofidyPasswordForm() 跳转学生 修改密码页
 * studentThesis() 学生选题页
 * studentThesisResult() 学生选题结果页
 * studentViewTaskOpening() 查看任务书及开题报告
 * studentSectionTask() 填写阶段任务
 * studentUploadFile() 文件上传
 * studentResourcesDownload() 文件下载
 * studentAnnouncement() 查看公共
 * studentScore() 查询成绩
 * 
 * @date 2021-3-12
 * @author z
 * 修改了studentThesis() 页面可以显示当前学生的选题状况，若是选择了课题则直接跳转到主页。
 * 修改了studentThesisResult() 显示学生的选题情况，若是为选择课题则不能查询选题结果，会直接跳转到主页
 * studentModifyInfoToDb() 修改学生信息
 * studentGetAllAvailableTopicForm() 获得可选择的课题，用于学生选题页
 * studentSelectTopic() 提交当前学生的选题情况
 * studentDeleteChosenTopic() 学生退选当前课题
 * 
 * @date 2021-3-15
 * @author z
 * 修改了studentViewTaskOpening() 页面，用于查看学生所选课题的开题报告和任务书，并下载
 * 修改了 studentResourcesDownload() 也main，显示学生上传的内容
 * fileDownload() 文件下载
 * studentUploadTaskBook() 上传自己写的任务书 ---以废除不用
 * studentUploadOpening() 上传自己写的开题报告
 * fileDelete() 文件删除
 * studentOpeningResult() 学生查看自己开题报告的审核结果 (未提交--- 通过---- 不通过  )
 * 
 * @date 2021-3-16
 * @author z
 * 修改了 studentSelectTopic() 方法 添加了 把选题信息保存到application 中，以便jsp页面的显示
 * getRealTimeTopic() 获得当前选题信息，并保存到application中
 * 
 * @date 2021-3-17
 * @author z
 * 修改了studentResourcesDownload方法，可以显示上传为论文信息
 * 修改了fileDelete方法，可以删除上传的论文信息
 * studentUploadThesisInformation() 学生提交 学生论文信息
 * 
 * @date 2021-3-18
 * @author z
 * studentQualification()  学生答辩资格的查看
 * 
 * @date 2021-3-9
 * @author z
 * studentDoubtForm() 跳转到学生提出疑惑页面
 * studentDoubtListForm() 显示学生的疑惑，并查看是否有解决方案
 * studentDoubtToDb() 添加学生的疑惑道数据库
 * 
 */

@Controller
@RequestMapping(value="/student")
public class StudentController {

	private static final Logger log = Logger.getLogger(StudentController.class);

	@Autowired
	private IStudentService studentService;
	
	@Autowired
	private IMajorService majorService;
	
	@Autowired
	private ITeacherService teacherService;
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String addStudentForm() {
		return "student/addStudent.jsp";
	}
	
	private String realTimeTopicMessageOn = "";
	
	// 已废除
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addStudent(HttpServletRequest request,String studentNo, String studentName,String sex,String grade ,String inputMan,String phone,String major,Model model) throws Exception {
		studentNo = new String(studentNo.getBytes("iso-8859-1"),"utf-8");
		studentName = new String(studentName.getBytes("iso-8859-1"),"utf-8");
		sex = new String(sex.getBytes("iso-8859-1"),"utf-8");
		grade = new String(grade.getBytes("iso-8859-1"),"utf-8");
		inputMan = new String(inputMan.getBytes("iso-8859-1"),"utf-8");
		phone = new String(phone.getBytes("iso-8859-1"),"utf-8");
		major = new String(major.getBytes("iso-8859-1"),"utf-8");
		Date currentTime = new Date();
		
		Student student = new Student();
		student.setStudentNo(studentNo);
		student.setStudentName(studentName);
		student.setSex(sex);
		student.setGrade(grade);
		student.setInputMan(inputMan);
		student.setPhone(phone);
		student.setMajorId(Integer.parseInt(major));
		student.setLastModifyTime(currentTime);
		
		int addNum = studentService.addStudent(student);
		log.info("添加数目："+addNum);
		
		return "student/addSuccess.jsp";
	}
	
	@RequestMapping(value="/info",method=RequestMethod.GET)
	public String studentInfo() {
		return "student/studentPersionalInformation.jsp";
	}
	
	@RequestMapping(value="/main",method=RequestMethod.GET)
	public String studentMainForm() {
		return "student/main.jsp";
	}
	
	@RequestMapping(value="/modifyInfo",method=RequestMethod.GET)
	public String studentMofidyInfoForm() {
		return "student/studentModifyInfo.jsp";
	}
	
	@RequestMapping(value="/modifyPassword",method=RequestMethod.GET)
	public String studentMofidyPasswordForm() {
		return "student/studentModifyPassword.jsp";
	}

	//学生-课题管理-选择课题
	@RequestMapping(value="/thesis",method=RequestMethod.GET)
	public String studentThesis(HttpServletResponse response,HttpServletRequest request,Model model) {
		User currentUser = (User)request.getSession().getAttribute("currentUser");
		String userNo = currentUser.getUserNo();
		//获取所有课题
		List<ThesisTitle> thesisList = teacherService.showAllThesisTitle();
		Student student = studentService.getStudentByNO(userNo);
		int studentId = student.getId();
		Topic topic = studentService.chosenThesisTitle(studentId);
		if(topic == null || "".equals(topic)) {
			model.addAttribute("thesisTitleList", thesisList);
			log.info("查询到的课题有："+thesisList);
			return "student/studentThesis.jsp";
		}else {
			log.info(topic);
			model.addAttribute("topicMessage", "你已选择课题，不可多选");
			return "student/main.jsp";
		}
	}
	
	@RequestMapping(value="/thesisResult",method=RequestMethod.GET)
	public String studentThesisResult(HttpServletResponse response,HttpServletRequest request,Model model) {
		User currnetUser = (User)request.getSession().getAttribute("currentUser");
		String studentNo = currnetUser.getUserNo();
		Student student = studentService.getStudentByNO(studentNo);
		int studentId = student.getId();
		Topic topic = studentService.chosenThesisTitle(studentId);
		Zhiyuan zhiyuan = studentService.chosenZhiyuan(studentId);
		if(topic == null || "".equals(topic)) {
			if(zhiyuan == null || "".equals(zhiyuan)) {
				model.addAttribute("topicMessage", "还未选择课题");
				model.addAttribute("realTimeTopicMessage", realTimeTopicMessageOn);
			} else {
				model.addAttribute("topicMessage", "已选择课题，请等待导师选择");
				model.addAttribute("realTimeTopicMessage", realTimeTopicMessageOn);
			}
			
			return "student/main.jsp";
		}else {
			int thesisId = topic.getThesisId();
			
			ThesisTitle theisTitle = teacherService.getThesisById(thesisId);
			String topicName = theisTitle.getThesisName();
			String inputMan = theisTitle.getInputMan();
			String nandu = theisTitle.getNandu();
			String liang = theisTitle.getLiang();
			String from = theisTitle.getFrom();
			String leixing = theisTitle.getLeixing();
			String description = theisTitle.getDescription();
			
			model.addAttribute("topicName", topicName);
			model.addAttribute("inputMan", inputMan);
			model.addAttribute("nandu", nandu);
			model.addAttribute("liang", liang);
			model.addAttribute("from", from);
			model.addAttribute("leixing", leixing);
			model.addAttribute("description", description);
			
			model.addAttribute("realTimeTopicMessage", realTimeTopicMessageOn);
			
			return "student/studentThesisResult.jsp";
		}
		
	}
	
	@RequestMapping(value="/viewTaskOpening")
	public String studentViewTaskOpening(HttpServletRequest request,Model model) {
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		Topic topic = studentService.chosenThesisTitle(studentId);
		if(topic == null || "".equals(topic)) {
			model.addAttribute("message", "尚未选择课题");
			return "student/studentViewTaskBookAndOpening.jsp";
		}else {
			int thesisId = topic.getThesisId();
			TeacherTaskBookOpening ttbo = studentService.getFilePathByThesisId(thesisId);
			if(ttbo==null || "".equals(ttbo)) {
				return "student/studentViewTaskBookAndOpening.jsp";
			}else {
				// 获得数据库中的文件目录；
				String taskBookPath = ttbo.getTaskBook();
				String openingPath = ttbo.getOpeningReport();
				String keXingXingPath = ttbo.getKeXingXing();
				String xuQiuPath = ttbo.getXuQiu();
				String gaiYaoPath = ttbo.getGaiYao();
				String shuJuKuPath = ttbo.getShuJuKu();
				
				Map<String, String> fileList = new HashMap<String, String>();

				if(taskBookPath == null || "".equals(taskBookPath)) {
					
				}else {
					String[] str1 = taskBookPath.split("\\\\");
					String taskBookName = str1[str1.length-1].toString();
					fileList.put(taskBookName, taskBookPath);
				}
				
				if(openingPath == null || "".equals(openingPath)) {
					
				}else {
					String[] str2 = openingPath.split("\\\\");
					String openingName = str2[str2.length-1].toString();
					fileList.put(openingName, openingPath);
				}
				
				if(keXingXingPath == null || "".equals(keXingXingPath)) {
					
				}else {
					String[] str2 = keXingXingPath.split("\\\\");
					String keXingXingName = str2[str2.length-1].toString();
					fileList.put(keXingXingName, keXingXingPath);
				}
				
				if(xuQiuPath == null || "".equals(xuQiuPath)) {
					
				}else {
					String[] str2 = xuQiuPath.split("\\\\");
					String xuQiuName = str2[str2.length-1].toString();
					fileList.put(xuQiuName, xuQiuPath);
				}
				
				if(gaiYaoPath == null || "".equals(gaiYaoPath)) {
					
				}else {
					String[] str2 = gaiYaoPath.split("\\\\");
					String gaiYaoName = str2[str2.length-1].toString();
					fileList.put(gaiYaoName, gaiYaoPath);
				}
				
				if(shuJuKuPath == null || "".equals(shuJuKuPath)) {
					
				}else {
					String[] str2 = shuJuKuPath.split("\\\\");
					String shuJuKuName = str2[str2.length-1].toString();
					fileList.put(shuJuKuName, shuJuKuPath);
				}
				
				model.addAttribute("fileList", fileList);
				
				return "student/studentViewTaskBookAndOpening.jsp";
			}
			
		}
		
		
	}
	
	@RequestMapping(value="/sectionTask")
	public String studentSectionTask(HttpServletRequest request,Model model) {
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		
		List<TeacherProgress> progresses = studentService.getTeacherProgressByStudentId(studentId);
		
		for(int i=0;i<progresses.size();i++) {
			int state = progresses.get(i).getState();
			if(state ==0) {
				progresses.get(i).setStateName("教师还未查看");
			}else if(state ==1) {
				progresses.get(i).setStateName("未通过");
			}else {
				progresses.get(i).setStateName("已通过");
			}
			
		}
		
		
		model.addAttribute("progressList", progresses);
		
		return "student/studentSectionTask.jsp";
	}
	
	@RequestMapping(value="/uploadFile",method=RequestMethod.GET)
	public String studentUploadFile() {
		return "student/studentUploadFile.jsp";
	}

	//学生-文档管理-查看已上传的文档
	@RequestMapping(value="/resourcesDownload")
	public String studentResourcesDownload(HttpServletRequest request,Model model) {
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		//学生查询 开题报告和任务书
		Map<String, String> fileList = studentService.getTaskBookOpeningToMap(studentId);
		if(fileList.isEmpty()) {
			return "student/studentResourcesDownload.jsp";
		}else {
            //学生查询 提交毕业论文
			List<ThesisInformation> thesisInformation4Db = studentService.getInfoByStudentId(studentId);
			for (int i = 0; i <thesisInformation4Db.size() ; i++) {
				ThesisInformation thesisInformation = thesisInformation4Db.get(i);
				if(thesisInformation ==null) {
                log.info("学生-毕业论文为空："+ thesisInformation);
				}else {
					String filePath = thesisInformation.getThesisText();
					String[] str = filePath.split("\\\\");
					String fileName = str[str.length-1].toString();
					fileList.put(fileName, filePath);
				}
			}
			model.addAttribute("fileList", fileList);
			return "student/studentResourcesDownload.jsp";
		}
	}
	
	@RequestMapping(value="/announcement")
	public String studentAnnouncement(Model model) {
		List<Announcement> list = studentService.showAllAnnouncement();
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
		for(int i=0;i<list.size();i++) {
			Date time4db = list.get(i).getLastModifyTime();
			String formatTime = time.format(time4db);
			list.get(i).setTimeFormat(formatTime);
		}
		
		model.addAttribute("announcementList", list);
		
		return "student/studentAnnouncement.jsp";
	}
	
	@RequestMapping(value="/score")
	public String studentScore(HttpServletRequest request,Model model) {
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		StudentScore dbScore = teacherService.showInfoByStudentId(studentId);
		if(dbScore == null || "".equals(dbScore)) {
			model.addAttribute("message", "暂无成绩");
			return "student/main.jsp";
			
		}else {
			String studentName = teacherService.getStudentInfoByStudentId(studentId).getStudentName();
			int thesisId = teacherService.getTopicInfoByStudentId(studentId).getThesisId();
			String thesisName = teacherService.getThesisById(thesisId).getThesisName();
			int score = teacherService.showInfoByStudentId(studentId).getThesisResult();
			Student student = new Student();
			student.setStudentName(studentName);
			student.setThesisName(thesisName);
			student.setThesisScore(score);
			
			model.addAttribute("studentList", student);
			
			return "student/studentScore.jsp";
		}
		
	}
	
	@RequestMapping(value="/modifyInfoToDb",method=RequestMethod.POST)
	public String studentModifyInfoToDb(Model model,HttpServletRequest request,int id,String studentNo, String studentName,String sex,String majorOld,String major,String grade, String phone, String email) throws Exception {
		
		// 以下代码是学生修改学生信息
		int majorId = 0;
		majorOld = new String(majorOld.getBytes("iso-8859-1"),"utf-8");
		//departmentId =  departmentService.getIdByName(departmentOld);
		majorId = majorService.getIdByName(majorOld);
		studentNo = new String(studentNo.getBytes("iso-8859-1"),"utf-8");
		studentName = new String(studentName.getBytes("iso-8859-1"),"utf-8");
		grade = new String(grade.getBytes("iso-8859-1"),"utf-8");
		sex = new String(sex.getBytes("iso-8859-1"),"utf-8");
		User user = (User)request.getSession().getAttribute("currentUser");
		String inputMan = user.getUserNo();
		phone = new String(phone.getBytes("iso-8859-1"),"utf-8");
		email = new String(email.getBytes("iso-8859-1"),"utf-8");
		major = new String(major.getBytes("iso-8859-1"),"utf-8");
		Date currentTime = new Date();
		
		
		Student student = new Student();
		student.setId(id);
		student.setStudentNo(studentNo);
		student.setStudentName(studentName);
		
		if (major == null || "".equals(major)) {
			student.setMajorId(majorId);
		}else {
			student.setMajorId(Integer.parseInt(major));
		}
		student.setGrade(grade);
		student.setSex(sex);
		student.setInputMan(inputMan);
		student.setLastModifyTime(currentTime);
		student.setPhone(phone);
		student.setEmail(email);
		
		int num = studentService.updateStudent(student);
		log.info("修改学生数目："+num);
		
		// 根据 院系id 获得专业name
		int majId = student.getMajorId();
		String majorNameNew = majorService.getNameById(majId);
		student.setMajorName(majorNameNew);
		
		HttpSession session = request.getSession();
		session.setAttribute("student", student);
		
		return "student/main.jsp";
	}
	
	@RequestMapping(value="/getAllAvailableTopic")
	public void studentGetAllAvailableTopicForm(HttpServletResponse response,HttpServletRequest request) throws Exception {
		
		List<ThesisTitle> thesisList = studentService.availableTopic();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter write = response.getWriter();
		write.write(JSONArray.toJSONString(thesisList));
		write.close();
	}
	
	@RequestMapping(value="/selectTopic")
	public String studentSelectTopic(HttpServletResponse response,HttpServletRequest request,Model model,int id,int topic) throws Exception {
		
		/*log.info("id:"+id);
		log.info("topic:"+topic);*/
		Topic top = new Topic();
		top.setStudentId(id);
		top.setThesisId(topic);
		Date time = new Date();
		top.setSelectTime(time);
		
		int num = studentService.addTopicToDb(top);
		log.info("添加了"+num+"个选题");
		
		Student student = studentService.getStudentNameById(id);
		String studentNo = student.getStudentNo();
		
		ThesisTitle thesis = studentService.getThesisInfoByid(topic);
		String thesisName = thesis.getThesisName();
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("学号为");
		sb.append(studentNo);
		sb.append("的学生，选择了");
		sb.append(thesisName);
		
		
		String infoMessage = sb.toString();
		
		this.realTimeTopicMessageOn = infoMessage;
		model.addAttribute("realTimeTopicMessage", realTimeTopicMessageOn);
		
		studentThesisResult(response, request, model);
		return "student/studentThesisResult.jsp";
	}
	
	@RequestMapping(value="/selectThesis")
	public String studentSelectThesis(HttpServletResponse response,HttpServletRequest request,Model model,int id,int thesis) throws Exception {
		
		/*log.info("id:"+id);
		log.info("topic:"+topic);*/
		Zhiyuan zhi = studentService.chosenZhiyuan(id);
		if(zhi == null || "".equals(zhi)) {
			Zhiyuan zhiyuan = new Zhiyuan();
			zhiyuan.setStudentId(id);
			zhiyuan.setThesisId(thesis);
			Date time = new Date();
			zhiyuan.setSelectTime(time);
			
			int num = studentService.addZhiyuanToDb(zhiyuan);
			log.info("添加了"+num+"个志愿");
			
			Student student = studentService.getStudentNameById(id);
			String studentNo = student.getStudentNo();
			
			ThesisTitle thesis1 = studentService.getThesisInfoByid(thesis);
			String thesisName = thesis1.getThesisName();
			
			
			StringBuffer sb = new StringBuffer();
			sb.append("学号为");
			sb.append(studentNo);
			sb.append("的学生，选择了");
			sb.append(thesisName);
			
			
			String infoMessage = sb.toString();
			
			this.realTimeTopicMessageOn = infoMessage;
			model.addAttribute("message", realTimeTopicMessageOn);
			
			studentThesisResult(response, request, model);
			return "student/main.jsp";
		}else {
			model.addAttribute("message", "已选择课题，不可再次选择");
			return "student/main.jsp";
		}
		
	}
	
	@RequestMapping(value="/deleteChosenTopic")
	public String studentDeleteChosenTopic(Model model,int studentId) throws Exception {
		
		// log.info(studentId);
		StudentTaskBookOpening stbo = studentService.getSTBOInfoById(studentId);
		if(stbo==null||"".equals(stbo)) {
			int num = studentService.deleteTopic(studentId); 
			log.info("成功退选 :"+num+"条数据");
			model.addAttribute("message", "成功退选");
			
			return "student/main.jsp";
		}else {
			model.addAttribute("message", "已上传开题报告，不可退选");
			return "student/main.jsp";
		}
	}
	
	@RequestMapping(value="/fileDownload")
	public ResponseEntity<byte[]> fileDownload(HttpServletRequest request, @RequestParam("filePath") String filePath,@RequestParam("fileName") String fileName, Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		log.info(fileName);
		log.info(filePath);
		//fileName = new String(fileName.getBytes("iso-8859-1"),"utf-8");
		//filePath = new String(filePath.getBytes("iso-8859-1"),"utf-8");
		File file = new File(filePath);
		HttpHeaders headers = new HttpHeaders();
		String downloadFile = new String(fileName.getBytes("utf-8"), "iso-8859-1");
		headers.setContentDispositionFormData("attachment", downloadFile);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED); 
	}
	
	@RequestMapping(value="/uploadTaskBook")
	public String studentUploadTaskBook(HttpServletRequest request, Model model,@RequestParam("file") MultipartFile file) throws Exception {
		
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		
		String studentIdToString = String.valueOf(studentId);
		
		if(!file.isEmpty()) {
			
			File fileRoot = new File("E:\\BSM\\student");
			File fileDb = new File(fileRoot, studentIdToString);
			String fileName = file.getOriginalFilename();
			
			File filePath = new File(fileDb, fileName);
			
			if(!filePath.getParentFile().exists()) {
				filePath.getParentFile().mkdirs();
			}
			
			file.transferTo(new File(fileDb+File.separator+fileName));
			
			int num = studentService.uploadTaskBook(studentId, filePath.toString());
			log.info("添加了"+num+"条信息");
			model.addAttribute("message", "上传任务书成功");
			return "student/main.jsp";
		}else {
			model.addAttribute("message", "上传任务书出错");
			return "error.jsp";
		}
	}

	//上传开题报告
	@RequestMapping(value="/uploadOpening")
	public String studentUploadOpening(HttpServletRequest request, Model model,@RequestParam("file") MultipartFile file) throws Exception {

		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();

		String studentIdToString = String.valueOf(studentId);

		Topic topic = studentService.chosenThesisTitle(studentId);
		if(topic == null || "".equals(topic)) {
			model.addAttribute("message", "无法上传开题报告");
			return "student/main.jsp";
		}else {
			if(!file.isEmpty()) {

				File fileRoot = new File("E:\\BSM\\student");
				File fileDb = new File(fileRoot, studentIdToString);
				String fileName = file.getOriginalFilename();

				File filePath = new File(fileDb, fileName);

				if(!filePath.getParentFile().exists()) {
					filePath.getParentFile().mkdirs();
				}

				file.transferTo(new File(fileDb+File.separator+fileName));

				int num = studentService.uploadOpening(studentId, filePath.toString());
				log.info("添加了"+num+"条信息");

				model.addAttribute("message", "上传开题报告成功");
				return "student/main.jsp";
			}else {
				model.addAttribute("message", "上传开题报告出错");
				return "student/main.jsp";
			}
		}
	}
	
	@RequestMapping(value="/uploadKexing")
	public String studentUploadKexing(HttpServletRequest request, Model model,@RequestParam("file") MultipartFile file) throws Exception {
		
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		
		String studentIdToString = String.valueOf(studentId);
		
		Topic topic = studentService.chosenThesisTitle(studentId);
		if(topic == null || "".equals(topic)) {
			model.addAttribute("message", "无法上传可行性分析报告");
			return "student/main.jsp";
		}else {
			if(!file.isEmpty()) {
				
				File fileRoot = new File("E:\\BSM\\student");
				File fileDb = new File(fileRoot, studentIdToString);
				String fileName = file.getOriginalFilename();
				
				File filePath = new File(fileDb, fileName);
				
				if(!filePath.getParentFile().exists()) {
					filePath.getParentFile().mkdirs();
				}
				
				file.transferTo(new File(fileDb+File.separator+fileName));
				
				int num = studentService.uploadKexing(studentId, filePath.toString());
				log.info("添加了"+num+"条信息");
				
				model.addAttribute("message", "上传可行性分析报告成功");
				return "student/main.jsp";
			}else {
				model.addAttribute("message", "上传可行性分析报告出错");
				return "student/main.jsp";
			}
		}
	}
	
	@RequestMapping(value="/uploadXuqiu")
	public String studentUploadXuqiu(HttpServletRequest request, Model model,@RequestParam("file") MultipartFile file) throws Exception {
		
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		
		String studentIdToString = String.valueOf(studentId);
		
		Topic topic = studentService.chosenThesisTitle(studentId);
		if(topic == null || "".equals(topic)) {
			model.addAttribute("message", "无法上传需求分析报告");
			return "student/main.jsp";
		}else {
			if(!file.isEmpty()) {
				
				File fileRoot = new File("E:\\BSM\\student");
				File fileDb = new File(fileRoot, studentIdToString);
				String fileName = file.getOriginalFilename();
				
				File filePath = new File(fileDb, fileName);
				
				if(!filePath.getParentFile().exists()) {
					filePath.getParentFile().mkdirs();
				}
				
				file.transferTo(new File(fileDb+File.separator+fileName));
				
				int num = studentService.uploadXuqiu(studentId, filePath.toString());
				log.info("添加了"+num+"条信息");
				
				model.addAttribute("message", "上传需求分析报告成功");
				return "student/main.jsp";
			}else {
				model.addAttribute("message", "上传需求分析报告出错");
				return "student/main.jsp";
			}
		}
	}
	
	@RequestMapping(value="/uploadGaiyao")
	public String studentUploadGaiyao(HttpServletRequest request, Model model,@RequestParam("file") MultipartFile file) throws Exception {
		
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		
		String studentIdToString = String.valueOf(studentId);
		
		Topic topic = studentService.chosenThesisTitle(studentId);
		if(topic == null || "".equals(topic)) {
			model.addAttribute("message", "无法上传概要设计报告");
			return "student/main.jsp";
		}else {
			if(!file.isEmpty()) {
				
				File fileRoot = new File("E:\\BSM\\student");
				File fileDb = new File(fileRoot, studentIdToString);
				String fileName = file.getOriginalFilename();
				
				File filePath = new File(fileDb, fileName);
				
				if(!filePath.getParentFile().exists()) {
					filePath.getParentFile().mkdirs();
				}
				
				file.transferTo(new File(fileDb+File.separator+fileName));
				
				int num = studentService.uploadGaiyao(studentId, filePath.toString());
				log.info("添加了"+num+"条信息");
				
				model.addAttribute("message", "上传概要设计报告成功");
				return "student/main.jsp";
			}else {
				model.addAttribute("message", "上传概要设计报告出错");
				return "student/main.jsp";
			}
		}
	}
	
	@RequestMapping(value="/uploadShujuku")
	public String studentUploadShujuku(HttpServletRequest request, Model model,@RequestParam("file") MultipartFile file) throws Exception {
		
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		
		String studentIdToString = String.valueOf(studentId);
		
		Topic topic = studentService.chosenThesisTitle(studentId);
		if(topic == null || "".equals(topic)) {
			model.addAttribute("message", "无法上传数据库设计报告");
			return "student/main.jsp";
		}else {
			if(!file.isEmpty()) {
				
				File fileRoot = new File("E:\\BSM\\student");
				File fileDb = new File(fileRoot, studentIdToString);
				String fileName = file.getOriginalFilename();
				
				File filePath = new File(fileDb, fileName);
				
				if(!filePath.getParentFile().exists()) {
					filePath.getParentFile().mkdirs();
				}
				
				file.transferTo(new File(fileDb+File.separator+fileName));
				
				int num = studentService.uploadShujuku(studentId, filePath.toString());
				log.info("添加了"+num+"条信息");
				
				model.addAttribute("message", "上传数据库设计报告成功");
				return "student/main.jsp";
			}else {
				model.addAttribute("message", "上传数据库设计报告出错");
				return "student/main.jsp";
			}
		}
	}
	
	@RequestMapping(value="/fileDelete")
	public String fileDelete(HttpServletRequest request,HttpServletResponse response, @RequestParam("filePath") String filePath,@RequestParam("fileName") String fileName, Model model) throws Exception {
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		fileName = new String(fileName.getBytes("iso-8859-1"),"utf-8");
		filePath = new String(filePath.getBytes("iso-8859-1"),"utf-8");
		File deleteFile = new File(filePath);
		String message = "";
		boolean flag = false;
		if(deleteFile.exists()) {
			flag = deleteFile.delete();
			if(flag ) {
				message = "删除成功";
			}else {
				message = "删除失败";
			}
			
		}else {
			message = "文件不存在";
		}
		
		ThesisInformation thesis = studentService.getInfoByFilePath(filePath);
		if(thesis== null || "".equals(thesis)) {
			
		}else {
			int num1 = studentService.deleteThesisInformation(studentId);
			log.info("成功删除论文文件");
		}
		
		StudentTaskBookOpening STBO = studentService.getInfoByTaskBookPath(filePath);
		if(STBO == null || "".equals(STBO)) {
			
		}else {
			int num = studentService.resetTaskBook(studentId);
			log.info("成功删除任务书");
		}
		
		StudentTaskBookOpening STBO2 = studentService.getInfoByOpeningPath(filePath);
		if(STBO2==null || "".equals(STBO2) ) {
			
		}else {
			int num = studentService.resetOpening(studentId);
			log.info("成功删除开题报告");
		}
		
		model.addAttribute("message", "成功删除一个文档");
	
		return "student/main.jsp";
	}
	
	@RequestMapping(value="/openingResult")
	public String studentOpeningResult(HttpServletRequest request,Model model) {
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		StudentTaskBookOpening STBO = studentService.getSTBOInfoById(studentId);
		if(STBO == null || "".equals(STBO)) {
			model.addAttribute("message", "尚未提交开题报告");
			return "student/studentOpeningResult.jsp";
		}else {
			int completion = STBO.getCompletion();
			if(completion == 0) {
				model.addAttribute("message", "你的开题报告还未审核，请耐心等待");
			}else if(completion == 1) {
				model.addAttribute("message", "你的开题报告未通过，请修改后重新提交");
			}else {
				model.addAttribute("message", "开题报告已通过");
			}
		}
		return "student/studentOpeningResult.jsp";
	}
	
	@RequestMapping(value="/wendangResult")
	public String studentwendangResult(HttpServletRequest request,Model model) {
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		StudentTaskBookOpening STBO = studentService.getSTBOInfoById(studentId);
		Map<String, String> wendangList = new HashMap<String, String>();
		wendangList.put("open", STBO.getOpenscore());
		wendangList.put("kexing", STBO.getKexingscore());
		wendangList.put("xuqiu", STBO.getXuqiuscore());
		wendangList.put("gaiyao", STBO.getGaiyaoscore());
		wendangList.put("shujuku", STBO.getShujukuscore());
/*		if(STBO == null || "".equals(STBO)) {
			model.addAttribute("message", "尚未提交开题报告");
			return "student/studentOpeningResult.jsp";
		}else {
			int completion = STBO.getCompletion();
			if(completion == 0) {
				model.addAttribute("message", "你的开题报告还未审核，请耐心等待");
			}else if(completion == 1) {
				model.addAttribute("message", "你的开题报告未通过，请修改后重新提交");
			}else {
				model.addAttribute("message", "开题报告已通过");
			}
		}*/
		log.info(wendangList);
		if(wendangList==null) {
			model.addAttribute("message", "还未提交任何文档");
		}
		model.addAttribute("wendangList", wendangList);
		return "student/studentWendangResult.jsp";
	}
	
	@RequestMapping(value="/getRealTimeTopic")
	public void getRealTimeTopic(HttpServletResponse response,HttpServletRequest request) throws Exception {
		String message = (String)request.getSession().getServletContext().getAttribute("realTimeTopicMessage");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter write = response.getWriter();
		write.write(JSONObject.toJSONString(message));
		write.close();
	}
	
	@RequestMapping(value="/uploadThesisInformation")
	public String studentUploadThesisInformation(HttpServletRequest request, Model model,@RequestParam("file") MultipartFile file) throws Exception {
		
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		
		String studentIdToString = String.valueOf(studentId);
		
		Topic topic = studentService.chosenThesisTitle(studentId);
		if(topic == null || "".equals(topic)) {
			model.addAttribute("message", "无法上传毕业论文");
			return "student/main.jsp";
		}else {
			
			StudentTaskBookOpening stbo  = studentService.getSTBOInfoById(studentId);
			if(stbo == null || "".equals(stbo)) {
				model.addAttribute("message", "开题报告还未上传或还未审核，无法上传");
				return "student/main.jsp";
			}else {
				int completion = stbo.getCompletion();
				if(completion == 2) {
					if(!file.isEmpty()) {
						
						File fileRoot = new File("E:\\BSM\\student");
						File fileDb = new File(fileRoot, studentIdToString);
						String fileName = file.getOriginalFilename();
						
						File filePath = new File(fileDb, fileName);
						
						if(!filePath.getParentFile().exists()) {
							filePath.getParentFile().mkdirs();
						}
						
						file.transferTo(new File(fileDb+File.separator+fileName));
						
						int num = studentService.uploadThesisInformation(studentId, filePath.toString());
						log.info("添加了"+num+"条信息");
						
						model.addAttribute("message", "上传论文成功");
						return "student/main.jsp";
					}else {
						model.addAttribute("message", "上传论文出错");
						return "student/main.jsp";
					}
					
				}else {
					
					model.addAttribute("message", "开题报告还未上传或还未审核，无法上传");
					return "student/main.jsp";
					
				}
				
				
				
			}
			}
			
			
	}
	
	
	@RequestMapping(value="/qualification")
	public String studentQualification(HttpServletRequest request,Model model) {
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		
		//ThesisInformation thesis = studentService.getThesisInforInfoByStudentId(studentId);
		List<ThesisInformation> thesis = studentService.getThesisInforInfoByStudentId(studentId);
		for (int i = 0; i <thesis.size() ; i++) {
			ThesisInformation thesisInformation = thesis.get(i);
			if(thesisInformation == null) {
				model.addAttribute("message", "不具备答辩资格");
			}else {
				int status = thesisInformation.getStatus();
				if(status == 0) {
					model.addAttribute("message", "不具备答辩资格");
				}else if(status == 1) {
					model.addAttribute("message", "不具备答辩资格");
				}else {
					model.addAttribute("message", "你已具备答辩资格");
				}

			}
		}


		return "student/studentQualifications.jsp";
	}
	
	
	@RequestMapping(value="/studentDoubt")
	public String studentDoubtForm() {
		return "student/studentDoubt.jsp";
	}
	
	@RequestMapping(value="/studentDoubtList")
	public String studentDoubtListForm(Model model,HttpServletRequest request) {
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		List<Doubt> doubtList = studentService.getAllDoubtByStudent(studentId);
		for(int i= 0;i<doubtList.size();i++) {
			String answer = doubtList.get(i).getAnswer();
			if(answer == null || "".equals(answer)) {
				doubtList.get(i).setAnswer("教师还未做出解答");
			}
		}
		model.addAttribute("doubtList", doubtList);
		return "student/studentDoubtList.jsp";
	}
	
	@RequestMapping(value="/studentDoubtToDb")
	public String studentDoubtToDb(Model model,String doubt,HttpServletRequest request) throws Exception {
		Student currentUser = (Student)request.getSession().getAttribute("student");
		int studentId = currentUser.getId();
		doubt = new String(doubt.getBytes("iso-8859-1"),"utf-8");
		Doubt d = new Doubt();
		d.setStudentId(studentId);
		d.setStudentDoubt(doubt);
		studentService.addDoubt(d);
		model.addAttribute("message", "添加疑问成功");
		return "student/main.jsp";
	}
	
	@RequestMapping(value="/getThesisDescById")
	public void getThesisDescById(Model model,int topicId,HttpServletResponse response) throws Exception {
		String description = studentService.getThesisDesc(topicId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("desc", description);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter write = response.getWriter();
		write.write(JSONObject.toJSONString(map));
		write.close();
	}
}
