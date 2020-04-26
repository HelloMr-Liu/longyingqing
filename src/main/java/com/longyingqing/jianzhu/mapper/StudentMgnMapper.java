package com.longyingqing.jianzhu.mapper;

import com.longyingqing.jianzhu.entity.StudentMgn;

public interface StudentMgnMapper {
    int deleteByPrimaryKey(String studentcode);

    int insert(StudentMgn record);

    int insertSelective(StudentMgn record);

    StudentMgn selectByPrimaryKey(String studentcode);

    int updateByPrimaryKeySelective(StudentMgn record);

    int updateByPrimaryKey(StudentMgn record);
}