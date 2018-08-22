package com.github.myway.voice.gpx;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.github.myway.common.BoundingBox;

import lombok.Data;

@Data
public class Track {
	private List<Segment> segments;

	public BoundingBox bbx() {
		if (CollectionUtils.isEmpty(segments)) {
			return null;
		}
		BoundingBox bbx = null;
		for (Segment segment : segments) {
			bbx = bbx == null ? segment.bbx() : BoundingBox.join(bbx, segment.bbx());
		}
		return bbx;
	}
}
