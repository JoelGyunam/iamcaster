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
	
	@PostMapping("/withdrawal")
	public Map<String,String> withdrawal(HttpSession session){
		Map<String,String> resultMap = new HashMap<>();
		int UID = (int) session.getAttribute("UID");
		String result = userInfoService.withdrawal(UID);
		resultMap.put("result", result);
		return resultMap;
	}
	
	@PostMapping("/logout")
	public Map<String,String> logout(HttpSession session){
		Map<String,String> resultMap = new HashMap<>();
		session.removeAttribute("UID");
		resultMap.put("result", "success");
		return resultMap;
	}
	
	@GetMapping("/userinfo/edit/rgid")
	public Map<String,String> updateRGID(HttpSession session, @RequestParam("RGID") int RGID){
		Map<String,String> resultMap = new HashMap<>();
		int UID = (int) session.getAttribute("UID");
		int result = userInfoService.updateRGID(UID, RGID);
		if(result == 1) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	@GetMapping("/terms/optionalAgreed")
	public Map<String,String> optionaltermsUpdate(@RequestParam("UID") int UID, @RequestParam("ifAgreed") boolean ifAgreed){
		Map<String,String> resultMap = new HashMap<>();
		int result = userInfoService.optionalTermsSubmit(UID, ifAgreed);
		if(result == 1) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		};
		return resultMap;
	};
	
	
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
			,@RequestParam("optionalTerms") boolean ifOptionalTermsAgreed
			,HttpSession session
			){
		Map<String,String> resultMap = new HashMap<>();
		UserInfo userInfo = new UserInfo();
		userInfo = userInfoService.registration(email, password, NickID, RGID, ifOptionalTermsAgreed);
		if(userInfo != null) {
			resultMap.put("result", "success");
			int UID = userInfo.getUID();
			userNicknameService.setUIDforNickname(UID, NickID);
			session.setAttribute("UID", UID);
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
			session.setAttribute("UID", userInfo.getUID());
		}
		return resultMap;
	}
	
	@PostMapping("/login/tempPW")
	public Map<String,String> tempPW(@RequestParam("email") String email){
		Map<String,String> resultMap = new HashMap<>();
		int result = userInfoService.sendTempPW(email);
		if(result==1) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}

	@PostMapping("/info/changePW")
	public Map<String,String> changePW(HttpSession session, @RequestParam("newPassword")String newPassword){
		Map<String,String> resultMap = new HashMap<>();
		int UID = (int) session.getAttribute("UID");
		
		int result = userInfoService.changePW(UID,newPassword);
		if(result==1) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
		
	}

}
