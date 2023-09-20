package com.iamcaster.menu.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.iamcaster.menu.domain.Notice;

@Repository
public interface NoticeRepository {

	public List<Notice> selectAllNotice();
	
}
