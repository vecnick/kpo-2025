package hse.kpo.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerResponse {
    private String name;
    private int legPower;
    private int handPower;
    private int iq;
    private List<Integer> carVin;
    private Integer catamaranVin;
}
