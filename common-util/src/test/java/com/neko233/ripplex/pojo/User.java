package com.neko233.ripplex.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SolarisNeko
 * @date 2022-02-22
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Comparable{

    Integer id;
    String name;
    Integer age;
    String job;
    Double salary;

    @Override
    public int compareTo(Object o) {
        if (o instanceof User) {
            User otherUser = (User) o;
            Double salary1 = this.getSalary();
            Double salary2 = otherUser.getSalary();
            if (salary1 == null || salary2 == null) {
                return 0;
            }
            return salary1.compareTo(salary2);
        }
        return 0;
    }
}
