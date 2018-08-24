package com.github.myway.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {
	private double latitude;
	private double longitude;

	/**
	 * Distance in meters.
	 */
	public double distance(Coordinate b) {
		return Earth.distance(latitude, longitude, b.getLatitude(), b.getLongitude());
	}
}
