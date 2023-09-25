package com.iamcaster.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KakaoUserInfo {
	private long id;
	@JsonProperty("connected_at")
	private String connected_at;
	@JsonProperty("synched_at")
	private String synched_at;
	private Properties properties;
	private Kakao_account kakao_account;
	
	@Data
	public static class Properties {
		private String nickname;
	}

	@Data
	public static class Kakao_account {
		@JsonProperty("profile_nickname_needs_agreement")
		private boolean profile_nickname_needs_agreement;
		private Profile profile;
		@JsonProperty("has_email")
		private boolean has_email;
		@JsonProperty("email_needs_agreement")
		private boolean email_needs_agreement;
		@JsonProperty("is_email_valid")
		private boolean is_email_valid;
		@JsonProperty("is_email_verified")
		private boolean is_email_verified;
		private String email;
		
		@Data
		public static class Profile{
			private String nickname;
		}
		
	}
}
