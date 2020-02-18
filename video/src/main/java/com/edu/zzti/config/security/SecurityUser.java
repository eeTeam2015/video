package com.edu.zzti.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 安全信息user
 */
@Data
public class SecurityUser implements UserDetails {

    /**
     * 主键id
     */
    private String id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 所属组织id
     */
    private String orgId;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 账户
     */
    private String account;

    private List<SimpleGrantedAuthority> authorities;

    public SecurityUser(String id,
                        String name,
                        String orgId,
                        String password,
                        String account,
                        List<SimpleGrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.orgId = orgId;
        this.account = account;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
