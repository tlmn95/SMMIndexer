package smm.indexer;
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
 
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.joda.time.DateTime;
import org.json.JSONObject;

import elastic.ElasticSearchConnection;
import common.base.SMMDocument;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

@Path("/")
public class RESTService {
	static ElasticSearchConnection es;
	
	static final String ELASTIC_HOSTNAME = "localhost";
	static final String INDEXNAME = "smm";
	
	@POST
	@Path("/crunchifyService")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crunchifyREST(InputStream incomingData) {
		try {
			es = new ElasticSearchConnection(ELASTIC_HOSTNAME);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Cannot connect to Elasticsearch server").build();
		}
		String data = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				data += line + '\n';
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		
		Gson gson = new Gson();
		System.out.println("-------------HERE---------------------------------");
		
		try{
			SMMDocument smmdoc = gson.fromJson(data, SMMDocument.class);
			
			System.out.println(smmdoc.toJson());
			
			String checkStatus = checkInputData(smmdoc);
			if (checkStatus == "OK")
				es.createIndexResponse(INDEXNAME, "article", smmdoc.getId(), smmdoc.toJson());
			else 
				return Response.status(409).entity(checkStatus).build();
		} catch (JsonParseException e){
			System.out.println("Error while parsing, check your data format");
			return Response.status(409).entity("Error while parsing, check your data format").build();
		} catch (NoNodeAvailableException e){
			System.out.println("No elasticsearch node is available");
			return Response.status(500).entity("No elasticsearch node is available").build();
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Error here");
			return Response.status(500).entity("There is some errors").build();
		}
		
		System.out.println("Data Received: " + data);
		
		// return HTTP response 200 in case of success
		return Response.status(200).entity("Indexed sucessfully").build();
	}
	
	private boolean checkDateFormat(String date){
		try {
			DateTime.parse(date);
		} catch (Exception e){
			return false;
		}
		return true;
	}
	
	private String checkInputData(SMMDocument doc){
		if (doc.getId() == null)
			return "Missing \'Id\' field";
		if (doc.getDate() == null)
			return "Missing \'Date\' field";
		if (doc.getContent() == null)
			return "Missing \'Content\' field";
		if (!checkDateFormat(doc.getDate()))
			return "Invalid Date format";
		return "OK";
	}
	
	@GET
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService(InputStream incomingData) {
		String result = "CrunchifyRESTService Successfully started..";
 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}
 
}