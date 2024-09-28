package pojo;

import lombok.Data;
import java.util.Arrays;
import java.util.List;

@Data
public class User {
    private Addition addition;
    private List<Integer> important_numbers = Arrays.asList(42, 87, 15);
    private String title = "qwerty";
    private boolean verified = true;

    @Data
    public static class Addition {
        private String additional_info;
        private int additional_number;
    }
}

