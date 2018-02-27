/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.service.TreeService;
import com.scnjwh.intellectreport.modules.sys.dao.OfficeDao;
import com.scnjwh.intellectreport.modules.sys.dao.RoleDao;
import com.scnjwh.intellectreport.modules.sys.dao.UserDao;
import com.scnjwh.intellectreport.modules.sys.entity.JsonTree;
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import com.scnjwh.intellectreport.modules.sys.entity.Role;
import com.scnjwh.intellectreport.modules.sys.entity.User;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private OfficeDao officeDao;

    public List<Office> findAll() {
        return UserUtils.getOfficeList();
    }

    public List<Office> findList(Boolean isAll) {
        if (isAll != null && isAll) {
            return UserUtils.getOfficeAllList();
        } else {
            return UserUtils.getOfficeList();
        }
    }


    @Transactional(readOnly = true)
    public List<Office> findList(Office office) {
        if (office != null) {
            office.setParentIds(office.getParentIds() + "%");
            return dao.findByParentIdsLike(office);
        }
        return new ArrayList<Office>();
    }

    @Transactional(readOnly = false)
    public void save(Office office) {
        super.save(office);
        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }

    @Transactional(readOnly = false)
    public void delete(Office office) {
        super.delete(office);
        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }
    
    public List<Office> findListByfTypeCode(Office office){
        return dao.findListByfTypeCode(office);
    }
    
    public List<Office> findListByFOfficeType(String fOfficeType){
    	return dao.findListByFOfficeType(fOfficeType);
    }

    public List<Office> findListByParentId(Office office){
        return officeDao.findListByParentId(office);

    }
    public List<Office> findListByType(Office office){
        return officeDao.findListByType(office);

    }

    /***
     * 获取JsonTrees
     * @return
     * @Author : pengjunhao. create at 2017年4月21日 上午11:20:57
     */
    public List<JsonTree> getJsonTrees() {
        //公司-》部门-》角色-》用户
        List<JsonTree> jsonTreess = new ArrayList<JsonTree>();
        List<JsonTree> jsonTrees = new ArrayList<JsonTree>();
        User user = UserUtils.getUser();
        //获取当前公司
        String companyId = user.getCompany().getParentId() + "," + user.getCompany().getId();
        //获取下级公司用户
        JsonTree jsonTree = new JsonTree();
        jsonTree.setId(user.getCompany().getId());
        jsonTree.setIsChecked(false);
        jsonTree.setState("closed");
        jsonTree.setType("1");
        jsonTree.setText(user.getCompany().getName());
        getCompanyUser(jsonTrees, companyId + ",");
        jsonTree.setChildren(jsonTrees);
        jsonTreess.add(jsonTree);
        return jsonTreess;
    }

    /**
     * 获取公司下的用户
     * @param jsonTrees 树
     * @param companyId 公司id
     * @Author : pengjunhao. create at 2017年4月21日 上午11:25:08
     */
    private void getCompanyUser(List<JsonTree> jsonTrees, String parentIds) {
        //公司-》部门-》角色-》用户
        //获取分公司
        List<Office> offices = new ArrayList<Office>();
        Office office = new Office();
        office.setParentIds(parentIds);
        office.setType("1");
        offices = officeDao.findListByParent(office);
        if (offices.size() > 0) {
            for (Office office2 : offices) {
                List<JsonTree> getChil = new ArrayList<JsonTree>();
                getCompanyUser(getChil, parentIds + office2.getId() + ",");
                if(getChil.size()<=0){
                    continue;
                }
                JsonTree jsonTree = new JsonTree();
                jsonTree.setId(office2.getId());
                jsonTree.setIsChecked(false);
                jsonTree.setText(office2.getName());
                jsonTree.setType("1");
                jsonTree.setState("closed");
                jsonTree.setChildren(getChil);
                jsonTrees.add(jsonTree);
            }
        }

        //获取当前部门
        office.setParentIds(parentIds);
        office.setType("2");
        offices = officeDao.findListByParent(office);
        for (Office off : offices) {
            List<JsonTree> getChil = getOfficeUser(off.getId(), parentIds);
            if (getChil.size() > 0) {
                JsonTree jsonTree = new JsonTree();
                jsonTree.setId(off.getId());
                jsonTree.setIsChecked(false);
                jsonTree.setText(off.getName());
                jsonTree.setType("2");
                jsonTree.setState("closed");
                jsonTree.setChildren(getChil);
                jsonTrees.add(jsonTree);
            }
        }
    }

    /**
     * 获取当前部门角色-》用户
     * @param offId 部门id
     * @param parentId 该部门父级编号
     * @return
     * @Author : pengjunhao. create at 2017年4月21日 上午11:39:33
     */
    private List<JsonTree> getOfficeUser(String offId, String parentId) {
        //获取当前部门角色-》用户
        User user = new User();
        Office office = new Office();
        office.setId(offId);
        user.setOffice(office);
        //获取全部用户
        List<User> users = userDao.findList(user);
        List<JsonTree> jsons = new ArrayList<JsonTree>();
        if (users.size() > 0) {
            //有用户
            //查看该部门用户
            for (User use : users) {
                JsonTree jsonTre = new JsonTree();
                jsonTre.setId(use.getId());
                jsonTre.setText(use.getName());
                jsonTre.setIsChecked(false);
                jsonTre.setType("3");
                jsons.add(jsonTre);
            }
        }
        //获取部门下部门的用户
        getCompanyUser(jsons, parentId + offId + ",");
        return jsons;
    }

    /**
     * 获取用户组名称
     * @param ids id集合
     * @return
     * @Author : pengjunhao. create at 2017年4月24日 上午11:25:31
     */
    public String getUserName(String ids) {
        String[] idList = ids.split(",");
        String name = "";
        for (String id : idList) {
            User user = userDao.get(id);
            name += user.getName() + ",";
        }
        return name;
    }

    /**
     * 获取部门下角色
     * @return
     * @Author : pengjunhao. create at 2017年5月10日 上午9:57:51
     */
    public List<JsonTree> getJsonTreesRole() {
        List<JsonTree> jsonTreess = new ArrayList<JsonTree>();
        List<JsonTree> jsonTrees = new ArrayList<JsonTree>();
        User user = UserUtils.getUser();
        //获取当前公司
        String companyId = user.getCompany().getParentId() + "," + user.getCompany().getId();
        //获取下级公司角色
        JsonTree jsonTree = new JsonTree();
        jsonTree.setId(user.getCompany().getId());
        jsonTree.setIsChecked(false);
        jsonTree.setState("closed");
        jsonTree.setType("1");
        jsonTree.setText(user.getCompany().getName());
        getCompanyRole(jsonTrees, companyId + ",");
        //获取当前部门下角色
        Role role = new Role();
        Office office = new Office();
        office.setId(user.getCompany().getId());
        role.setOffice(office);
        List<JsonTree> jsons = new ArrayList<JsonTree>();
        List<Role> roles = roleDao.findList(role);
        if (roles.size() > 0) {
            for (Role rolee : roles) {
                JsonTree jsonTre = new JsonTree();
                jsonTre.setId(rolee.getId());
                jsonTre.setText(rolee.getName());
                jsonTre.setIsChecked(false);
                jsonTre.setType("4");
                jsons.add(jsonTre);
            }
        }
        jsonTrees.addAll(jsons);
        jsonTree.setChildren(jsonTrees);
        jsonTreess.add(jsonTree);
        return jsonTreess;
    }

    /**
     * 获取公司下的角色和部门
     * @param jsonTrees
     * @param string
     * @Author : pengjunhao. create at 2017年5月10日 上午10:02:33
     */
    private void getCompanyRole(List<JsonTree> jsonTrees, String parentIds) {
        List<Office> offices = new ArrayList<Office>();
        Office office = new Office();
        office.setParentIds(parentIds);
        office.setType("1");
        offices = officeDao.findListByParent(office);
        if (offices.size() > 0) {
            for (Office office2 : offices) {
                //获取当前部门下角色
                Role role = new Role();
                office = new Office();
                office.setId(office2.getId());
                role.setOffice(office);
                List<JsonTree> jsons = new ArrayList<JsonTree>();
                List<Role> roles = roleDao.findList(role);
                if (roles.size() > 0) {
                    for (Role rolee : roles) {
                        JsonTree jsonTre = new JsonTree();
                        jsonTre.setId(rolee.getId());
                        jsonTre.setText(rolee.getName());
                        jsonTre.setIsChecked(false);
                        jsonTre.setType("4");
                        jsons.add(jsonTre);
                    }
                }
                getCompanyRole(jsons, parentIds + office2.getId() + ",");
                if(jsons.size()<=0){
                    continue;
                }
                JsonTree jsonTree = new JsonTree();
                jsonTree.setId(office2.getId());
                jsonTree.setIsChecked(false);
                jsonTree.setText(office2.getName());
                jsonTree.setType("1");
                jsonTree.setState("closed");
                jsonTree.setChildren(jsons);
                jsonTrees.add(jsonTree);
            }
        }
        //获取当前部门
        office.setParentIds(parentIds);
        office.setType("2");
        offices = officeDao.findListByParent(office);
        for (Office off : offices) {
            List<JsonTree> getChil = getOfficeRole(off.getId(), parentIds);
            if (getChil.size() > 0) {
                JsonTree jsonTree = new JsonTree();
                jsonTree.setId(off.getId());
                jsonTree.setIsChecked(false);
                jsonTree.setText(off.getName());
                jsonTree.setType("2");
                jsonTree.setState("closed");
                jsonTree.setChildren(getChil);
                jsonTrees.add(jsonTree);
            }
        }
    }

    /**
     * 获取部门下角色
     * @param offId
     * @param parentId
     * @return
     * @Author : pengjunhao. create at 2017年5月10日 上午10:04:16
     */
    private List<JsonTree> getOfficeRole(String offId, String parentId) {
        Role role = new Role();
        Office office = new Office();
        office.setId(offId);
        role.setOffice(office);
        List<JsonTree> jsons = new ArrayList<JsonTree>();
        List<Role> roles = roleDao.findList(role);
        if (roles.size() > 0) {
            for (Role rolee : roles) {
                JsonTree jsonTre = new JsonTree();
                jsonTre.setId(rolee.getId());
                jsonTre.setText(rolee.getName());
                jsonTre.setIsChecked(false);
                jsonTre.setType("4");
                jsons.add(jsonTre);
            }
        }
        getCompanyRole(jsons, parentId + offId + ",");
        return jsons;
    }

    
    /**
     * 获取角色名称
     * @param ids
     * @return
     * @Author : pengjunhao. create at 2017年5月10日 上午10:50:57
     */
    public String getRoleNames(String ids) {
        String[] idss = ids.split(",");
        String names = "";
        for (String id : idss) {
            names += roleDao.get(id).getName() + ",";
        }
        return names;
    }

}
