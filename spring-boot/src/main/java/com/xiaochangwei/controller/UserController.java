package com.xiaochangwei.controller;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaochangwei.entity.User;
import com.xiaochangwei.mapper.UserMapper;
import com.xiaochangwei.vo.UserParamVo;

/**
 * @since 2017年2月7日 下午2:06:11
 * @author 肖昌伟 317409898@qq.com
 * @description
 */

@RestController
public class UserController {

	@Value("${spring.datasource.url}")
	private String dataSourceURL;

	protected static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserMapper userDao;

	@RequestMapping("/count/{tableName}")
	public int dataCount(@PathVariable String tableName) {
		return userDao.dataCount(tableName);
	}

	@RequestMapping(value = "/users", method = { RequestMethod.GET })
	public List<User> users(UserParamVo param) {
		System.out.println("dataSourceURL:" + dataSourceURL);
		return userDao.getUsers(param);
	}

	@RequestMapping(value = "/insert", method = { RequestMethod.GET })
	public String insert(String name,String pwd,String salt) {
		User user = new User();
		user.setName(name);
		user.setPwd(pwd);
		user.setSalt(salt);
		System.out.println("dataSourceURL:" + dataSourceURL);
		try {
			userDao.insert(user);
			return "保存成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "保存失败";
		}
	}

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Resource(name = "stringRedisTemplate")
	ValueOperations<String, String> valueOperationStr;

	@RequestMapping("/redis/string/set")
	public String setKeyAndValue(String key, String value) {
		logger.debug("访问set:key={},value={}", key, value);
		valueOperationStr.set(key, value);
		return "Set Ok";
	}

	@RequestMapping("/redis/string/get")
	public String getKey(String key) {
		logger.debug("访问get:key={}", key);
		return valueOperationStr.get(key);
	}

	@Autowired
	RedisTemplate<Object, Object> redisTemplate;

	@Resource(name = "redisTemplate")
	ValueOperations<Object, Object> valOps;

	@RequestMapping("/redis/obj/set")
	public void save(User user) {
		valOps.set(user.getId(), user);
	}

	@RequestMapping("/redis/obj/get")
	public User getPerson(String id) {
		return (User) valOps.get(id);
	}

	@RequestMapping("date/test")
	public User dateTest() {
		User user = new User();
		user.setName("xiaochangwei");
		user.setPwd("123456");
		return user;
	}
	
	@RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}
