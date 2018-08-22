package com.github.myway.voice.gpx;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.github.myway.common.BoundingBox;

import lombok.Data;

@Data
public class Segment {
	private List<Point> points;

	public BoundingBox bbx() {
		if (CollectionUtils.isEmpty(points)) {
			return null;
		}
		BoundingBox bbx = null;
		for (Point point : points) {
			bbx = bbx == null ? BoundingBox.fromPoint(point) : BoundingBox.join(bbx, point);
		}
		return bbx;
	}
}
