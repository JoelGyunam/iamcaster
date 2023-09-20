package com.iamcaster.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.iamcaster.menu.domain.Notice;
import com.iamcaster.menu.service.NoticeService;

@Controller
@RequestMapping("/main/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping()
	public String noticeMenuView(Model model) {
		
		model.addAttribute("noticeList",noticeService.getAllNotice());
		return "notice/notice-main";
	}
	
	@GetMapping("/noticeID")
	public String noticeEachView(@RequestParam("id") int noticeID, Model model) {
		
		List<Notice> noticeList = noticeService.getAllNotice();
		if(noticeList.size()>0) {
			for(Notice selectNotice : noticeList) {
				if(selectNotice.getNoticeID()==noticeID) {
					model.addAttribute("notice",selectNotice);
					continue;
				}
			}
		}
		
		return "notice/notice-each";
	}
}
