package com.example.NavigationMap;

import com.google.android.gms.maps.model.Polyline;
import com.google.maps.model.DirectionsLeg;

public class RoadPath {
    public Polyline polyline;
    public DirectionsLeg directionsLeg;


    public RoadPath(Polyline polyline, DirectionsLeg directionsLeg) {
        this.polyline = polyline;
        this.directionsLeg = directionsLeg;
    }

    public Polyline getPolyline() {
        return polyline;
    }


    public DirectionsLeg getLeg() {
        return directionsLeg;
    }

    @Override
    public String toString() {
        return "PolylineData{" +
                "polyline=" + polyline +
                ", leg=" + directionsLeg +
                '}';
    }
}
