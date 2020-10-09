package com.csmaxwell.tes.dao;

import com.csmaxwell.tes.domain.TesPermission;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TesPermissionMapper extends Mapper<TesPermission> {
    @Select("select * from tes_permission tp")
    List<TesPermission> getPermissionList(Long permissionId);

    @Select("select count(trp.id) FROM tes_role_permission trp WHERE trp.permission_id = #{id}")
    int deleteSelect(Long id);
}
