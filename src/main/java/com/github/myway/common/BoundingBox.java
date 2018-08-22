package com.github.myway.common;

import com.github.myway.voice.gpx.Point;

import lombok.Data;

@Data
public class BoundingBox {
	private double latitudeMin;
	private double latitudeMax;
	private double longitudeMin;
	private double longitudeMax;

	public static BoundingBox join(BoundingBox bbx1, BoundingBox bbx2) {
		BoundingBox bbxn = new BoundingBox();
		bbxn.setLatitudeMax(bbx1.getLatitudeMax());
		bbxn.setLatitudeMin(bbx1.getLatitudeMin());
		bbxn.setLongitudeMax(bbx1.getLongitudeMax());
		bbxn.setLongitudeMin(bbx1.getLongitudeMin());
		if (bbx2.getLatitudeMin() < bbxn.getLatitudeMin()) {
			bbxn.setLatitudeMin(bbx2.getLatitudeMin());
		}
		if (bbx2.getLatitudeMax() > bbxn.getLatitudeMax()) {
			bbxn.setLatitudeMax(bbx2.getLatitudeMax());
		}
		if (bbx2.getLongitudeMin() < bbxn.getLongitudeMin()) {
			bbxn.setLongitudeMin(bbx2.getLongitudeMin());
		}
		if (bbx2.getLongitudeMax() < bbxn.getLatitudeMin()) {
			bbxn.setLongitudeMax(bbx2.getLongitudeMax());
		}
		return bbxn;
	}

	public static BoundingBox join(BoundingBox bbx, Point point) {
		BoundingBox bbxn = new BoundingBox();
		bbxn.setLatitudeMax(bbx.getLatitudeMax());
		bbxn.setLatitudeMin(bbx.getLatitudeMin());
		bbxn.setLongitudeMax(bbx.getLongitudeMax());
		bbxn.setLongitudeMin(bbx.getLongitudeMin());
		if (point.getLatitude() < bbxn.getLatitudeMin()) {
			bbxn.setLatitudeMin(point.getLatitude());
		}
		if (point.getLatitude() > bbxn.getLatitudeMax()) {
			bbxn.setLatitudeMax(point.getLatitude());
		}
		if (point.getLongitude() < bbxn.getLongitudeMin()) {
			bbxn.setLongitudeMin(point.getLongitude());
		}
		if (point.getLongitude() < bbxn.getLatitudeMax()) {
			bbxn.setLongitudeMax(point.getLongitude());
		}
		return bbxn;
	}

	public static BoundingBox fromPoint(Point point) {
		BoundingBox bbx = new BoundingBox();
		bbx.setLatitudeMax(point.getLatitude());
		bbx.setLatitudeMin(point.getLatitude());
		bbx.setLongitudeMax(point.getLongitude());
		bbx.setLongitudeMin(point.getLongitude());
		return bbx;
	}
}
