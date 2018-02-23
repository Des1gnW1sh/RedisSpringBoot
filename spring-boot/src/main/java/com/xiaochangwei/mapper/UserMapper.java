package com.xiaochangwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiaochangwei.entity.User;
import com.xiaochangwei.vo.UserParamVo;

/**
 * @since 2017年2月7日 下午1:58:46
 * @author 肖昌伟 317409898@qq.com
 * @description
 */
@Mapper
public interface UserMapper {
	public int dataCount(String tableName);

	public List<User> getUsers(UserParamVo param);

	public void insert(User user);
}
