package benchmark;

/**
 * Basic student information.
 *
 * @param id        student's identifier
 * @param firstName student's first name
 * @param lastName  student's second name
 * @param group     student's group
 * @author alnmlbch
 */
public record Student(
    long id,
    String firstName,
    String lastName,
    Group group
) implements Comparable<Student> {

    @Override
    public int compareTo(final Student other) {
        return Long.compare(this.id, other.id);
    }

    /**
     * Full information about the student in the string.
     *
     * @return a string with information
     */
    public String getInformation() {
        return "%s (%d)".formatted(getName(), id);
    }

    private String getName() {
        return firstName + " " + lastName;
    }
}
