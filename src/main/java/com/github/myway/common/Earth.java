package com.github.myway.common;

public class Earth {

	public static final double DTR = Math.PI / 180.0d;
	public static final double RADIUS_IN_METERS = 6378137d;

	public static double distance(double latitude1, double longitude1, double latitude2, double longitude2) {
		double lat1 = latitude1 * Earth.DTR;
		double lat2 = latitude2 * Earth.DTR;
		double lon1 = longitude1 * Earth.DTR;
		double lon2 = longitude2 * Earth.DTR;
		return Earth.RADIUS_IN_METERS
				* Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1));
	}
}
