package utility;

public enum IntakePosition {
	DOWN(4713), SWITCH(2053), SCALE(0);
	
	private double pos;
	
	IntakePosition(double pos) {
		this.pos = pos;
	}
	
	public double pos() {
		return pos;
	}
}
