package com.bubbling.frame.dao;

import com.bubbling.frame.base.dao.BaseMineMapper;
import com.bubbling.frame.entity.TAcUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TAcUserMapper extends BaseMineMapper<TAcUser> {
    @Select("select b.* from t_ac_user_org a left join t_ac_user b on b.ID=a.USER_ID where a.org_id=#{orgId} and a.IS_VALID=1 and b.IS_VALID=1")
    List<TAcUser> queryUserByOrgId(String orgId);
    @Select("select * from  t_ac_user b  where b.login_name=#{loginName}")
    TAcUser queryUserNoVaild(String loginName);
}
