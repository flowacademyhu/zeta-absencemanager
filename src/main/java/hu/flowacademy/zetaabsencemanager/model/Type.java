package hu.flowacademy.zetaabsencemanager.model;

public enum Type {
    ABSENCE("ABSENCE"),
    NON_WORKING("NOT_WORKING"),
    CHILD_SICK_PAY("CHILD_SICK_PAY"),
    UNPAID_HOLIDAY("UNPAID_HOLIDAY");

    private String type;

    Type(String type){
        this.type=type;
    }
}