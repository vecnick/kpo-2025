package HSEBank.Domains.Categories;

import HSEBank.Enums.OperationTypes;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private String name;
    private int id;
    private OperationTypes operationType;

    @JsonCreator
    public Category(@JsonProperty("id") int id, @JsonProperty("name") String name,
                    @JsonProperty("operationType") OperationTypes operationType) {
        this.name = name;
        this.id = id;
        this.operationType = operationType;
    }
}
