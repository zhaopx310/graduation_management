package com.zc.web;

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONArray;
import com.zc.entity.Department;
import com.zc.service.IDepartmentService;

/**
 * @author z
 * showAllDep() 显示所以院系信息
 */

@Controller
public class DepartmentContraller {

	private static final Logger log = Logger.getLogger(DepartmentContraller.class);

	@Autowired
	private IDepartmentService departmentService;
	
	@RequestMapping(value="/getAllPartment")
	public String showAllDep(HttpServletResponse response,HttpServletRequest request) throws Exception {
		List<Department> departments = departmentService.allDepartment();
		//request.setAttribute("departments", departments);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter write = response.getWriter();
		write.write(JSONArray.toJSONString(departments));
		write.close();
		return "admin/adminTeacherAdd.jsp";
	}
}
