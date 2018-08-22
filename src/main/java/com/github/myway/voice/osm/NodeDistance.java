package com.github.myway.voice.osm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NodeDistance {
	private Node node;
	private double distance;
}
