package com.example.todo_app.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/*UserDetails is an interface in Spring Security that represents the core user information used
for authentication and authorization.
Spring uses it as a standardized way to describe any user, no matter where they come from
        (a database, an LDAP server, an API, etc.).
 */

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String email;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                             String email) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    /*this method returns a collection (like a list or set) of objects that represent the roles or
    permissions the user has.*/
    /*It’s saying:
            “I will return a collection of objects that are either GrantedAuthority or any subclass of it.”*/
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
