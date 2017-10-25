package beans;

import java.io.Serializable;

public class Statistics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5003595938443692161L;
	private int numFotoPublicate;

	public Statistics() {
		super();
	}

	public int getNumFotoPublicate() {
		return numFotoPublicate;
	}

	public void setNumFotoPublicate(int numFotoPublicate) {
		this.numFotoPublicate = numFotoPublicate;
	}
	
}
