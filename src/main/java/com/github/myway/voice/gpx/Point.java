package com.github.myway.voice.gpx;

import com.github.myway.common.Coordinate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Point extends Coordinate {
	private double elevation;
}
