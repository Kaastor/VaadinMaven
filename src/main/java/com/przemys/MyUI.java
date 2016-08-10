package com.przemys;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MapClickListener;
import com.vaadin.tapio.googlemaps.client.layers.GoogleMapKmlLayer;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
//@Theme("mytheme")
@Widgetset("com.przemys.MyAppWidgetset")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        GoogleMap googleMap = new GoogleMap("AIzaSyCUNnX2w3iRiSAckvJ4zKFCZKZ5SY9EhJ8", null, "english");
        layout.setSizeFull();
        googleMap.setSizeFull();
        googleMap.addMarker("DRAGGABLE: Marker 1",
                new LatLon(52.683366, 18.629028), true, null);
        googleMap.addMarker("DRAGGABLE: Marker 2", new LatLon(
                41.914585, 12.502345), false, null);
        googleMap.setMinZoom(4);
        googleMap.setMaxZoom(16);

        ArrayList<LatLon> points = new ArrayList<>();
        points.add(new LatLon(52.683366, 18.629028));
        points.add(new LatLon(41.914585, 12.502345));
        GoogleMapPolyline overlay = new GoogleMapPolyline(
                points, "#d31717", 0.8, 10);
        googleMap.addPolyline(overlay);


        googleMap.addMapClickListener(new MapClickListener() {
            @Override
            public void mapClicked(LatLon position) {

                googleMap.addMarker("Marker", new LatLon(
                        position.getLat(), position.getLon()), true, null);
            }
        });


        layout.addComponents(googleMap);
        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
