package com.bubbling.frame.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bubbling.frame.base.bean.ResponseBean;
import com.bubbling.frame.base.tools.SessionUtils;
import com.bubbling.frame.mapper.*;
import com.mysql.cj.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubbling.frame.base.service.impl.BaseService;
import com.bubbling.frame.base.tools.BaseUtils;
import com.bubbling.frame.entity.TAcFunc;
import com.bubbling.frame.entity.TAcOrg;
import com.bubbling.frame.entity.TAcRole;
import com.bubbling.frame.entity.TAcRoleFunc;
import com.bubbling.frame.entity.TAcRoleOrg;
import com.bubbling.frame.entity.TAcUser;
import com.bubbling.frame.entity.TAcUserOrg;
import com.bubbling.frame.entity.TAcUserRole;
import com.bubbling.frame.service.IFrameService;

@Service
public class FrameService extends BaseService implements IFrameService {
    @Autowired
    private TAcFuncMapper tAcFuncMapper;
    @Autowired
    private TAcOrgMapper tAcOrgMapper;
    @Autowired
    private TAcUserMapper tAcUserMapper;
    @Autowired
    private TAcRoleMapper tAcRoleMapper;
    @Autowired
    private TAcUserOrgMapper tAcUserOrgMapper;
    @Autowired
    private TAcRoleOrgMapper tAcRoleOrgMapper;
    @Autowired
    private TAcRoleFuncMapper tAcRoleFuncMapper;
    @Autowired
    private TAcUserRoleMapper tAcUserRoleMapper;

    @Override
    public ResponseBean getFuncList(Map<String, Object> map) throws Exception {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("is_valid", 1);
        List<TAcFunc> list = tAcFuncMapper.selectList(wrapper);
        return ResponseBean.success(list);
    }

    @Override
    public ResponseBean saveOrUpdateFunc(Map<String, Object> map) throws Exception {
        TAcFunc func = new TAcFunc();
        BaseUtils.mapToObject(map, func);
        if (BaseUtils.isNull(func.getId())) {
            tAcFuncMapper.insert(func);
        } else {
            func.setId((String) map.get("id"));
            tAcFuncMapper.updateById(func);
        }
        return ResponseBean.success();
    }

    @Override
    public ResponseBean delFunc(Map<String, Object> map) throws Exception {
        TAcFunc tAcFunc = new TAcFunc();
        BaseUtils.mapToObject(map, tAcFunc);
        tAcFuncMapper.deleteById(tAcFunc.getId());
        return ResponseBean.success();
    }

    @Override
    public ResponseBean getOrgList(Map<String, Object> map) throws Exception {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("is_valid", 1);
        List<TAcOrg> list = tAcOrgMapper.selectList(wrapper);
        return ResponseBean.success(list);
    }

    @Override
    public ResponseBean saveOrUpdateOrg(Map<String, Object> map) throws Exception {
        TAcOrg tAcOrg = new TAcOrg();
        BaseUtils.mapToObject(map, tAcOrg);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("pid", tAcOrg.getPid());
        wrapper.eq("org_name", tAcOrg.getOrgName());
        List<TAcOrg> list = tAcOrgMapper.selectList(wrapper);
        if (BaseUtils.isNotNullList(list)) {
            return ResponseBean.error("相同父节点下同名！");
        }
        tAcOrgMapper.insertOrUpdate(tAcOrg);
        return ResponseBean.success(list);
    }

    @Override
    public ResponseBean delOrg(Map<String, Object> map) throws Exception {
        TAcOrg tAcOrg = new TAcOrg();
        BaseUtils.mapToObject(map, tAcOrg);
        tAcOrgMapper.deleteById(tAcOrg.getId());
        return ResponseBean.success();
    }

    @Override
    public ResponseBean getUserByOrg(Map<String, Object> map) throws Exception {
        List<TAcUser> list = tAcUserMapper.queryUserByOrgId((String) map.get("orgId"));
        return ResponseBean.success(list);
    }

    @Override
    public ResponseBean getRoleByOrg(Map<String, Object> map) throws Exception {
        List<TAcRole> list = tAcRoleMapper.queryRoleByOrgId((String) map.get("orgId"));
        return ResponseBean.success(list);
    }

    @Override
    public ResponseBean saveOrUpdateUser(Map<String, Object> map) throws Exception {
        TAcUser user = new TAcUser();
        String orgId = (String) map.get("orgId");
        BaseUtils.mapToObject((Map<String, Object>) map.get("user"), user);
        user.setPassword(BaseUtils.getMD5String("111111"));
        if (BaseUtils.isNull(user.getId())) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("login_name", user.getUserName());
            TAcUser userQuery = tAcUserMapper.queryUserNoVaild(user.getLoginName());
            if (BaseUtils.isNotNull(userQuery)) {
                if (userQuery.getIsValid() == 1) {
                    return ResponseBean.error("登录名已存在！");
                }
            }
        }
        tAcUserMapper.insertOrUpdate(user);
        if (BaseUtils.isNotNull(orgId)) {
            TAcUserOrg tAcUserOrg = new TAcUserOrg();
            tAcUserOrg.setUserId(user.getId());
            tAcUserOrg.setOrgId(orgId);
            tAcUserOrgMapper.insert(tAcUserOrg);
        }
        return ResponseBean.success();
    }

    @Override
    public ResponseBean delUser(Map<String, Object> map) throws Exception {
        tAcUserMapper.deleteById((String) map.get("id"));
        return ResponseBean.success();
    }

    @Override
    public ResponseBean saveOrUpdateRole(Map<String, Object> map) throws Exception {
        TAcRole role = new TAcRole();
        BaseUtils.mapToObject((Map<String, Object>) map.get("role"), role);
        String orgId=(String) map.get("orgId");
        tAcRoleMapper.insertOrUpdate(role);
        if(BaseUtils.isNotNull(orgId)){
            //新增
            TAcRoleOrg roleOrg = new TAcRoleOrg();
            roleOrg.setRoleId(role.getId());
            roleOrg.setOrgId((String) map.get("orgId"));
            tAcRoleOrgMapper.insert(roleOrg);
        }
        return ResponseBean.success();
    }
    @Override
    public ResponseBean delRole(Map<String, Object> map) throws Exception {
        String id= (String) map.get("id");
        tAcRoleMapper.deleteById(id);
        return ResponseBean.success();
    }
    @Override
    public ResponseBean queryFuncByRole(Map<String, Object> map) throws Exception {
        String roleId = (String) map.get("roleId");
        String orgId = (String) map.get("orgId");
        QueryWrapper queryWrapper = new QueryWrapper();
        List<TAcFunc> funcsMaps = tAcFuncMapper.selectList(queryWrapper);
        queryWrapper = new QueryWrapper();
        queryWrapper.select("id as id");
        queryWrapper.inSql("id", "select func_id from t_ac_role_func b where b.role_id='" + roleId + "' and b.is_valid=1");
        queryWrapper.eq("is_valid", 1);
        List<Map<String, Object>> funcSelectMaps = tAcFuncMapper.selectMaps(queryWrapper);
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("funcs", funcsMaps);
        retMap.put("funcSelect", funcSelectMaps);
        return ResponseBean.success(retMap);
    }
    @Override
    public ResponseBean saveRoleFunc(Map<String, Object> map) throws Exception {
        List<String> funcs = (List<String>) map.get("funcSelect");
        String roleId = (String) map.get("roleId");
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("role_id", roleId);
        tAcRoleFuncMapper.delete(updateWrapper);
        for (String funcId : funcs) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("role_id", roleId);
            queryWrapper.eq("func_id", funcId);
            TAcRoleFunc roleFunc = tAcRoleFuncMapper.queryNoValid(roleId,funcId);
            if (BaseUtils.isNotNull(roleFunc)) {
                if (roleFunc.getIsValid() != 1) {
                    tAcRoleFuncMapper.updateIsValid(roleFunc.getId(),new Date(), SessionUtils.getUserId());
                }
            } else {
                roleFunc = new TAcRoleFunc();
                roleFunc.setFuncId(funcId);
                roleFunc.setRoleId(roleId);
                tAcRoleFuncMapper.insert(roleFunc);
            }
        }
        return ResponseBean.success();
    }
    @Override
    public ResponseBean queryRoleByOrg(Map<String, Object> map) throws Exception {
        String userId = (String) map.get("userId");
        String orgId = (String) map.get("orgId");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("id as 'key'", "role_name as title ", "role_name as description");
        queryWrapper.inSql("id", "select role_id from t_ac_role_org b where b.org_id='" + orgId + "' and b.is_valid=1");
        queryWrapper.eq("is_valid", 1);
        List<Map<String, Object>> rolesMaps = tAcRoleMapper.selectMaps(queryWrapper);
        queryWrapper = new QueryWrapper();
        queryWrapper.select("id as id");
        queryWrapper.inSql("id", "select role_id from t_ac_user_role b where b.user_id='" + userId + "' and b.is_valid=1");
        queryWrapper.eq("is_valid", 1);
        List<Map<String, Object>> roleSelectMaps = tAcRoleMapper.selectMaps(queryWrapper);
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("roles", rolesMaps);
        retMap.put("roleSelect", roleSelectMaps);
        return ResponseBean.success(retMap);
    }

    @Override
    public ResponseBean saveUserRole(Map<String, Object> map) throws Exception {
        String userId = (String) map.get("userId");
        List<String> roleIds = (List<String>) map.get("roleSelect");
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_id", userId);
        tAcUserRoleMapper.delete(updateWrapper);
        for (String roleId : roleIds) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("role_id", roleId);
            queryWrapper.eq("user_Id", userId);
            TAcUserRole userRole = tAcUserRoleMapper.selectOne(queryWrapper);
            if (BaseUtils.isNotNull(userRole)) {
                if (userRole.getIsValid() != 1) {
                    userRole.setIsValid(1);
                    tAcUserRoleMapper.updateById(userRole);
                }
            } else {
                userRole = new TAcUserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                tAcUserRoleMapper.insert(userRole);
            }

        }
        return ResponseBean.success();
    }

    /**
     * 重置密码
     *
     * @param map userId
     * @return
     * @throws Exception
     */
    @Override
    public ResponseBean updatePassword(Map<String, Object> map) throws Exception {
        String userId = (String) map.get("userId");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", userId);
        TAcUser user = tAcUserMapper.selectOne(queryWrapper);
        if (BaseUtils.isNotNull(user)) {
            user.setPassword(BaseUtils.getMD5String("111111"));
            tAcUserMapper.updateById(user);
        }
        return ResponseBean.success();
    }

}
