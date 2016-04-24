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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class GetDataTest {	
	public static void main(String[] args) {
		try { 
			String string = "";
			InputStream inputstream = new FileInputStream("C:/Users/Eriksen/Wordspace/SMMIndexer/WebContent/data/CrunchifyJSON.txt");
			InputStreamReader reader = new InputStreamReader(inputstream);
			BufferedReader br = new BufferedReader(reader);
			String line;
			while ((line = br.readLine()) != null) {
				string += line + "\n";
			}
			JSONObject jsonObject = new JSONObject(string);
			System.out.println(jsonObject);
 			
			// Step2: Now pass JSON File Data to REST Service
			try {
				String JSON_STRING = jsonObject.toString();
				CloseableHttpClient httpClient = HttpClients.createDefault();
				StringEntity requestEntity = new StringEntity(
					    JSON_STRING,
					    ContentType.APPLICATION_JSON);

				HttpPost postMethod = new HttpPost("http://localhost:8080/SMMIndexer/data/");

				postMethod.setEntity(requestEntity);
				CloseableHttpResponse response = httpClient.execute(postMethod);
				System.out.println("\nREST Service Invoked Successfully..");
				try {
				    System.out.println("Status code: " + response.getStatusLine().getStatusCode());
				    HttpEntity entity = response.getEntity();
				    
				    String responseFromServer = EntityUtils.toString(entity);
					Document doc = Jsoup.parse( responseFromServer );
					
					Elements headlines = doc.select("a[href]");
					for( Element e : headlines ) {
						System.out.println( e.attr("href") );
					}	
					
				    EntityUtils.consume(entity);
				} finally {
				    response.close();
				    httpClient.close();
				}
				
			} catch (Exception e) {
				System.out.println("\nError while calling REST Service");
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