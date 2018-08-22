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
}
