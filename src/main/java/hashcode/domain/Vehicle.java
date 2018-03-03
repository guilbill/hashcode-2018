package hashcode.domain;

import lombok.Data;

@Data
public class Vehicle {

    public Vehicle(int id) {
        this.id = id;
    }

    int id;

    int currentRow, currentColumn = 0;
    int step = 0;

    @Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
