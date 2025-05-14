package analysis.interfaces;

import java.util.Optional;

public interface ITextsCmpService {

    Optional<Integer> calculatePlagiatePoints(int newFileId);
}
