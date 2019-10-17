package hu.flowacademy.zetaabsencemanager.model;

import org.springframework.util.StringUtils;

public enum Roles {
    ADMIN("ADMIN"),
    LEADER("LEADER"),
    EMPLOYEE("EMPLOYEE");

    private String role;

    Roles(String role){
        this.role=role;
    }

}

