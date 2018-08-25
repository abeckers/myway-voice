package com.github.myway.voice.navigate;

import com.github.myway.core.Coordinate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Position extends Coordinate {
	private double deltaLatitude;
	private double deltaLongitude;
	private double deltaTime;
}
