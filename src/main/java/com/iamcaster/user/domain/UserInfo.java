package com.iamcaster.user.domain;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="userInfo")
@Entity
public class UserInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int UID;
	private String email;
	private String password;
	private String salt;
	
	@Column(name="NickID")
	private int NickID;
	private int RGID;
	
	@Column(name="optionalTerms")
	private ZonedDateTime optionalTerms;
	
	@Column(name="createdAt", updatable=false)
	@UpdateTimestamp
	private ZonedDateTime createdAt;
	
	@Column(name="updatedAt")
	@UpdateTimestamp
	private ZonedDateTime updatedAt;
	
	@Column(name="ifKakao")
	private boolean ifKakao;
	
	@Transient
	private boolean ifOptionalTermsAgreed;
	
}
