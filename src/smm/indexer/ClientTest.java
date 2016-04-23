package smm.indexer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
 

public class ClientTest {	
	public static void main(String[] args) {
		
		String string = "";
		try { 
			InputStream crunchifyInputStream = new FileInputStream("/Users/TLMN/Desktop/CrunchifyJSON.txt");
			InputStreamReader crunchifyReader = new InputStreamReader(crunchifyInputStream);
			BufferedReader br = new BufferedReader(crunchifyReader);
			String line;
			while ((line = br.readLine()) != null) {
				string += line + "\n";
			}
			JSONObject jsonObject = new JSONObject(string);
			System.out.println(jsonObject);
 
			// Step2: Now pass JSON File Data to REST Service
			try {
				/*
				URL url = new URL("http://localhost:8080/CrunchifyTutorials/api/crunchifyService");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
				System.out.println("Here 1");
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				System.out.println("Here 2");
				string = "";
				while ((line = in.readLine()) != null) {
					string += line + '\n';
				}
				*/
				String JSON_STRING = jsonObject.toString();
				CloseableHttpClient httpClient = HttpClients.createDefault();
				StringEntity requestEntity = new StringEntity(
					    JSON_STRING,
					    ContentType.APPLICATION_JSON);

				HttpPost postMethod = new HttpPost("http://localhost:8080/CrunchifyTutorials/api/crunchifyService");
				postMethod.setEntity(requestEntity);

				CloseableHttpResponse response1 = httpClient.execute(postMethod);
				System.out.println("\nCrunchify REST Service Invoked Successfully..");
				try {
				    System.out.println("Status code: " + response1.getStatusLine().getStatusCode());
				    HttpEntity entity1 = response1.getEntity();
				    // do something useful with the response body
				    // and ensure it is fully consumed
				    // System.out.println(EntityUtils.toString(entity1));
				    
					// jsonObject = new JSONObject(string);
					System.out.println("Response from server: " + EntityUtils.toString(entity1));
				    EntityUtils.consume(entity1);
				} finally {
				    response1.close();
				    httpClient.close();
				}
				
			} catch (Exception e) {
				System.out.println("\nError while calling Crunchify REST Service");
				System.out.println(e);
			} finally {
				// in.close();
			}
 
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}