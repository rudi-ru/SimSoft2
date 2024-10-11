package pojo;

import lombok.Data;
import java.util.List;

@Data
public class Response {
    private String id;
    private Addition addition;
    private String title;
    private boolean verified;

    private List<Integer> importantNumbers;
    private String error;

    @Data
    public static class Addition {
        private String id;
        private String additionalInfo;
        private int additionalNumber;
    }
}

