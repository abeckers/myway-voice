package com.github.myway.voice.cli;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import com.github.myway.osm.MapData;
import com.github.myway.osm.MapReader;
import com.github.myway.osm.Node;
import com.github.myway.osm.Way;
import com.github.myway.render.AwtDrawableFactory;
import com.github.myway.render.MapToImage;
import com.github.myway.voice.gpx.GpxReader;
import com.github.myway.voice.gpx.Track;
import com.github.myway.voice.navigate.Navigator;

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
		try (MapToImage m2i = new MapToImage(new AwtDrawableFactory(), map)) {
			m2i.drawMap();
			m2i.drawTrack(track);
			m2i.getDrawable().saveTo("/tmp/map.jpg");
		}
	}
}
