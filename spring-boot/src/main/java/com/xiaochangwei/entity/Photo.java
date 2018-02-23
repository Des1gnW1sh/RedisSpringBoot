package com.xiaochangwei.entity;

import java.io.Serializable;

/**
 * @since 2017年3月8日 上午10:31:58
 * @author 肖昌伟 317409898@qq.com
 * @description
 */
public class Photo implements Serializable {

	private static final long serialVersionUID = 3080256694777271178L;

	private Long photoId;

	private String photoURL;

	private Long userId;

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
