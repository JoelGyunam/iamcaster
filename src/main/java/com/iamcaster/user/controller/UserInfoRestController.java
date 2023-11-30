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
import com.iamcaster.user.dto.BooleanResponse;
import com.iamcaster.user.dto.ResultResponse;
import com.iamcaster.user.service.UserInfoService;
import com.iamcaster.user.service.UserNicknameService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"회원정보 등록, 수정, 삭제"})
@RequestMapping("/rest")
@RestController
public class UserInfoRestController {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserNicknameService userNicknameService;
	
	@ApiOperation(value="회원탈퇴")
	@PostMapping("/withdrawal")
	public Map<String,String> withdrawal(@ApiIgnore HttpSession session){
		Map<String,String> resultMap = new HashMap<>();
		int UID = (int) session.getAttribute("UID");
		String result = userInfoService.withdrawal(UID);
		resultMap.put("result", result);
		
		if("bye".equals(result)) {
			session.removeAttribute("UID");
		};
		
		return resultMap;
	}
	
	@ApiOperation(value="로그아웃")
	@PostMapping("/logout")
	public ResultResponse logout(@ApiIgnore HttpSession session){
		ResultResponse result = new ResultResponse();
		session.removeAttribute("UID");
		result.setResult("success");
		return result;
	}
	
	@ApiOperation(value="활동지역 변경")
	@GetMapping("/userinfo/edit/rgid")
	public ResultResponse updateRGID(@ApiIgnore HttpSession session, 
			@ApiParam("RGID") @RequestParam("RGID") int RGID){
		ResultResponse result = new ResultResponse();
		int UID = (int) session.getAttribute("UID");
		int updateResult = userInfoService.updateRGID(UID, RGID);
		if(updateResult == 1) {
			result.setResult("success");
		} else {
			result.setResult("fail");
		}
		return result;
	}
	
	@ApiOperation(value="선택약관 동의여부")
	@GetMapping("/terms/optionalAgreed")
	public ResultResponse optionaltermsUpdate(@RequestParam("UID") int UID, 
			@ApiParam("ifAgreed") @RequestParam("ifAgreed") boolean ifAgreed){
		ResultResponse result = new ResultResponse();
		int isAgreed = userInfoService.optionalTermsSubmit(UID, ifAgreed);
		if(isAgreed == 1) {
			result.setResult("success");
		} else {
			result.setResult("fail");
		};
		return result;
	};
	
	
	@ApiOperation(value="이메일 중복 확인")
	@GetMapping("/reg/emailverify/ifDuplicated")
	public BooleanResponse ifDuplicated(@ApiParam("email") @RequestParam("email") String email){
		Boolean result = userInfoService.ifRegisteredEmail(email);
		BooleanResponse booleanResponse = new BooleanResponse();
		Map<String,Boolean> resultMap = new HashMap<>();
		resultMap.put("ifDuplicated", result);
		booleanResponse.setIfDuplicated(result);
		booleanResponse.setResult(result);
		return booleanResponse;
	}
	
	@ApiOperation(value="회원가입 시도")
	@PostMapping("/reg/submit")
	public ResultResponse registration(
			@RequestParam("email") String email
			,@RequestParam("password") String password
			,@RequestParam("NickID") int NickID
			,@RequestParam("RGID") int RGID
			,@RequestParam("optionalTerms") boolean ifOptionalTermsAgreed
			,@RequestParam(value="ifKakao", required=false) boolean ifkakao
			,@ApiIgnore HttpSession session
			){
		ResultResponse result = new ResultResponse();
		UserInfo userInfo = new UserInfo();
		userInfo = userInfoService.registration(email, password, NickID, RGID, ifOptionalTermsAgreed, ifkakao);
		if(userInfo != null) {
			result.setResult("success");
			int UID = userInfo.getUID();
			userNicknameService.setUIDforNickname(UID, NickID);
			session.setAttribute("UID", UID);
		} else {
			result.setResult("fail");
		}
		return result;
	}

	@ApiOperation(value = "로그인 시도")
	@PostMapping("/login/submit")
	public ResultResponse login(
			@ApiIgnore HttpSession session
			,@ApiParam(value="email") @RequestParam("email") String email
			,@ApiParam(value="password") @RequestParam("password") String password
			){
		UserInfo userInfo = new UserInfo();
		userInfo = userInfoService.login(email, password);
		ResultResponse result = new ResultResponse();
		if(userInfo==null) {
			result.setResult("fail");
		} else {
			result.setResult("success");
			session.setAttribute("UID", userInfo.getUID());
		}
		return result;
	};
	
	@ApiOperation(value = "둘러보기 계정")
	@PostMapping("/login/tester")
	public ResultResponse testerLogin(@ApiIgnore HttpSession session){
		UserInfo userInfo = userInfoService.testerLogin();
		ResultResponse result = new ResultResponse();
		if(userInfo!=null) {
			session.setAttribute("UID", userInfo.getUID());
			result.setResult("success");
		} else {
			result.setResult("fail");
		}
		return result;
	};
	
	@ApiOperation("임시비밀번호 발급")
	@PostMapping("/login/tempPW")
	public ResultResponse tempPW(@RequestParam("email") String email){
		ResultResponse result = new ResultResponse();
		Integer mailSendResult = userInfoService.sendTempPW(email.trim());
		if(mailSendResult == null || mailSendResult != 1) {
			result.setResult("fail");
		} else {
			result.setResult("success");
		}
		return result;
	}

	@PostMapping("/info/changePW")
	public ResultResponse changePW(@ApiIgnore HttpSession session, @RequestParam("newPassword")String newPassword){
		ResultResponse result = new ResultResponse();
		int UID = (int) session.getAttribute("UID");
		int changeResult = userInfoService.changePW(UID,newPassword);
		if(changeResult==1) {
			result.setResult("success");
		} else {
			result.setResult("fail");
		}
		return result;
	}
}
