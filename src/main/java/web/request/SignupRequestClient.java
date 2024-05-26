package web.request;

import jakarta.validation.constraints.*;

public class SignupRequestClient {
    @NotBlank
    @Size(min = 3, max = 40)
    private String nom;
    @NotBlank
    @Size(min = 3, max = 40)
    private String prenom;
    @NotBlank
    @Size(min = 3, max = 40)
    private String numTel;

   // @Size(min = 3, max = 50)
    //private String username;

    @NotBlank
    @Size(max = 80)
    @Email
    private String email;

    public String getTypecompte() {
        return typecompte;
    }

    public void setTypecompte(String typecompte) {
        this.typecompte = typecompte;
    }

    @NotBlank
    private String typecompte;




    @NotBlank
    @Size(min = 2, max = 80)
    private String password;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

   /* public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

