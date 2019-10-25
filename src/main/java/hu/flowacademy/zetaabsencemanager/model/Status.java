package hu.flowacademy.zetaabsencemanager.model;

public enum Status {
  OPEN("OPEN"),
  UNDER_REVIEW("UNDER_REVIEW"),
  APPROVED("APPROVED"),
  ADMINISTRATED("ADMINISTRATED"),
  DONE("DONE"),
  REJECTED("REJECTED");

  private String status;

  Status(String status) {
    this.status = status;
  }
}