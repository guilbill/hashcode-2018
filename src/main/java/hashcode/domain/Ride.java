package hashcode.domain;

import lombok.Data;

@Data
public class Ride {

	private int index;
	private int rowStart;
	private int columnStart;
	private int rowEnd;
	private int columnEnd;
	private int earliestStart;
	private int latestFinish;
	private int distance;
	private boolean dejaPris;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ride other = (Ride) obj;
		if (index != other.index)
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		return result;
	}
	
}
