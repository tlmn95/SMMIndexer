package elastic;

import static org.elasticsearch.client.Requests.createIndexRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class ElasticSearchConnection {
	private Client client;
	private Node node;

	public ElasticSearchConnection() {
		node = new NodeBuilder().node();
		client = node.client();
	}

	public ElasticSearchConnection(String ipAddress) throws Exception {
		Settings settings = Settings.settingsBuilder().put("client.transport.sniff", true).build();
		client = TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ipAddress), 9300));
	}

	public void closeConnection() {
		client.close();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void createIndexResponse(String indexname, String type, List<String> jsondata) {
		IndexRequestBuilder requestBuilder = client.prepareIndex(indexname, type).setRefresh(true);

		for (int i = 0; i < jsondata.size(); i++) {
			requestBuilder.setSource(jsondata.get(i)).get();
		}
	}

	public IndexResponse createIndexResponse(String indexname, String type, String jsondata) {
		IndexResponse response = client.prepareIndex(indexname, type).setSource(jsondata).get();
		return response;
	}

	public IndexResponse createIndexResponse(String indexname, String type, String id, String jsondata) {
		IndexResponse response = client.prepareIndex(indexname, type, id).setSource(jsondata).get();
		return response;
	}

	public void deleteIndex(String indexname) {
		DeleteIndexResponse delete = client.admin().indices().delete(new DeleteIndexRequest(indexname)).actionGet();
		if (!delete.isAcknowledged()) {
			System.out.println("Index wasn't deleted");
		}
	}

	public void createMapping(String indexname, String type) {
		XContentBuilder builder;
		try {
			builder = XContentFactory.jsonBuilder().startObject().startObject(type).startObject("properties")
					.startObject("date").field("type", "date").endObject().startObject("source").field("type", "string")
					.field("index", "not_analyzed").endObject().startObject("keywords").field("type", "string")
					.field("index", "not_analyzed").endObject().endObject().endObject().endObject();
			client.admin().indices().create(createIndexRequest(indexname).mapping(type, builder)).actionGet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
