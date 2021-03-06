package com.csmaxwell.tes.service.impl;

import com.csmaxwell.tes.dao.TesRoleMapper;
import com.csmaxwell.tes.dao.TesRoleMenuMapper;
import com.csmaxwell.tes.dao.TesRolePermissionMapper;
import com.csmaxwell.tes.domain.*;
import com.csmaxwell.tes.service.TesRoleService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * TesRoleService实现类
 * Created by yl on 2020/9/16.
 */
@Service
public class TesRoleServiceImpl implements TesRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesRoleServiceImpl.class);

    @Autowired
    private TesRoleMapper tesRoleMapper;
    @Autowired
    private TesRolePermissionMapper tesRolePermissionMapper;
    @Autowired
    private TesRoleMenuMapper tesRoleMenuMapper;

    @Override
    public List<TesRole> selectAll() {
        List<TesRole> roleList = tesRoleMapper.selectAll();
        return roleList;
    }

    @Override
    public int delete(Long roleId) {
        int count=tesRoleMapper.deleteByPrimaryKey(roleId);
        return count;
    }

    @Override
    public int update(Long roleId, TesRole tesRoleDto) {
        tesRoleDto.setId(roleId);
        return tesRoleMapper.updateByPrimaryKeySelective(tesRoleDto);
    }

    @Override
    public int create(TesRole tesRoleParam) {
        int count=tesRoleMapper.insertSelective(tesRoleParam);
        return count;
    }

    @Override
    public List<TesMenu> getMenuList(Long id) {
        return tesRoleMapper.getMenuList(id);
    }

    @Override
    public TesRole findById(Long id) {
        TesRole tesRole = new TesRole();
        tesRole.setId(id);
        return tesRoleMapper.selectOne(tesRole);
    }

    @Override
    public int deleteRelation(Long roleId) {
        // 删除角色权限表中数据
        Example example1 = new Example(TesRolePermission.class);
        example1.createCriteria().andEqualTo("roleId", roleId);
        int count1 = tesRolePermissionMapper.deleteByExample(example1);

        // 删除角色菜单表中数据
        Example example2 = new Example(TesRoleMenu.class);
        example2.createCriteria().andEqualTo("roleId", roleId);
        int count2 = tesRoleMenuMapper.deleteByExample(example2);

        // 删除角色表中角色
        int count3 = tesRoleMapper.deleteByPrimaryKey(roleId);

        return count3;
    }


    @Override
    public List<TesRole> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(TesRole.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andLike("name", "%" + keyword + "%");
//            example.or(example.createCriteria().andLike("no", "%" + keyword + "%"));
        }
        List<TesRole> roleList = tesRoleMapper.selectByExample(example);
        return roleList;
    }

    @Override
    public int updateStatus(Long id, TesRole tesRole) {

        tesRole.setId(id);
        int count = tesRoleMapper.updateByPrimaryKeySelective(tesRole);
        return count;
    }

    @Override
    public List<TesRoleMenu> listRoleMenu(Long roleId) {

        Example example = new Example(TesRoleMenu.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        List<TesRoleMenu> tesRoleMenuses = tesRoleMenuMapper.selectByExample(example);
        return tesRoleMenuses;
    }

    @Override
    public int insertMenu(Long roleId, Long menuId) {
        TesRoleMenu tesRoleMenu = new TesRoleMenu();
        tesRoleMenu.setRoleId(roleId);
        tesRoleMenu.setMenuId(menuId);
        int count = tesRoleMenuMapper.insert(tesRoleMenu);
        return count;
    }

    @Override
    public int delRoleMenu(Long roleId) {
        Example example = new Example(TesRoleMenu.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        int count1 = tesRoleMenuMapper.deleteByExample(example);
        System.out.println(count1+"count1");
        return count1;
    }

    @Override
    public List<TesRolePermission> listRolePermission(Long roleId) {
        Example example = new Example(TesRolePermission.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        List<TesRolePermission> tesRolePermissions = tesRolePermissionMapper.selectByExample(example);
        return tesRolePermissions;
    }

    @Override
    public int delRolePermission(Long roleId) {
        Example example = new Example(TesRolePermission.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        int count1 = tesRolePermissionMapper.deleteByExample(example);
        System.out.println(count1+"count1");
        return count1;
    }

    @Override
    public int insertPermission(Long roleId, Long permissionId) {
        TesRolePermission tesRolePermission=new TesRolePermission();
        tesRolePermission.setRoleId(roleId);
        tesRolePermission.setPermissionId(permissionId);
        int count=tesRolePermissionMapper.insert(tesRolePermission);
        return count;
    }

}
