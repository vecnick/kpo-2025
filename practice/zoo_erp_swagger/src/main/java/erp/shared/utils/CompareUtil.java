package erp.shared.utils;

import static org.assertj.core.api.Assertions.assertThat;

public class CompareUtil {
    public static boolean tryCompareRecursively(Object a, Object b) {
        try {
            // Проверяет на соответствие всех полей 1-ого и 2-ого объекта
            assertThat(a).usingRecursiveComparison().isEqualTo(b);
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }
}

