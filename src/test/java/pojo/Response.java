package pojo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Response {


    @Data
    class Root {
        private String id;
        private Addition addition;

        private int[] important_numbers;
        private String title;
        private boolean verified;
    }

    @Data
    class Addition {
        private String additional_info;
        private int additional_number;
    }
}
