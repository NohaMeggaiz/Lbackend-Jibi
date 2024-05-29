package web.security;


import web.models.Agent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

public class AgentDetails implements UserDetails{

    private Agent agent;

    public AgentDetails(Agent userApp) {
        super();
        this.agent = userApp;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(agent.getRole()));
    }

    @Override
    public String getPassword() {
        return agent.getPassword();
    }

    @Override
    public String getUsername() {
        return agent.getUsername();
    }
    public String getIdType() {
        return agent.getPieceIdentite();
    }

    public String getAdress() {
        return agent.getAdresse();
    }
    public String getPhone() {
        return agent.getNumTel();
    }
    public String getNumMatricule() {
        return agent.getNumMatriculation();
    }
    public String getNumId() {
        return agent.getNumPieceIdentite();
    }
    public String getNumPatente() {
        return agent.getNumPattente();
    }
    public String getNom() {
        return agent.getNom();
    }
    public String getPrenom() {
        return agent.getPrenom();
    }
    public Date getDateNaissance() {
        return agent.getDateNaissance();
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
        AgentDetails user = (AgentDetails) o;
        return Objects.equals(agent.getId_user(), user.agent.getId_user());
    }
    public Long getId() {
        return agent.getId_user();
    }

    public String getEmail() {
        return agent.getEmail();
    }
}