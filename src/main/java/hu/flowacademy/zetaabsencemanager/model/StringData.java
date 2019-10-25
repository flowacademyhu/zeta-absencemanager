package hu.flowacademy.zetaabsencemanager.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StringData {

    private String dataA;
    private String dataB;
    private String dataC;


    public StringData(String dataA, String dataB, String dataC) {
        this.dataA = dataA;
        this.dataB = dataB;
        this.dataC = dataC;
    }

    public StringData(String dataA, String dataB) {
        this.dataA = dataA;
        this.dataB = dataB;
    }



    public StringData() {
        this.dataA = null;
        this.dataB = null;
    }

}
