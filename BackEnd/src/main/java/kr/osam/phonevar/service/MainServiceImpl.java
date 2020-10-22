package kr.osam.phonevar.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.osam.phonevar.domain.UserLog;
import kr.osam.phonevar.mapper.UserLogMapper;

@Service("mainService")
public class MainServiceImpl implements MainService {
    @Autowired
    private UserLogMapper userLogMapper;
    
    @Override
    public List<UserLog> getUsersLog() {
        
        return userLogMapper.getUsersLog();
    }
}
