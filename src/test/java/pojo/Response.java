package pojo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class Response {
    private int id;
    private String title;
    private boolean verified;
    private Addition addition;
    private List<Integer> importantNumbers;

    @Data
    public static class Addition {
        private int id;
        private String additionalInfo;
        private int additionalNumber;
    }
}

