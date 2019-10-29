package hu.flowacademy.zetaabsencemanager.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PasswordChangeDTO {

    private String firstPassword;
    private String secondPassword;
    private String oldPassword;


    public PasswordChangeDTO(String firstPassword, String secondPassword, String oldPassword) {
        this.firstPassword = firstPassword;
        this.secondPassword = secondPassword;
        this.oldPassword = oldPassword;
    }

    public PasswordChangeDTO() {
        this.firstPassword = null;
        this.secondPassword = null;
        this.oldPassword = null;
    }

}
