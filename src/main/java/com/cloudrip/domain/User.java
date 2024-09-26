package com.cloudrip.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.transaction.Transactional;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Entity
@Table(name="user")
@NoArgsConstructor
@ToString(exclude="reviews")
@Transactional
public class User {
	
	@Id
	private String providerId;
	
	@Column(nullable=false)
	private String provider;
	
	@Column(nullable=false)
	private String roles; //ROLE_USER=일반사용자,ROLE_ADMIN=관리자
	
	
	@Column(nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String username;
	
	@Column(unique=true)
	private String nickname;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private List<Review> reviews = new ArrayList<Review>();
	
	private LocalDateTime userRegdate;

	
	@JoinColumn(name="image_id")
	@OneToOne(fetch = FetchType.EAGER)
	private Image image;
	
	@Builder
	public User(String provider_id, String provider, String roles, String email, String password, String username,
			String nickname, List<Review> reviews, LocalDateTime userRegdate) {
		this.providerId = provider_id;
		this.provider = provider;
		this.roles = roles;
		this.email = email;
		this.password = password;
		this.username = username;
		this.nickname = nickname;
		this.reviews = reviews;
		this.userRegdate = userRegdate;
	}
	
	
////	ROLE_USER,ROLE_ADMIN 이런식일때 스플릿해서 array에 넣어주는 메서드
//	public List<String>getRoleList(){
//		if(this.roles.length()>0) {
//			return Arrays.asList(this.roles.split(","));
//		}
//		return new ArrayList<>();
//	} 
	
}
