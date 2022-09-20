package com.obeast.bendan.service.Impl;

import com.obeast.bendan.dao.UserRepository;
import com.obeast.bendan.po.User;
import com.obeast.bendan.service.UserService;
import com.obeast.bendan.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    @Override
    public User saveUser(User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userRepository.save(user);
    }

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.encode(password));
        return user;
    }

    @Override
    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }


}
