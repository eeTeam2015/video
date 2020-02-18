package com.edu.zzti.foreign.service.impl;

import com.edu.zzti.common.dao.impl.UserMapper;
import com.edu.zzti.common.model.User;
import com.edu.zzti.foreign.service.UserService;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * 类型service层接口实现
 */
@Component
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User add(User user) {
        user.beforeInsert();
        return userMapper.insert(user) > 0 ? user : null;
    }

    @Override
    public List<User> listIsUse() {
        User user = new User();
        user.setIsUse(1);
        return userMapper.select(user);
    }

    @Override
    public User load(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int saveAll(List<User> users) {
        int count=0;
        for (int i = 0; i < users.size(); i++) {
            try {
                userMapper.insert(users.get(i));
                count++;
            }catch (Exception e){
                //不理会
            }
        }
        return count;
    }

    @Override
    public List<User> findByCondition(User user) {
        return userMapper.select(user);
    }

    @Override
    public Boolean update(User user) {
        return userMapper.updateByPrimaryKeySelective(user) > 0;
    }
}
