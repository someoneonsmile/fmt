package com.closer.ws.entity;

import com.closer.ws.common.CrudBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class SysUser extends CrudBaseEntity {
    private String userName;
    private String pwd;
}
