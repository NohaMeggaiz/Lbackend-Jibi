package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.models.Admin;
import web.models.Agent;
import web.models.Client;
import web.repositories.AdminRepo;
import web.repositories.AgentRepo;
import web.repositories.ClientRepository;
@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminRepo adminRepository;
    @Autowired
    private AgentRepo agentRepo;
    @Autowired
    private ClientRepository clientRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        Admin admin =  adminRepository.findByUsername(uid);
        Agent agent = agentRepo.findByUsername(uid);
        Client client = clientRepository.findByNumTel(uid);

        if(agent==null && admin==null && client==null) throw new UsernameNotFoundException(uid);
        if (agent!=null) return new AgentDetails(agent);
        if (admin!=null) return new AdminDetails(admin);
        if (client!=null) return new ClientDetails(client);

        return null;
    }
}