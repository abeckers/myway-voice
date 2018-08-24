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
	
	public Way findWay(double latitude, double longitude, double maxDist) {
		for (Way way : ways) {
			findOnWay(latitude, longitude, way);
		}
		return null;
	}

	private void findOnWay(double latitude, double longitude, Way way) {
		List<NodeRef> waynodes = way.getNodes();
		for (int i = 0; i < waynodes.size() - 1; i++) {
			NodeRef a = waynodes.get(i);
			NodeRef b = waynodes.get(i + 1);
			if (a.getNode() != null && b.getNode() != null) {
				isOnLeg(latitude, longitude, a, b);
			}
		}
	}

	private void isOnLeg(double latitude, double longitude, NodeRef a, NodeRef b) {
		if (a.getNode().getLatitude() == b.getNode().getLatitude()) {
			if (latitude == a.getNode().getLatitude()) {
				System.out.println("lat is equal");
			}
		}
		else if (a.getNode().getLongitude() == b.getNode().getLongitude()) {
			if (longitude == a.getNode().getLongitude()) {
				System.out.println("lon is equal");
			}
		}
		else {
			double lla = (latitude - a.getNode().getLatitude()) / (b.getNode().getLatitude() - a.getNode().getLatitude());  
			double llo = (longitude - a.getNode().getLongitude()) / (b.getNode().getLongitude() - a.getNode().getLongitude());
			double d = Math.abs(lla - llo);
			if (d < .1d && lla < 1d && lla > 0 && llo < 1d && llo > 0) {
				System.out.println(d + " lla " + lla + ", llo " + llo);
			}
		}
	}
}
