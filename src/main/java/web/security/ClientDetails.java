package web.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import web.models.Client;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class ClientDetails implements UserDetails {

    private Client client;

    public ClientDetails(Client client) {
        super();
        this.client = client;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(client.getRole()));
    }

    @Override
    public String getPassword() {
        return client.getPassword();
    }

    //login by num tel

    @Override
    public String getUsername() {
        return client.getNumTel();
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
        ClientDetails user = (ClientDetails) o;
        return Objects.equals(client.getId_client(), user.client.getId_client());
    }
    public Long getId() {
        return client.getId_client();
    }

    public String getEmail() {
        return client.getEmail();
    }
}