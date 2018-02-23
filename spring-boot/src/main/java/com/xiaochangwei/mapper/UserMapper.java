package com.xiaochangwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiaochangwei.entity.User;
import com.xiaochangwei.vo.UserParamVo;

@Mapper
public interface UserMapper {
	public int dataCount(String tableName);

	public List<User> getUsers(UserParamVo param);

	public void insert(User user);
}
