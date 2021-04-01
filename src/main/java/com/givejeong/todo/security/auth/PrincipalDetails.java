package com.givejeong.todo.security.auth;

import com.givejeong.todo.domain.Account;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
@Data
public class PrincipalDetails implements UserDetails {
    private Account account;

    public PrincipalDetails(Account account){
        this.account = account;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

//        account.getRoleList().forEach(r->{
//            authorities.add(()-> {
//                return r;
//            });
//        });
        authorities.add(()->account.getRole());
        System.out.println("PrincipalDetails");
        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getAccountPw();
    }

    @Override
    public String getUsername() {
        return account.getAccountId();
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
