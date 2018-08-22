package com.github.myway.voice.osm;

import java.util.List;
import java.util.Map;

import com.github.myway.common.BoundingBox;

import lombok.Data;

@Data
public class MapData {
	private BoundingBox bounds;
	private List<Node> nodes;
	private List<Way> ways;
	private List<Relation> relations;
	
	private Map<String, Tagged> objects;

	public NodeDistance findNode(double latitude, double longitude, double maxDist) {
		double minDistance = maxDist;
		Node closestNode = null;
		for (Node node : nodes) {
			double distance = node.distance(latitude, longitude);
			if (distance < minDistance) {
				minDistance = distance;
				closestNode = node;
			}
		}
		return closestNode == null ? null : new NodeDistance(closestNode, minDistance);
	}
}
