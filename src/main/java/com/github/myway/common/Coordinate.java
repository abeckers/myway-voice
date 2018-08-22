package com.github.myway.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {
	public static final double DTR = Math.PI / 180.0d;
	private static final double EARTH = 6378137d;
	private double latitude;
	private double longitude;


	/**
	 * Distance in meters.
	 */
	public double distance(Coordinate b) {
		double lat1 = latitude * DTR;
		double lat2 = b.getLatitude() * DTR;
		double lon1 = longitude * DTR;
		double lon2 = b.getLongitude() * DTR;
		return EARTH
				* Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1));
	}
}
