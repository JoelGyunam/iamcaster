package com.iamcaster.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iamcaster.user.service.UserInfoService;
import com.iamcaster.user.service.UserNicknameService;

@RestController
@RequestMapping("/rest/nickname")
public class UserNicknameRestController {

	@Autowired
	private UserNicknameService userNicknameService;
	@Autowired
	private UserInfoService userInfoService;
	
	@PostMapping("/ifDuplicated")
	public Map<String,Object> ifDuplicatedNickname(@RequestParam("nickname") String nickname){
		Map<String,Object> resultMap = new HashMap<>();
		
		boolean dupCheck = userNicknameService.ifDuplicatedNickname(nickname);
		resultMap.put("ifDuplicated", dupCheck);
		
		if(!dupCheck) {
			int NickID = userNicknameService.insertNickname(nickname);
			resultMap.put("NickID",NickID);
		}
		return resultMap;
	}
	
	@PostMapping("/removeNickname")
	public Map<String,String> removeNickname(@RequestParam("NickID") int NickID){
		Map<String,String> resultMap = new HashMap<>();

		int result = userNicknameService.deleteNicknameByNickID(NickID);
		if(result==1) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}

	@PostMapping("/changeNickname")
	public Map<String,String> changeNickname(@RequestParam("nickname") String nickname, HttpSession session){
		Map<String,String> resultMap = new HashMap<>();
		int UID = (int) session.getAttribute("UID");
		int currentNickID = userInfoService.getUserInfo(UID).getNickID();
		Map<String,Object> dupChecker = ifDuplicatedNickname(nickname);
		boolean ifDup = (boolean) dupChecker.get("ifDuplicated");
		if(ifDup) {
			resultMap.put("result", "duplicatedNickname");
		} else {
			String removeResult = removeNickname(currentNickID).get("result");
			if(removeResult.equals("success")) {
				int newNickID = (int) dupChecker.get("NickID");
				int newSetResult = userNicknameService.setUIDforNickname(UID, newNickID);
				if(newSetResult == 1) {
					int updateUserInfo = userInfoService.updateNickID(UID, newNickID);
					if(updateUserInfo == 1) {
						resultMap.put("result", "success");
					}
				}
			} else {
				resultMap.put("result", "deleteError");
			}
		}
		return resultMap;
	}
}
