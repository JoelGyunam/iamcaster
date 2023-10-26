package com.iamcaster.regional.userregion.domain;

import java.time.ZonedDateTime;

import javax.persistence.AttributeOverride;
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
@Table(name="region")
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserRegion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int RGID;
	
	@Column(name="regSourceID")
	private String regSourceID;
	
	@Column(name="STN_ID")
	private int STN_ID;
	
	@Column(name="regionName")
	private String regionName;
	private double lat;
	private double lon;
	
	@Column(name="createdAt", updatable=false)
	@UpdateTimestamp
	private ZonedDateTime createdAt;
	
	@Column(name="updatedAt")
	@UpdateTimestamp
	private ZonedDateTime updatedAt;
}
