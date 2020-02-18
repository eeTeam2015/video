package com.edu.zzti.foreign.service;

import com.edu.zzti.common.model.User;
import java.util.List;

/**
 * 类型service层接口
 */
public interface UserService {

    User add(User user);

    List<User> listIsUse();

    User load(String id);

    int saveAll(List<User> users);

    List<User> findByCondition(User user);

    Boolean update(User user);
}
