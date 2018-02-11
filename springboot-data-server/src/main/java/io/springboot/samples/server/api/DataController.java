package io.springboot.samples.server.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import io.springboot.samples.server.model.DataRessource;

@RestController
class DataController implements DataApi {
	
	private Map<String, List<DataRessource>> resources = new HashMap<>();

	@Override
	public ResponseEntity<List<DataRessource>> getData(String source) {
		return new ResponseEntity<>(this.resources.getOrDefault(source, new ArrayList<>()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> postData(String source, DataRessource body) {
		final List<DataRessource> data = this.resources.getOrDefault(source, new ArrayList<>());
		data.add(body);
		this.resources.putIfAbsent(source, data);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}
