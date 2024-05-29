package web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import web.models.Admin;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class AdminDetails implements UserDetails {
    private Admin admin;

    public AdminDetails(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(admin.getRole()));
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }


    @Override
    public String getUsername() {
        return admin.getUsername();
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
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AdminDetails user = (AdminDetails) o;
        return Objects.equals(admin.getId_admin(), user.admin.getId_admin());
    }

    public Long getId() {
        return admin.getId_admin();
    }

    public String getEmail() {
        return admin.getEmail();
    }
}
