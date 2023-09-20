package com.iamcaster.menu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iamcaster.menu.domain.Notice;
import com.iamcaster.menu.repository.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;
	
	public List<Notice> getAllNotice(){
		return noticeRepository.selectAllNotice();
	}
}
