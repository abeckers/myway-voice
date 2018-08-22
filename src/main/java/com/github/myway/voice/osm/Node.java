package com.github.myway.voice.osm;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Node extends Tagged {
	public static final double DTR = Math.PI / 180.0d;
	private static final double EARTH = 6378137d;
	private double longitude;
	private double latitude;
	
	private List<Way> ways;
	
	/**
	 * Distance in meters.
	 */
	public double distance(double otherLatitude, double otherLongitude) {
		double lat1 = latitude * DTR;
		double lat2 = otherLatitude * DTR;
		double lon1 = longitude * DTR;
		double lon2 = otherLongitude * DTR;
		return EARTH
				* Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1));
	}

}
