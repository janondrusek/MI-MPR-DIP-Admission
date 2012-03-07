package cz.cvut.fit.mi_mpr_dip.admission.web.client;

import java.util.Collection;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public class TestClient {

	private String baseURI;
	private WebClient client;

	public TestClient() {
		super();
	}

	public TestClient(String baseUrl) {
		super();
		this.baseURI = baseUrl;
	}

	public void testImport(Collection<Admission> collection) {
		connect();

		Response r = client.path("admissions/").accept("text/xml")
				.postCollection(collection, Admission.class);
	}

	private void connect() {
		if (client == null) {
			client = WebClient.create(baseURI);
		}
	}

	public String getBaseURI() {
		return baseURI;
	}

	public void setBaseURI(String baseURI) {
		this.baseURI = baseURI;
	}

}
