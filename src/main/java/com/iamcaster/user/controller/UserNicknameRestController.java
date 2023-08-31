package com.iamcaster.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iamcaster.user.service.UserNicknameService;

@RestController
@RequestMapping("/rest/nickname")
public class UserNicknameRestController {

	@Autowired
	private UserNicknameService userNicknameService;
	
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

}
