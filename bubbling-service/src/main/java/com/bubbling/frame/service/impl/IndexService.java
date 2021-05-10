package com.bubbling.frame.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bubbling.frame.base.bean.ResponseBean;
import com.bubbling.frame.entity.TAcFunc;
import com.bubbling.frame.mapper.TAcFuncMapper;
import com.bubbling.frame.mapper.TAcUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubbling.frame.base.service.impl.BaseService;
import com.bubbling.frame.base.tools.BaseUtils;
import com.bubbling.frame.base.tools.SessionUtils;
import com.bubbling.frame.entity.TAcUser;
import com.bubbling.frame.service.IIndexService;
@Service
public class IndexService extends BaseService implements IIndexService {
	@Autowired
	private TAcFuncMapper tAcFuncMapper;
	@Autowired
	private TAcUserMapper tAcUserMapper;
	@Override
	public List<TAcFunc> queryFuncTree() throws Exception {
		Map<String, Object> reMap=new HashMap<String, Object>();
		if (SessionUtils.getUserId().equals("001")) {
			QueryWrapper queryWrapper=new QueryWrapper<>();
			return tAcFuncMapper.selectList(queryWrapper);
		}else {
			QueryWrapper queryWrapper=new QueryWrapper<>();
			queryWrapper.exists("select 1 from t_ac_user_role b left join t_ac_role_func c on b.role_id=c.role_id where c.func_id=t_ac_func.id and b.user_id='"+SessionUtils.getUserId()+"' and b.is_valid=1 and c.is_valid=1");
			return tAcFuncMapper.selectList(queryWrapper);
		}
	}
	@Override
	public ResponseBean updatePassword(Map<String, Object> map) throws Exception {
		String password=(String) map.get("pass");
		QueryWrapper queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("id",SessionUtils.getUserId());
		queryWrapper.eq("is_valid",1);
		TAcUser user= tAcUserMapper.selectOne(queryWrapper);
		if (BaseUtils.isNotNull(user)) {
			user.setPassword(BaseUtils.getMD5String(password));
			tAcUserMapper.updateById(user);
			return ResponseBean.success();
		}else {
			return ResponseBean.error("未找到用户！");
		}
		
	}
	
}
