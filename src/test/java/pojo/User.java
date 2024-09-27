package pojo;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Data
public class User {
    private String title = "zagolovok";
    private boolean verified = true;
    private Addition addition;
    private List<Integer> importantNumbers = Arrays.asList(1, 2 ,3);

    @Data
    public static class Addition {
        private String additionalInfo = "asdf";
        private int additionalNumber = 123;
    }
}


