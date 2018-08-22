package com.github.myway.voice.cli;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import com.github.myway.voice.gpx.GpxReader;
import com.github.myway.voice.gpx.Track;
import com.github.myway.voice.navigate.Navigator;
import com.github.myway.voice.osm.MapData;
import com.github.myway.voice.osm.MapReader;
import com.github.myway.voice.osm.Node;
import com.github.myway.voice.osm.Way;

public class CliMain {
	private static final String TRACK = "src/test/resources/track.gpx";

	public static void main(String[] args) throws XMLStreamException, IOException {
		Track track = GpxReader.loadTrack(TRACK);
		System.out.println(track);
		System.out.println(track.bbx());

		MapData map = MapReader.loadMap("src/test/resources/map.xml");
		System.out.println(map.getBounds());
		System.out.println("Nodes");
		for (Node node : map.getNodes()) {
			System.out.println("\t" + node);
		}
		System.out.println("Ways");
		for (Way way : map.getWays()) {
			System.out.println("\t" + way);
		}

		new Navigator().navigate(null, track, map);
	}
}
