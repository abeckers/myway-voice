package com.github.myway.voice.gpx;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class GpxReader {
	public static Track loadTrack(InputStream stream) throws XMLStreamException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(stream);

		return new GpxReader().loadTrack(parser);
	}

	public static Track loadTrack(String file) throws IOException, XMLStreamException {
		try (InputStream stream = new FileInputStream(file)) {
			return loadTrack(stream);
		}
	}

	private Point currentPoint = null;
	private Segment currentSegment = null;
	private Track currentTrack = null;
	private String inElement = null;

	public Track loadTrack(XMLStreamReader parser) throws XMLStreamException {
		while (parser.hasNext()) {
			switch (parser.getEventType()) {
			case XMLStreamConstants.END_DOCUMENT:
				parser.close();
				break;

			case XMLStreamConstants.START_ELEMENT:
				String name = parser.getLocalName();
				inElement = name;
				switch (name) {
				case "trk":
					currentTrack = new Track();
					break;
				case "trkseg":
					parseSegment();
					break;
				case "trkpt":
					parsePoint(parser);
					break;
				case "ele":
					break;
				}
				break;

			case XMLStreamConstants.CHARACTERS:
				if (!parser.isWhiteSpace() && inElement.equals("ele")) {
					currentPoint.setElevation(Double.parseDouble(parser.getText()));
				}
				break;

			default:
				break;
			}
			parser.next();
		}
		return currentTrack;
	}

	private void parseSegment() {
		currentSegment = new Segment();
		if (currentTrack.getSegments() == null) {
			currentTrack.setSegments(new ArrayList<>());
		}
		currentTrack.getSegments().add(currentSegment);
	}

	private void parsePoint(XMLStreamReader parser) {
		currentPoint = new Point();
		if (currentSegment.getPoints() == null) {
			currentSegment.setPoints(new ArrayList<>());
		}
		currentSegment.getPoints().add(currentPoint);
		for (int i = 0; i < parser.getAttributeCount(); i++) {
			String attrName = parser.getAttributeLocalName(i);
			switch (attrName) {
			case "lat":
				currentPoint.setLatitude(Double.parseDouble(parser.getAttributeValue(i)));
				break;
			case "lon":
				currentPoint.setLongitude(Double.parseDouble(parser.getAttributeValue(i)));
				break;
			}
		}
	}
}
