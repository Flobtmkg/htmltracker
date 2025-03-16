package flo.web.htmltracker.exceptions;

public class TrackerNotFoundException extends Exception {

	private static final long serialVersionUID = -9196569857653170209L;
	
	private static final String ERR_TRACKER_NOT_FOUND = "There is no tracker with the given ID.";
	
	public TrackerNotFoundException() {
		super(ERR_TRACKER_NOT_FOUND);
		
	}

}
