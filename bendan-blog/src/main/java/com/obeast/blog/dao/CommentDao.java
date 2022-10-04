package com.obeast.blog.dao;


import com.obeast.blog.entity.CommentEntity;
import com.obeast.oss.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author obeast-dragon
 * Date 2022-09-21 12:18:12
 * @version 1.0
 * Description: 
 */
@Mapper
public interface CommentDao extends BaseDao<CommentEntity> {

}