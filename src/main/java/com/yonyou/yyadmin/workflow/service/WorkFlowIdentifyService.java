package com.yonyou.yyadmin.workflow.service;

import com.yonyou.yyadmin.system.entity.Role;
import com.yonyou.yyadmin.system.entity.User;
import com.yonyou.yyadmin.system.entity.UserRole;
import com.yonyou.yyadmin.system.service.RoleService;
import com.yonyou.yyadmin.system.service.UserRoleService;
import com.yonyou.yyadmin.system.service.UserService;
import com.yonyou.yyadmin.workflow.mapper.WorkFlowIdentifyMapper;
import org.activiti.engine.IdentityService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 流程授权服务类
 */
@Service
public class WorkFlowIdentifyService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected IdentityService identityService;

    @Autowired
    protected WorkFlowIdentifyMapper workFlowIdentifyMapper;

    @Autowired
    protected RoleService roleService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserRoleService userRoleService;

    public void synAllUserAndRoleToActiviti() throws Exception {
        // 清空工作流用户、角色以及关系
        deleteAllActivitiIdentifyData();

        // 复制角色数据
        synRoleToActiviti();

        // 复制用户以及关系数据
        synUserWithRoleToActiviti();

    }

    public void deleteAllActivitiIdentifyData() throws Exception {
        this.workFlowIdentifyMapper.deleteAllMemerShip();
        this.workFlowIdentifyMapper.deleteAllRole();
        this.workFlowIdentifyMapper.deleteAllUser();
    }

    /**
     * 同步所有角色数据到
     *
     * @throws Exception
     */
    private void synRoleToActiviti() throws Exception {
        List<Role> roles = this.roleService.selectList(null);
        for (Role role : roles) {
            String roleID = role.getId().toString();
            org.activiti.engine.identity.Group identity_group = identityService.newGroup(roleID);
            identity_group.setName(role.getRoleName());
            identityService.saveGroup(identity_group);
        }
    }

    /**
     * 复制用户以及关系数据
     *
     * @throws Exception
     */
    private void synUserWithRoleToActiviti() throws Exception {
        List<User> allUser = this.userService.selectList(null);
        List<UserRole> userRoles = this.userRoleService.selectList(null);
        HashMap<String, UserRole> userID2userRole = new HashMap<>();
        for (UserRole userRole : userRoles) {
            userID2userRole.put(userRole.getUserId(), userRole);
        }
        for (User user : allUser) {
            String userId = user.getId().toString();
            String roleID = userID2userRole.get(user.getId()).getId().toString();
            if (roleID == null || roleID.equals("")) {
                continue;
            }
            // 添加一个用户到Activiti
            saveActivitiUser(user);
            // 角色和用户的关系
            addMembershipToIdentify(userId, roleID);
        }
    }

    /**
     * 添加Activiti Identify的用户于组关系
     *
     * @param groupId 角色ID集合
     * @param userId  用户ID
     */
    private void addMembershipToIdentify(String userId, String groupId) {

        identityService.createMembership(userId, groupId);
    }

    /**
     * 添加一个用户到Activiti {@link org.activiti.engine.identity.User}
     *
     * @param user 用户对象, {@link User}
     */
    private void saveActivitiUser(User user) {
        String userId = user.getId().toString();
        org.activiti.engine.identity.User activitiUser = identityService.newUser(userId);
        cloneAndSaveActivitiUser(user, activitiUser);
        logger.info("add activiti user: {}" + ToStringBuilder.reflectionToString(activitiUser));
    }

    /**
     * 使用系统用户对象属性设置到Activiti User对象中
     *
     * @param user         系统用户对象
     * @param activitiUser Activiti User
     */
    private void cloneAndSaveActivitiUser(User user, org.activiti.engine.identity.User activitiUser) {
        activitiUser.setFirstName(user.getUserName());
        activitiUser.setLastName(StringUtils.EMPTY);
        activitiUser.setPassword(StringUtils.EMPTY);
        activitiUser.setEmail(StringUtils.EMPTY);
        identityService.saveUser(activitiUser);
    }
}
