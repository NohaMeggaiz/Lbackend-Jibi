package web.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import web.models.Admin;
import web.models.Agent;
import web.models.FileAgentStorage;
import web.repositories.AdminRepo;
import web.repositories.AgentRepo;
import web.repositories.FileAgentStorageRepository;
import web.request.AgentProjection;

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

    @Autowired
    private final EmailService emailService;

    @Autowired
    private FileAgentStorageRepository fileAgentStorageRepository;


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
                               String numTel, String numMatriculation, String numPattente, MultipartFile file)
            throws IOException, MessagingException {

        Agent agent = new Agent();
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

        String pass = this.genererPassword();
        System.out.println("Password of agent " + pass);
        log.info("Password of agent " + pass);

        // Encode the password
        String encodedPassword = passwordEncoder.encode(pass);
        agent.setPassword(encodedPassword);

        // Save the file as a BLOB in the database
        if (file != null && !file.isEmpty()) {
            FileAgentStorage fileAgentStorage = new FileAgentStorage();
            fileAgentStorage.setFileName(file.getOriginalFilename());
            fileAgentStorage.setFileData(file.getBytes());
            //here li zdt
            fileAgentStorage.setAgentUsername(uid);
            //..
            fileAgentStorage = fileAgentStorageRepository.save(fileAgentStorage);

            agent.setIdDocument(fileAgentStorage);
        }

        agentRepo.save(agent);

        String content = "<h1> Hello Agent " + nom + " " + prenom + " and Welcome To JIBI application.</h1> </br>" +
                " <h3> please use these informations to log In to your Account: </h3>" +
                "<ul>" +
                "<li style='color:blue;'> User Name :  " + uid + " </li> " +
                "<li style='color:blue;'> Password : " + pass + " </li> " +
                "</ul>";
        emailService.sendEmail(email, "Welcome to JIBI", content);

        return true;
    }


    //liste dyal gae Les agents
  /*  public List<Agent> getAgents() {
        log.info("Fetching all agents by admin");
        List<Agent> users = agentRepo.findAll();
        return   users;
    }*/
    public List<AgentProjection> getAgents() {
        log.info("Fetching all agents by admin");
        return agentRepo.findAllAgentsProjection();
    }



    public Agent getAgent(String uid) {
        log.info("Fetching agent by the admin {}", uid);
        return agentRepo.findByUsername(uid);
    }

    public void deleteAgentByUid(String uid) {
        log.info("Deleting agent with UID {}", uid);
        Agent agent = agentRepo.findByUsername(uid);
        if (agent != null) {
            agentRepo.delete(agent);
            log.info("Agent with UID {} deleted successfully", uid);
        } else {
            log.warn("Agent with UID {} not found", uid);
        }
    }





    //hnaya les fonctions tae admin 3la les admins
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
    public Admin getAdmin(Long id) {
        log.info("Fetching admin with ID {}", id);
        return adminRepo.findById(id).orElse(null);
    }

    public boolean deleteAdminById(Long id) {
        log.info("Deleting admin with ID {}", id);
        if (adminRepo.existsById(id)) {
            adminRepo.deleteById(id);
            log.info("Admin with ID {} deleted successfully", id);
            return true;
        } else {
            log.warn("Admin with ID {} not found", id);
            return false;
        }
    }


    public void changePassword(String uid, String newPassword) {
        Admin agent = adminRepo.findByUsername(uid);
        String encodedPassword = passwordEncoder.encode(newPassword);
        // if(agent.getFirstAuth() == true){ agent.setFirstAuth(false);}
        agent.setPassword(encodedPassword);
        adminRepo.save(agent);
    }


}
