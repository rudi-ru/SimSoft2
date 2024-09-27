package pojo;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Builder
public class User {

    @Builder.Default
    private HashMap<String, String> addition = new HashMap<>();

    @Builder.Default
    private int[] important_numbers = {42, 87, 15};
    @Builder.Default
    private String title = "Заголовок сучности";
    @Builder.Default
    private boolean verified = true;
}

@Builder
class Addition {
    @Builder.Default
    private String additional_info = "Доп сведения";
    @Builder.Default
    private int additional_number = 123;
}


