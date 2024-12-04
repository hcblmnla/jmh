package benchmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StudentTest {

    @Test
    void getInformationShouldReturnFullInformation() {
        // Given
        var anton = new Student(42, "Anton", "Pablo", Group.M3234);

        // When
        var id = anton.getInformation();

        // Then
        assertThat(id)
            .contains("Anton")
            .contains("Pablo")
            .contains("42");
    }

    @Test
    void compareToShouldSortStudentById() {
        // Given
        var anton = new Student(42, "Anton", "Pablo", Group.M3232);
        var oleg = new Student(1, "Oleg", "Messi", Group.M3239);
        var daniil = new Student(1520, "Daniil", "Serov", Group.M3234);

        // When
        var list = new ArrayList<>(List.of(anton, oleg, daniil));
        Collections.sort(list);

        // Then
        assertThat(list)
            .isEqualTo(List.of(oleg, anton, daniil));
    }
}
