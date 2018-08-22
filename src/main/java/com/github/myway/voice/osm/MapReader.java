package com.github.myway.voice.osm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.github.myway.common.BoundingBox;

public class MapReader {
	public static MapData loadMap(InputStream stream) throws XMLStreamException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(stream);

		return new MapReader().loadMap(parser);
	}

	public static MapData loadMap(String file) throws IOException, XMLStreamException {
		try (InputStream stream = new FileInputStream(file)) {
			return loadMap(stream);
		}
	}

	public static void main(String[] args) throws XMLStreamException, IOException {
		MapData map = loadMap("/Users/abeckers/private/myway/myway-voice/map.xml");
		System.out.println(map.getBounds());
		System.out.println("Nodes");
		for (Node node : map.getNodes()) {
			System.out.println("\t" + node);
		}
		System.out.println("Ways");
		for (Way way : map.getWays()) {
			System.out.println("\t" + way);
		}
	}

	private MapData currentMap = null;
	private Tagged currentTagged;
	private Way currentWay;

	public MapData loadMap(XMLStreamReader parser) throws XMLStreamException {
		while (parser.hasNext()) {
			switch (parser.getEventType()) {
			case XMLStreamConstants.END_DOCUMENT:
				parser.close();
				break;

			case XMLStreamConstants.START_ELEMENT:
				String name = parser.getLocalName();
				switch (name) {
				case "osm":
					currentMap = new MapData();
					break;
				case "bounds":
					parseBounds(parser);
					break;
				case "node":
					parseNode(parser);
					break;
				case "nd":
					parseNodeRef(parser);
					break;
				case "tag":
					parseTag(parser);
					break;
				case "way":
					parseWay(parser);
					break;
				case "ele":
					break;
				}
				break;

			default:
				break;
			}
			parser.next();
		}
		return currentMap;
	}

	private void parseWay(XMLStreamReader parser) {
		currentWay = new Way();
		currentTagged = currentWay;
		if (currentMap.getWays() == null) {
			currentMap.setWays(new ArrayList<>());
		}
		currentMap.getWays().add(currentWay);
		for (int i = 0; i < parser.getAttributeCount(); i++) {
			String attrName = parser.getAttributeLocalName(i);
			switch (attrName) {
			case "id":
				currentWay.setId(parser.getAttributeValue(i));
				break;
			}
		}
		addTagged(currentWay);
	}

	private void parseTag(XMLStreamReader parser) {
		String k = null;
		String v = null;
		for (int i = 0; i < parser.getAttributeCount(); i++) {
			String attrName = parser.getAttributeLocalName(i);
			switch (attrName) {
			case "k":
				k = parser.getAttributeValue(i);
				break;
			case "v":
				v = parser.getAttributeValue(i);
				break;
			}
		}
		if (currentTagged.getTags() == null) {
			currentTagged.setTags(new HashMap<>());
		}
		currentTagged.getTags().put(k, v);
	}

	private void parseNodeRef(XMLStreamReader parser) {
		NodeRef nd = new NodeRef();
		if (currentWay.getNodes() == null) {
			currentWay.setNodes(new ArrayList<>());
		}
		currentWay.getNodes().add(nd);
		for (int i = 0; i < parser.getAttributeCount(); i++) {
			String attrName = parser.getAttributeLocalName(i);
			switch (attrName) {
			case "ref":
				nd.setId(parser.getAttributeValue(i));
				break;
			}
		}
		Node node = (Node) currentMap.getObjects().get(nd.getId());
		if (node != null) {
			nd.setNode(node);
			if (node.getWays() == null) {
				node.setWays(new ArrayList<>());
			}
			node.getWays().add(currentWay);
		}
	}

	private void parseNode(XMLStreamReader parser) {
		Node currentNode = new Node();
		currentTagged = currentNode;
		if (currentMap.getNodes() == null) {
			currentMap.setNodes(new ArrayList<>());
		}
		currentMap.getNodes().add(currentNode);
		for (int i = 0; i < parser.getAttributeCount(); i++) {
			String attrName = parser.getAttributeLocalName(i);
			switch (attrName) {
			case "id":
				currentNode.setId(parser.getAttributeValue(i));
				break;
			case "lat":
				currentNode.setLatitude(Double.parseDouble(parser.getAttributeValue(i)));
				break;
			case "lon":
				currentNode.setLongitude(Double.parseDouble(parser.getAttributeValue(i)));
				break;
			}
		}
		addTagged(currentNode);
	}

	private void addTagged(Tagged tagged) {
		if (currentMap.getObjects() == null) {
			currentMap.setObjects(new HashMap<>());
		}
		currentMap.getObjects().put(tagged.getId(), tagged);
	}

	private void parseBounds(XMLStreamReader parser) {
		BoundingBox bbx = new BoundingBox();
		currentMap.setBounds(bbx);
		for (int i = 0; i < parser.getAttributeCount(); i++) {
			String attrName = parser.getAttributeLocalName(i);
			switch (attrName) {
			case "maxlat":
				bbx.setLatitudeMax(Double.parseDouble(parser.getAttributeValue(i)));
				break;
			case "maxlon":
				bbx.setLongitudeMax(Double.parseDouble(parser.getAttributeValue(i)));
				break;
			case "minlat":
				bbx.setLatitudeMin(Double.parseDouble(parser.getAttributeValue(i)));
				break;
			case "minlon":
				bbx.setLongitudeMin(Double.parseDouble(parser.getAttributeValue(i)));
				break;
			}
		}
	}

}
