package com.zc.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zc.entity.Student;
import com.zc.entity.Teacher;
import com.zc.entity.User;
import com.zc.service.IDepartmentService;
import com.zc.service.IMajorService;
import com.zc.service.IStudentService;
import com.zc.service.ITeacherService;
import com.zc.service.IUserService;

/**
 * @date 2021-3-6
 * @author z
 * adminLogin() 管理员登陆
 * teacherLogin() 教师登陆
 * studentLogin() 学生登陆
 * quitSystem() 退出系统
 * modifyPassword() 修改密码，由于管理员，教师，学生的修改密码相同，所以只用一个controller作为修改。
 *
 * @date 2021-3-11
 * @author z
 * 修改了teacherLogin() 方法，新增了查询教师信息的功能。并且写入session中。
 *
 */

@Controller
public class UserController {

	private static final Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ITeacherService teacherService;
	
	@Autowired
	private IDepartmentService departmentService;
	
	@Autowired
	private IStudentService studentService;
	
	@Autowired
	private IMajorService majorService;

	//管理员登录
	@RequestMapping(value="/admin/login")
	public String adminLogin(String userNo,String password,Model model,HttpServletRequest request) {
		User currentUser = userService.login(userNo, password);
		if("".equals(currentUser)||currentUser==null) {
			model.addAttribute("message", "用户名或密码错误");
			return "../../admin/adminLogin.jsp";
		}
		if(currentUser.getPermission()==3) {
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(3600);
			session.setAttribute("currentUser", currentUser);
			return "admin/main.jsp";
		}else {
			model.addAttribute("message", "当前用户不是管理员,请用管理员用户登录");
			return "../../admin/adminLogin.jsp";
		}
	}

	//教师登录
	@RequestMapping(value="/teacher/login")
	public String teacherLogin(String userNo,String password,Model model,HttpServletRequest request) {
		User currentUser = userService.login(userNo, password);
		if("".equals(currentUser)||currentUser==null) {
			model.addAttribute("message", "用户名或密码错误");
			return "../../teacher/teacherLogin.jsp";
		}
		if(currentUser.getPermission()==2) {
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(3600);
			// 在t_user表的信息
			session.setAttribute("currentUser", currentUser);
			// 完整的teacher信息
			Teacher teacher = teacherService.showInfoByNo(userNo);
			int depId = teacher.getDepartmentId();
			String depName = departmentService.getNameById(depId);
			teacher.setDepartmentName(depName);
			session.setAttribute("teacher", teacher);
			return "teacher/main.jsp";
		}else {
			model.addAttribute("message", "当前用户不是教师,请用教师用户登录");
			return "../../teacher/teacherLogin.jsp";
		}
	}


	//学生登录
	@RequestMapping(value="/student/login")
	public String studentLogin(String userNo,String password,Model model,HttpServletRequest request) {
		User currentUser = userService.login(userNo, password);
		if("".equals(currentUser)||currentUser==null) {
			model.addAttribute("message", "用户名或密码错误");
			return "../../student/studentLogin.jsp";
		}
		//判断权限 学生权限-1 教师权限-2 管理员权限-3
		if(currentUser.getPermission()==1) {
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(3600);
			// t_user 表中的关于学生的信息
			session.setAttribute("currentUser", currentUser);
			// t_student 表中的信息
			Student student = studentService.getStudentByNO(userNo);
			String majorName = majorService.getNameById(student.getMajorId());
			student.setMajorName(majorName);
			session.setAttribute("student", student);
			return "student/main.jsp";
		}else {
			model.addAttribute("message", "当前用户不是学生,请用学生用户登录");
			return "../../student/studentLogin.jsp";
		}
	}
	
	@RequestMapping(value="/quit")
	public String quitSystem(Model model,HttpServletRequest request) { 
		
		request.getSession().invalidate();
		
		return "../../index.jsp";
	}
	
	@RequestMapping(value="/modifyPassword")
	public String modifyPassword(Model model,String newPassword1,String currentUserNo) {
		
		/*log.info("新的密码："+newPassword1);
		log.info("账户："+currentUserNo);*/
		int num = userService.modifyPassword(currentUserNo, newPassword1);
		log.info("修改了"+num+"条数据");
		model.addAttribute("num", num);
		return "modifySuccess.jsp";
	}
	
}
