package hu.flowacademy.zetaabsencemanager.model;

public enum Roles {
    ADMIN("ADMIN"),
    LEADER("LEADER"),
    EMPLOYEE("EMPLOYEE"),
    INACTIVE("INACTIVE");

    private String role;

    Roles(String role){
        this.role=role;
    }

}

