package com.practice.practicemanage.security;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.repository.RoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;


    public CustomUserDetails(User user, RoleRepository roleRepository) {
        this.username = user.getUserName();
        this.password = user.getPassWord();
//        this.roleRepository = roleRepository;  // 保存传入的 RoleRepository
        this.authorities = new HashSet<>();

        // 根据 userId 查找对应的角色标识（identity）
        String roleIdentify = getRoleIdentifyByUserId(roleRepository, Integer.valueOf(user.getStatus()));

        // 将 roleIdentify 转换为 GrantedAuthority
        if (roleIdentify != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleIdentify));  // 添加角色前缀
        }
    }

    private String getRoleIdentifyByUserId(RoleRepository roleRepository, Integer userId) {
        // 调用 RoleRepository 查找角色标识
        return roleRepository.findByUserid(userId).getIdentity();  // 根据 userId 查找角色标识
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
