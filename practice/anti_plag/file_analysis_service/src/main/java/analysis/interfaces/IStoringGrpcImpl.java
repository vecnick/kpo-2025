package analysis.interfaces;

import java.util.List;
import java.util.Optional;

public interface IStoringGrpcImpl {

    Optional<List<Integer>> getAllIds();
    Optional<String> getHashById(int id);
    Optional<String> getFileTextById(int id);
}
