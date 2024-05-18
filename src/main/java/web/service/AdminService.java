package web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.models.Admin;
import web.models.Agent;
import web.repositories.AdminRepo;
import web.repositories.AgentRepo;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminService {

    @Autowired
    private final AdminRepo adminRepo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AgentRepo agentRepo;


    final String letterLower = "abcdefghijklmnopqrstuvwxyz";
    final String letterUpper= letterLower.toUpperCase();
    final String number = "0123456789";
    final String caractereSpeciaux = "!@#$%&*_?':,;~°^";
    final String passworwdCombinaison= letterLower+ letterUpper + number + caractereSpeciaux;
    public String genererPassword() {


        SecureRandom random = new SecureRandom();
        String password="";

        password+=letterLower.charAt(random.nextInt(letterLower.length()));
        password+=letterUpper.charAt(random.nextInt(letterUpper.length()));
        password+=number.charAt(random.nextInt(number.length()));
        password+=caractereSpeciaux.charAt(random.nextInt(caractereSpeciaux.length()));

        for(int i=0;i<5;i++) {
            password+=passworwdCombinaison.charAt(random.nextInt(passworwdCombinaison.length()));
        }

        return password;
    }

    //Generer le username
    public String genererUid(String email) {
        Long dateoftoday =  System.currentTimeMillis();
        String dateoftodayinms = dateoftoday.toString();

        SecureRandom random = new SecureRandom();
        String uid="";

        uid+=letterUpper.charAt(random.nextInt(letterUpper.length()));

        for(int i=0;i<3;i++) {
            uid+=number.charAt(random.nextInt(number.length()));
        }
        uid+=dateoftodayinms;

        log.info("UID of agent genarated: "+uid);
        return uid;
    }

    public Boolean createAgent(String nom, String prenom, String pieceIdentite,
                               String numPieceIdentite, Date dateNaissance, String adresse, String email,
                               String numTel, String numMatriculation,
                               String numPattente) throws IOException {

        Agent agent=new Agent();

        agent.setNom(nom);
        agent.setPrenom(prenom);
        agent.setPieceIdentite(pieceIdentite);
        agent.setNumPieceIdentite(numPieceIdentite);
        agent.setDateNaissance(dateNaissance);
        agent.setAdresse(adresse);
        agent.setEmail(email);
        agent.setNumTel(numTel);
        agent.setNumMatriculation(numMatriculation);
        agent.setNumPattente(numPattente);
        String uid = this.genererUid(email);
        agent.setUsername(uid);


        String pass=this.genererPassword();
        System.out.println("Password of agent "+pass);
        log.info("Password of agent "+pass);
        //we need to encode it before setting it to agent here
        // hnaya drt smiya okhra bax mayt overytax(overwritting 3la pass)
        String encodedPassword = passwordEncoder.encode(pass);
        agent.setPassword(encodedPassword);

        agentRepo.save(agent);


        return  true;
    }

    public List<Agent> getAgents() {
        log.info("Fetching all agents by admin");
        return  agentRepo.findAll();
    }

    public Agent getAgent(String uid) {
        log.info("Fetching agent by the admin {}", uid);
        return agentRepo.findByUsername(uid);
    }

    public List<Admin> getAdmins() {
        log.info("Fetching all Admins by admin");

        return adminRepo.findAll();
    }
    public Admin saveAdmin(Admin admin) {
        log.info("Saving new admin {} to the database", admin.getUsername());
        //also here need to encode it later
        // Encode the password before saving the admin
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);
        return adminRepo.save(admin);
    }
    public Admin checkAdminExists(String uid){
        Admin admin1 = adminRepo.findByUsername(uid);

        if (admin1 != null) return admin1;
        else return null;
    }



}
