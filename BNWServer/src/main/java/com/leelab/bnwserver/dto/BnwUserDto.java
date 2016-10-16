package com.leelab.bnwserver.dto;

import java.sql.Date;

public class BnwUserDto {
	private String id;
	private String password;
	private String profile_url;
	private String email;
	private Date birth;
	private String phone;
	private Date create_date;
	
	public BnwUserDto(){}

	public BnwUserDto(String id, String password, String profile_url, String email, Date birth, String phone, Date create_date) {
		this.id = id;
		this.password = password;
		this.profile_url = profile_url;
		this.email = email;
		this.birth = birth;
		this.phone = phone;
		this.create_date = create_date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfile_url() {
		return profile_url;
	}

	public void setProfile_url(String profile_url) {
		this.profile_url = profile_url;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	@Override
	public String toString() {
		return "BnwUserDto [id=" + id + ", password=" + password + ", profile_url=" + profile_url + ", email=" + email
				+ ", birth=" + birth + ", phone=" + phone + ", create_date=" + create_date + "]";
	}
	
}
