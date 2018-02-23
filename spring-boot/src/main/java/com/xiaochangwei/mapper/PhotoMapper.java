package com.xiaochangwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiaochangwei.entity.Photo;

/**
 * @since 2017年3月8日 上午10:36:50
 * @author 肖昌伟 317409898@qq.com
 * @description
 */
@Mapper
public interface PhotoMapper {
	public List<Photo> findPhotoByUserId(Long userId);
}
