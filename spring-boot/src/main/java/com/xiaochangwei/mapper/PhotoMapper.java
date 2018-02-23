package com.xiaochangwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiaochangwei.entity.Photo;

@Mapper
public interface PhotoMapper {
	public List<Photo> findPhotoByUserId(Long userId);
}
