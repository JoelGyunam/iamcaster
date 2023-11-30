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

import io.swagger.annotations.ApiModelProperty;
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

	@ApiModelProperty(value = "사용자Key", example = "1")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int UID;

	@ApiModelProperty(value = "사용자ID", example = "abc@iam.cater")
	private String email;
	
	@ApiModelProperty(value = "사용자비밀번호", example = "abc123abc")
	private String password;

	private String salt;
	
	@ApiModelProperty(value = "닉네임Key", example = "1")
	@Column(name="NickID")
	private int NickID;

	@ApiModelProperty(value = "사용자지역Key", example = "1")
	private int RGID;
	
	@ApiModelProperty(value = "선택약관 동의 철회 일시", example = "2022-04-18T09:00:00+09:00[Asia/Seoul]")
	@Column(name="optionalTerms")
	private ZonedDateTime optionalTerms;
	
	@Column(name="createdAt", updatable=false)
	@UpdateTimestamp
	private ZonedDateTime createdAt;
	
	@Column(name="updatedAt")
	@UpdateTimestamp
	private ZonedDateTime updatedAt;
	
	@ApiModelProperty(value = "카카오 연동 여부", example = "true")
	@Column(name="ifKakao")
	private boolean ifKakao;
	
	@Transient
	private boolean ifOptionalTermsAgreed;
	
}
