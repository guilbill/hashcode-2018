package hashcode.domain;

import lombok.Data;

@Data
public class Vehicle {

    public Vehicle(int id) {
        this.id = id;
    }

    int id;

    int currentRow, currentColumn = 0;

    public int hashCode() {
        return id;
    }



}
