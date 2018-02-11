package io.springboot.samples;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.springboot.samples.api.DataApi;
import io.springboot.samples.model.DataRessource;
import io.springboot.samples.server.SpringBootServerApp;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes=SpringBootServerApp.class)
@Import({RestClientConfig.class})
@TestPropertySource(properties= {
	"server.port=9999",
	"rest.api.host=http://localhost:${server.port}",
})
public class DataApiTest {
	
	@Autowired
	DataApi dataApi;
	
	@Test
	public void sendAndGetData() {
		final DataRessource sampleResource = new DataRessource();
		sampleResource.setId("ID-1");
		sampleResource.setName("Sample Data");
		sampleResource.setData("Some Data");
		final String source = "samples";
		dataApi.postData(source, sampleResource );
		List<DataRessource> data = dataApi.getData(source);
		assertEquals(1, data.size());
		assertEquals(sampleResource, data.get(0));
	}
	
}
