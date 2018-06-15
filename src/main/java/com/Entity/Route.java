package com.Entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.Wrapper.RouteDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "route")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "routeId")
public class Route {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer routeId;
	
	private String source;
	
	private String destination;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval=true)
	@JoinTable(name="Route_stations")
	private List<Station> stops= new ArrayList<>();
	
	public Route() {}
	
	public Route(RouteDTO routeDto) {
		this.routeId=routeDto.getRouteId();
		this.source=routeDto.getSource();
		this.destination=routeDto.getDestination();
		if(routeDto.getStops()!=null) {
		this.stops=routeDto.getStops().stream().map(s->new Station(s)).collect(Collectors.toList());}
	}
	
	public Route(Integer routeId, String source, String destination, List<Station> stops,
			LinkedList<Integer> distance) {
		this.routeId = routeId;
		this.source = source;
		this.destination = destination;
		this.stops = stops;
		this.distance = distance;
	}

	private LinkedList<Integer> distance;
	
	public LinkedList<Integer> getDistance() {
		return distance;
	}
	public int getTotalDistance(Station start,Station end) {
		int startIndex=stops.indexOf(start);
		int endIndex=stops.indexOf(end);
		int totalDistance=0;
		int i;
		for(i=startIndex;i<=endIndex;i++)
		{
			totalDistance+= distance.get(i);
		}
		return totalDistance;
	}
	
	public void setDistance(LinkedList<Integer> distance) {
		this.distance = distance;
	}
	
	public void addStationInList(Station addst) {
		stops.add(addst);
	}
	
	public void addAfterStation(Station station1, Station addStation) {
		int index = this.stops.indexOf(station1);
		this.stops.add(index, addStation);
	}

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<Station> getStops() {
		return stops;
	}

	public void setStops(List<Station> stops) {
		this.stops = stops;
	}

}