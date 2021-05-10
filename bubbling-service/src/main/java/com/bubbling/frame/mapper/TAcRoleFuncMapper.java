package com.bubbling.frame.mapper;

import com.bubbling.frame.base.mapper.BaseMineMapper;
import com.bubbling.frame.entity.TAcRoleFunc;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface TAcRoleFuncMapper extends BaseMineMapper<TAcRoleFunc> {
    @Select("select * from  t_ac_role_func b  where b.role_id=#{roleId} and b.func_id=#{funcId}")
    TAcRoleFunc queryNoValid(String roleId, String funcId);
    @Update("update t_ac_role_func set is_valid=1,update_time=#{updateTime},update_user=#{updateUser} where id=#{id}")
    void updateIsValid(String id, Date updateTime, String updateUser);
}
