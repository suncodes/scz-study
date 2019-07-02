package basic_01.chapter_11.lambda;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Employee {
    private int id;
    private String name;
    private int age;
    private double salary;

    public Employee(int id) {
        this.id = id;
    }
}