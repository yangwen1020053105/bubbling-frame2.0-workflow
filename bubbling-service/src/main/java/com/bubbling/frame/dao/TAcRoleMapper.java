package com.bubbling.frame.dao;

import com.bubbling.frame.base.dao.BaseMineMapper;
import com.bubbling.frame.entity.TAcRole;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TAcRoleMapper extends BaseMineMapper<TAcRole> {
    @Select("select b.* from t_ac_role_org a left join t_ac_role b on a.role_id=b.id where a.org_id=#{orgId} and a.IS_VALID=1 and b.IS_VALID=1")
    List<TAcRole> queryRoleByOrgId(String orgId);
    @Select("select a.* from t_ac_role_org a left join t_ac_role b on a.role_id=b.id where a.org_id=#{orgId} and b.role_name=#{roleName}")
    TAcRole queryRoleByOrgId(String orgId,String roleName);
}
