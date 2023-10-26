package com.iamcaster.user.domain;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userNickname")
@ToString
@Builder(toBuilder = true)

public class UserNickname {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="NickID")
	private int NickID;
	
	private String nickname;
	
	private Integer UID;
	
	@Column(name = "createdAt", updatable=false)
	@UpdateTimestamp
	private ZonedDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updatedAt")
	private ZonedDateTime updatedAt;	
	
}
