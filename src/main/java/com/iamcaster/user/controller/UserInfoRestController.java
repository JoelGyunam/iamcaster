package com.iamcaster.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iamcaster.user.domain.UserInfo;
import com.iamcaster.user.service.UserInfoService;
import com.iamcaster.user.service.UserNicknameService;

@RequestMapping("/rest")
@RestController
public class UserInfoRestController {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserNicknameService userNicknameService;
	
	@GetMapping("/reg/emailverify/ifDuplicated")
	public Map<String,Boolean> ifDuplicated(@RequestParam("email") String email){
		Boolean result = userInfoService.ifRegisteredEmail(email);
		Map<String,Boolean> resultMap = new HashMap<>();
		resultMap.put("ifDuplicated", result);
		return resultMap;
	}
	
	
	@PostMapping("/reg/submit")
	public Map<String,String> registration(
			@RequestParam("email") String email
			,@RequestParam("password") String password
			,@RequestParam("NickID") int NickID
			,@RequestParam("RGID") int RGID
			){
		Map<String,String> resultMap = new HashMap<>();
		UserInfo userInfo = new UserInfo();
		userInfo = userInfoService.registration(email, password, NickID, RGID);
		if(userInfo != null) {
			resultMap.put("result", "success");
			int UID = userInfo.getUID();
			userNicknameService.setUIDforNickname(UID, NickID);
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}

	@PostMapping("/login/submit")
	public Map<String,String> login(
			HttpSession session
			,@RequestParam("email") String email
			,@RequestParam("password") String password
			){
		Map<String,String> resultMap = new HashMap<>();
		UserInfo userInfo = new UserInfo();
		userInfo = userInfoService.login(email, password);
		if(userInfo==null) {
			resultMap.put("result","fail");
		} else {
			resultMap.put("result","success");
		}
		return resultMap;
	}
}
