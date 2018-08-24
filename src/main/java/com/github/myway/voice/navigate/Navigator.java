package com.github.myway.voice.navigate;

import com.github.myway.voice.gpx.Point;
import com.github.myway.voice.gpx.Segment;
import com.github.myway.voice.gpx.Track;
import com.github.myway.voice.osm.MapData;
import com.github.myway.voice.osm.NodeDistance;
import com.github.myway.voice.osm.Way;

public class Navigator {
	public NavigationInfo navigate(Position position, Track track, MapData map) {
		for (Segment segment : track.getSegments()) {
			for (Point point : segment.getPoints()) {
				NodeDistance node = map.findNode(point.getLatitude(), point.getLongitude(), 5d);
				System.out.println(point + " -> " + node);
			}
		}
		for (Segment segment : track.getSegments()) {
			for (Point point : segment.getPoints()) {
				Way way = map.findWay(point.getLatitude(), point.getLongitude(), 5d);
				System.out.println(point + " -> " + way);
			}
		}
		return null;
	}
}
