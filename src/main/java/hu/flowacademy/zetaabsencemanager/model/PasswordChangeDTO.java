package hu.flowacademy.zetaabsencemanager.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PasswordChangeDTO {

    private String firstPassword;
    private String secondPassword;


    public PasswordChangeDTO(String firstPassword, String secondPassword, String dataC) {
        this.firstPassword = firstPassword;
        this.secondPassword = secondPassword;
    }

    public PasswordChangeDTO(String firstPassword, String secondPassword) {
        this.firstPassword = firstPassword;
        this.secondPassword = secondPassword;
    }



    public PasswordChangeDTO() {
        this.firstPassword = null;
        this.secondPassword = null;
    }

}
