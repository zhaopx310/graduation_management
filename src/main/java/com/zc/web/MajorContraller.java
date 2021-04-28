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
import com.zc.entity.Major;
import com.zc.service.IMajorService;


/**
 * @author z
 * showAllMajor() 获得所有专业信息
 */

@Controller
public class MajorContraller {

	private static final Logger log = Logger.getLogger(MajorContraller.class);

	@Autowired
	private IMajorService majorService;

	//学生修改个人信息
	@RequestMapping(value="/getAllMajor")
	public String showAllMajor(HttpServletResponse response,HttpServletRequest request) throws Exception {
		List<Major> majors = majorService.allMajor();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter write = response.getWriter();
		write.write(JSONArray.toJSONString(majors));
		write.close();
		return "admin/adminStudentAdd.jsp";
	}
}
