package com.iamcaster.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import com.iamcaster.common.APIService;
import com.iamcaster.user.domain.UserInfo;
import com.iamcaster.user.dto.KakaoUserInfo;
import com.iamcaster.user.dto.OAuthToken;
import com.iamcaster.user.service.UserInfoService;

@Controller
@RequestMapping("/Oauth")
public class KakaoOauthRestController {

	@Autowired
	private APIService aPIService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserInfoController userInfoController;

	@GetMapping("/authorize/callback")
	public String oauthAuthorizeCallback(@RequestParam(value = "code", required = false) String code, Model model, HttpSession session) {

		Map<String, String> resultMap = new HashMap<>();

		if (code != null) {
			try {
				OAuthToken getToken = aPIService.fetchOAuthToken(code);
				if (getToken != null) {
					KakaoUserInfo kakaoUserInfo = aPIService.fetchOAuthUserInfo(getToken.getAccess_token());
					String email = kakaoUserInfo.getKakao_account().getEmail();
					boolean result = userInfoService.ifRegisteredEmail(email);
					if(!result) {
						resultMap.put("result", "newUser");
						resultMap.put("ifDuplicated", Boolean.toString(result));
						resultMap.put("email", email);
					} else {
						UserInfo userInfo = userInfoService.kakaoIntegration(email);
						resultMap.put("result", "success");
						resultMap.put("email", userInfo.getEmail());
						resultMap.put("UID", Integer.toString(userInfo.getUID()));
						session.setAttribute("UID", userInfo.getUID());
					}
					model.addAttribute("kakaoResult", resultMap);
					return userInfoController.loginView(model, session);
				} else {
					resultMap.put("result", "tokenFail");
					model.addAttribute("kakaoResult", resultMap);
					return resultMap.get("result");
				}
			} catch (HttpClientErrorException e) {
				if (e.getStatusCode().isError()) {
					model.addAttribute("kakaoResult", "error");
					return userInfoController.loginView(model, session);
				}
			}
		}

		else {
			resultMap.put("result", "codeFail");
			model.addAttribute("result", resultMap);
		}
		return userInfoController.loginView(model, session);
			
	};

}
