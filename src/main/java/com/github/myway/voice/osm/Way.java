package com.github.myway.voice.osm;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = "nodes")
public class Way extends Tagged {
	private List<NodeRef> nodes;
	
	public Node center() {
		Node node = new Node();
		int cnt = 0;
		for (NodeRef ref : nodes) {
			if (ref.getNode() != null) {
				cnt++;
				node.setLongitude(node.getLongitude() + ref.getNode().getLongitude());
				node.setLatitude(node.getLatitude() + ref.getNode().getLatitude());
			}
		}
		if (cnt > 0) {
			node.setLongitude(node.getLongitude() / cnt);
			node.setLatitude(node.getLatitude() / cnt);
		}
		return node;
	}
}
