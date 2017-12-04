package com.closer.ws.common;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CrudBaseEntity<T extends Model> extends Model<T> {

    private Long id;
    private Long sId;
    private Integer logicDel;
    private Long createUser;
    private Date createTime;
    private Long updateUser;
    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
