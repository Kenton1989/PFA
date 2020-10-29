package sg.edu.ntu.gg4u.pfa.persistence.Record;

import java.time.LocalDateTime;

public class SumByCategory {
    public String categoryName;
    public Double sum;


    public SumByCategory() {
        categoryName = "dummy";
        sum = 10.0;
    }

    public SumByCategory(String name, double value) {
        categoryName = name;
        sum = value;
    }
}
