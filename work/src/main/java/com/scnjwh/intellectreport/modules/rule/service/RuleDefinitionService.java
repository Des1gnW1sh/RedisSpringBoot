/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.rule.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.service.CrudService;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.modules.func.entity.FunctionDataRelation;
import com.scnjwh.intellectreport.modules.func.entity.FunctionDataRelationDetail;
import com.scnjwh.intellectreport.modules.func.service.FunctionDataRelationDetailService;
import com.scnjwh.intellectreport.modules.func.service.FunctionDataRelationService;
import com.scnjwh.intellectreport.modules.gen.dao.GenTableColumnDao;
import com.scnjwh.intellectreport.modules.gen.service.GenTableService;
import com.scnjwh.intellectreport.modules.rule.dao.RuleDefinitionDao;
import com.scnjwh.intellectreport.modules.rule.entity.RuleCondition;
import com.scnjwh.intellectreport.modules.rule.entity.RuleDefinition;
import com.scnjwh.intellectreport.modules.sys.dao.MenuDao;
import com.scnjwh.intellectreport.modules.sys.entity.Menu;
import com.scnjwh.intellectreport.modules.sys.utils.UserUtils;

/**
 * 规则定义Service
 * @author 彭俊豪
 * @version 2017-07-10
 */
@Service
@Transactional(readOnly = true)
public class RuleDefinitionService extends CrudService<RuleDefinitionDao, RuleDefinition> {

    @Autowired
    private RuleTypeService ruleTypeService;

    @Autowired
    private RuleConditionService ruleConditionService;

    @Autowired
    private GenTableColumnDao genTableColumnDao;

    @Autowired
    private GenTableService genTableService;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private FunctionDataRelationDetailService functionDataRelationDetailService;

    @Autowired
    private FunctionDataRelationService functionDataRelationService;

    public RuleDefinition get(String id) {
        return super.get(id);
    }

    public List<RuleDefinition> findList(RuleDefinition ruleDefinition) {
        return super.findList(ruleDefinition);
    }

    public Page<RuleDefinition> findPage(Page<RuleDefinition> page, RuleDefinition ruleDefinition) {
        return super.findPage(page, ruleDefinition);
    }

    @Transactional(readOnly = false)
    public String saveOfCheck(RuleDefinition ruleDefinition) {
        if (StringUtils.isBlank(ruleDefinition.getId())) {
            setMenuName(ruleDefinition);
            ruleDefinition.setOffice(UserUtils.getUser().getOffice());
            super.save(ruleDefinition);
            addDetail(ruleDefinition);
            return "保存规则定义成功";
        }
        setMenuName(ruleDefinition);
        super.save(ruleDefinition);
        ruleConditionService.batchDeleteByDid(ruleDefinition.getId());
        addDetail(ruleDefinition);
        return "保存规则定义成功";
    }

    /**
     * 添加从表
     * @param ruleDefinition
     */
    private void addDetail(RuleDefinition ruleDefinition) {
        if (ruleDefinition.getRuleConditions().size() > 0) {
            for (RuleCondition ruleCondition : ruleDefinition.getRuleConditions()) {
                if (ruleCondition.getCondition() == null) {
                    continue;
                }
                ruleCondition.setOffice(ruleDefinition.getOffice());
                ruleCondition.setDId(ruleDefinition.getId());
                ruleConditionService.save(ruleCondition);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(RuleDefinition ruleDefinition) {
        super.delete(ruleDefinition);
    }

    @Transactional(readOnly = false)
    public String batchDeleteOfCheck(String[] ids) {
        for (String id : ids) {
            ruleConditionService.batchDeleteByDid(id);
        }
        super.batchDelete(ids);
        return "批量删除规则定义成功";
    }

    /**
     * 设置
     * @param ruleDefinition
     */
    public void setSome(RuleDefinition ruleDefinition) {
        if (StringUtils.isNotBlank(ruleDefinition.getTypeId())) {
            ruleDefinition.setTypeName(ruleTypeService.get(ruleDefinition.getTypeId()).getName());
        }
        if (StringUtils.isBlank(ruleDefinition.getId())) {
            return;
        }
        setMenuName(ruleDefinition);
        RuleCondition ruleCondition = new RuleCondition();
        ruleCondition.setDId(ruleDefinition.getId());
        List<RuleCondition> conditions = ruleConditionService.findList(ruleCondition);
        for (RuleCondition ruleCondition2 : conditions) {
            ruleCondition2.setTableColumnName(genTableColumnDao.getCommentsByTableName(
                ruleCondition2.getTableId(), ruleCondition2.getTableColumn()));
            ruleCondition2
                .setTableName(genTableService.getTableComment(ruleCondition2.getTableId()));
        }
        ruleDefinition.setRuleConditions(conditions);
    }

    /**
     * 设置菜单名称
     * @param ruleDefinition
     */
    private void setMenuName(RuleDefinition ruleDefinition) {
        //添加
        Menu menu = menuDao.get(ruleDefinition.getFunctionId());
        if (menu != null) {
            ruleDefinition.setFunctionName(menu.getName());
        }
    }

    /**
     * 获取 left join sql
     * @param functionId 功能id
     * @return
     */
    public List<String> getLeftSql(String functionId) {
        if (StringUtils.isBlank(functionId)) {
            return null;
        }
        //获取功能与表对应
        FunctionDataRelation functionDataRelation = new FunctionDataRelation();
        functionDataRelation.setFunctionId(functionId);
        List<FunctionDataRelation> dataRelations = functionDataRelationService
            .findList(functionDataRelation);
        if (dataRelations.size() <= 0) {
            return null;
        }
        //功能与表对应只有1个
        functionDataRelation = dataRelations.get(0);
        //通过对应表获取从表数据
        FunctionDataRelationDetail functionDataRelationDetail = new FunctionDataRelationDetail();
        functionDataRelationDetail.setFId(functionDataRelation.getId());
        List<FunctionDataRelationDetail> functionDataRelationDetails = functionDataRelationDetailService
            .findList(functionDataRelationDetail);
        if (functionDataRelationDetails.size() <= 0) {
            //没从表就不需要left join
            return null;
        }
        List<String> sqls = new ArrayList<String>();
        for (FunctionDataRelationDetail detail : functionDataRelationDetails) {
            String sql = "LEFT JOIN " + detail.getRetinueTableId() + " ON "
                         + detail.getRetinueTableId() + "." + detail.getRetinueColumn() + " = "
                         + functionDataRelation.getTableId() + "." + detail.getColumnId();
            sqls.add(sql);
        }
        return sqls;
    }

    /**
     * 获取where sql
     * @param functionId 功能id
     * @param ruleDefinitions 该用户拥有的全部规则
     * @return
     */
    public List<String> getWhereSql(String functionId, List<RuleDefinition> ruleDefinitions) {
        if (ruleDefinitions.size() <= 0) {
            return null;
        }
        if (StringUtils.isBlank(functionId)) {
            return null;
        }
        for (int i = 0; i < ruleDefinitions.size(); i++) {
            RuleDefinition ruleDefinition = ruleDefinitions.get(i);
            if (!functionId.equals(ruleDefinition.getFunctionId())) {
                //删除掉不是该功能的规则
                ruleDefinitions.remove(i);
            }
        }
        if (ruleDefinitions.size() <= 0) {
            return null;
        }
        //获取从表 拼接语句
        List<String> sqls = new ArrayList<String>();
        for (RuleDefinition ruleDefinition : ruleDefinitions) {
            RuleCondition ruleCondition = new RuleCondition();
            ruleCondition.setDId(ruleDefinition.getId());
            List<RuleCondition> conditions = ruleConditionService.findList(ruleCondition);
            if (conditions.size() <= 0) {
                return null;
            }
            for (RuleCondition condition : conditions) {

                String sql = new String();
                sql = "AND " + condition.getTableId() + "." + condition.getTableColumn();
                if (condition.getCondition() == 1) {
                    //排除
                    if (StringUtils.isNotBlank(condition.getDictName())) {
                        //有字典值
                        String value = condition.getContent().substring(0,
                            condition.getContent().length() - 1);
                        sql += " NOT IN (" + value + ")";
                        sqls.add(sql);
                        continue;
                    }
                    //获取反符号
                    String fh = getFFh(condition.getFiltration());
                    //无字典值
                    sql += " " + fh + " " + condition.getContent();
                    sqls.add(sql);
                    continue;
                } else {
                    //包含
                    if (StringUtils.isNotBlank(condition.getDictName())) {
                        //有字典值
                        String value = condition.getContent().substring(0,
                            condition.getContent().length() - 1);
                        sql += " IN (" + value + ")";
                        sqls.add(sql);
                        continue;
                    }
                    //获取正符号
                    String fh = getZFh(condition.getFiltration());
                    //无字典值
                    sql += " " + fh + " " + condition.getContent();
                    sqls.add(sql);
                    continue;
                }

            }

        }
        return sqls;
    }

    /**
     * 获取 where 和 left sql
     * @param functionId 功能id
     * @param ruleDefinitions 该用户的所有规则
     * @return left:left sql where:where sql
     */
    public Map<String, List<String>> getLeftAndWhereSql(String functionId,
                                                        List<RuleDefinition> ruleDefinitions) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        map.put("left", getLeftSql(functionId));
        map.put("where", getWhereSql(functionId, ruleDefinitions));
        return map;
    }

    /**
     * 获取 返回id 的sql
     * @param functionId 功能id
     * @param ruleDefinitions 该用户的所有规则
     * @return
     */
    public String getSql(String functionId, List<RuleDefinition> ruleDefinitions) {
        String sql = new String();
        //获取功能与表对应
        FunctionDataRelation functionDataRelation = new FunctionDataRelation();
        functionDataRelation.setFunctionId(functionId);
        List<FunctionDataRelation> dataRelations = functionDataRelationService
            .findList(functionDataRelation);
        if (dataRelations.size() <= 0) {
            return null;
        }
        //功能与表对应只有1个
        functionDataRelation = dataRelations.get(0);
        String ztable = functionDataRelation.getTableId();
        sql = "SELECT " + ztable + ".ID FROM " + ztable;
        List<String> lefts = getLeftSql(functionId);
        if (lefts.size() > 0) {
            //有left
            for (String left : lefts) {
                sql += " " + left;
            }
        }
        //where
        sql += " WHERE 1=1";
        List<String> wheres = getWhereSql(functionId, ruleDefinitions);
        if (wheres.size() > 0) {
            //有wheres
            for (String where : wheres) {
                sql += " " + where;
            }
        }
        return sql;
    }

    /**
     * 获取符号
     * @param filtration
     * @return
     */
    private String getZFh(Integer filtration) {
        if (filtration == null) {
            return null;
        }
        //0 大于  1小于 2等于  3不等于  4无值
        String fh = "";
        switch (filtration) {
            case 1:
                fh = ">";
                break;
            case 2:
                fh = "<";
                break;
            case 3:
                fh = "=";
                break;
            case 4:
                fh = "!=";
                break;
            case 5:
                fh = "is null";
                break;
            default:
                break;
        }
        return fh;
    }

    /**
     * 获取符号
     * @param filtration
     * @return
     */
    private String getFFh(Integer filtration) {
        if (filtration == null) {
            return null;
        }
        //0 大于  1小于 2等于  3不等于  4无值
        String fh = "";
        switch (filtration) {
            case 1:
                fh = "<";
                break;
            case 2:
                fh = ">";
                break;
            case 3:
                fh = "!=";
                break;
            case 4:
                fh = "==";
                break;
            case 5:
                fh = "is not null";
                break;
            default:
                break;
        }
        return fh;
    }

    public void test() {
        List<RuleDefinition> ruleDefinitions = new ArrayList<RuleDefinition>();
        RuleDefinition ruleDefinition = get("092218efae0e4d3ca76e4fb7bc3c8346");
        ruleDefinitions.add(ruleDefinition);
        @SuppressWarnings("unused")
        List<String> strings = getLeftSql("ae9fe32147cf49b0a0ad423aaf2993c1");
        @SuppressWarnings("unused")
        List<String> strigs = getWhereSql("ae9fe32147cf49b0a0ad423aaf2993c1", ruleDefinitions);
        @SuppressWarnings("unused")
        String sql = getSql("ae9fe32147cf49b0a0ad423aaf2993c1", ruleDefinitions);
        System.out.println(1);
    }

    /**
     * 检查detail是否完善
     * @param ruleConditions
     * @return
     */
    public boolean checkDetails(List<RuleCondition> ruleConditions) {
        if (ruleConditions.size() <= 0) {
            return false;
        }
        for (int i = 0; i < ruleConditions.size(); i++) {
            RuleCondition ruleCondition = ruleConditions.get(i);
            if (ruleCondition.getCondition() == null) {
                ruleConditions.remove(i);
                continue;
            }
            if (StringUtils.isBlank(ruleCondition.getTableId())) {
                return false;
            }
            if (StringUtils.isBlank(ruleCondition.getTableColumn())) {
                return false;
            }
            if (StringUtils.isBlank(ruleCondition.getContent())) {
                return false;
            }
            if (StringUtils.isBlank(ruleCondition.getDictName())) {
                if (ruleCondition.getFiltration() == null) {
                    return false;
                }
            }
            if (StringUtils.isNotBlank(ruleCondition.getDictName())) {
                if (ruleCondition.getFiltration() != null) {
                    return false;
                }
            }
        }
        return true;
    }
}