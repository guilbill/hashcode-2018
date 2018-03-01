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
}
