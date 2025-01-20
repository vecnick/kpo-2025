package studying;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
public class PedalEngine implements IEngine{
    private final int size;

    @Override
    public boolean isCompatible(Customer customer) {
        return customer.legPower > 5;
    }

    public PedalEngine(int size) {
        this.size = size;
    }
}
