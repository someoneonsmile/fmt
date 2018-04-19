package com.closer.ws.dao;

import com.closer.ws.entity.SysUser;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserDao extends CrudRepository<SysUser, Long>, QueryDslPredicateExecutor<SysUser> {

}
