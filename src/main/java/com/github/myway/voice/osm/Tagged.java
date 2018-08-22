package com.github.myway.voice.osm;

import java.util.Map;

import lombok.Data;

@Data
public class Tagged {
	private String id;
	private Map<String, String> tags;
}
