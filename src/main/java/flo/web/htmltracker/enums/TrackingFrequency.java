package flo.web.htmltracker.enums;

public enum TrackingFrequency {
	MINUTELY("EVERY 5 MINUTES"), HOURLY("HOURLY"), DAILY("DAILY"), WEEKLY("WEEKLY"), MONTHLY("MONTHLY");
	
	private String frequencyValue;
	
	private TrackingFrequency(String frequency) {
		this.frequencyValue = frequency;
	}

	public String getFrequencyValue() {
		return frequencyValue;
	}	
	
	public static TrackingFrequency getFrequencyFromString(String frequency) throws IllegalArgumentException{
		
		
		if(MINUTELY.getFrequencyValue().equals(frequency)) {
			return TrackingFrequency.MINUTELY;
		} else if(HOURLY.getFrequencyValue().equals(frequency)) {
			return TrackingFrequency.HOURLY;
		} else if(DAILY.getFrequencyValue().equals(frequency)) {
			return TrackingFrequency.DAILY;
		} else if(WEEKLY.getFrequencyValue().equals(frequency)) {
			return TrackingFrequency.WEEKLY;
		} else if(MONTHLY.getFrequencyValue().equals(frequency)) {
				return TrackingFrequency.MONTHLY;
		} else {
			throw new IllegalArgumentException("The provided tracking frequency is invalid.");
		}
	}
	
}
