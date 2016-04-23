package common.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

// import edu.vnu.uet.smm.common.util.Utility;
import com.google.gson.Gson;

public class SMMDocument implements Serializable, Comparable<SMMDocument> {
	private static final long serialVersionUID = -8408096307274418340L;
	private String type;//document, comment
	private String link;
	private String category;
	
	private String date;//original date from raw data
	private String formattedDate;//date in the same format
	private String lastUpdate;//the last time document is updated by system
	private String dateSearch; //dateSearch from lucene
	
	private String title;
	private String like;
	
	private String content;//raw content
	private String contentTok;//content after tokenization
	private String contentPOS;//content after tokenization and POS
	
	private String quotation;
	
	private String source;//vnexpress.net, tinhte.vn,...
	private String sourceCategory;//news, forum, social_network
	
	private String id;//id of document after indexing in Lucene
	private String parentID;//id of document's parent
	private String articleID;//id of the article (thread)
	private int sentimentValue;
	private String sentiment;
	private ArrayList<SMMDocument> comments;
	private String author;
	private String authorID;
	private String sourceDocumentID;
	private String tags;
	
	public String getSourceDocumentID() {
		if (sourceDocumentID == null) {
			sourceDocumentID = "";
		}
		return sourceDocumentID;
	}

	public void setSourceDocumentID(String sourceDocumentID) {
		this.sourceDocumentID = sourceDocumentID;
	}

	public String getAuthorID() {
		if (authorID == null) {
			authorID = "";
		}
		return authorID;
	}

	public void setAuthorID(String authorID) {
		this.authorID = authorID;
	}

	public String getAuthor() {
		if (author == null) {
			author = "";
		}
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	//this is used for determine the priority to update comment for a document
	private int updatePriority = 0;
	private int numberOfComments = 0;

	
	public SMMDocument(String type,String link, String category, 
			String date, String formattedDate, String lastUpdate, String title,
			String like, String content, String contentTok , String contentPOS,
			String quotation, String source, String sourceCategory,
			String id, String parentID, String articleID, String author) {
		super();
		this.type = type;
		this.link = link;
		this.category = category;
		this.date = date;
		this.formattedDate = formattedDate;
		this.lastUpdate = lastUpdate;
		this.title = title;
		this.like = like;
		this.content = content;
		this.contentTok = contentTok;
		this.contentPOS = contentPOS;
		this.quotation = quotation;
		this.source = source;
		this.sourceCategory = sourceCategory;
		this.setId(id);
		this.setParentId(parentID);
		this.setArticleId(articleID);
		this.sentimentValue = 0;
		this.sentiment = "";
		this.author = author;
		this.comments = new ArrayList<SMMDocument>();
	}
	
	public SMMDocument() {
		// TODO Auto-generated constructor stub
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFormattedDate() {
		return formattedDate;
	}
	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLike() {
		return like;
	}
	public void setLike(String like) {
		this.like = like;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentTok() {
		return contentTok;
	}
	public void setContentTok(String contentTok) {
		this.contentTok = contentTok;
	}
	public String getContentPOS() {
		return contentPOS;
	}
	public void setContentPOS(String contentPOS) {
		this.contentPOS = contentPOS;
	}
	public String getQuotation() {
		return quotation;
	}
	public void setQuotation(String quotation) {
		this.quotation = quotation;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceCategory() {
		return sourceCategory;
	}
	public void setSourceCategory(String sourceCategory) {
		this.sourceCategory = sourceCategory;
	}

	public String getId() {
		return id;
	}

	public void setId(String documentId) {
		this.id = documentId;
	}
	public String getParentId() {
		return parentID;
	}

	public void setParentId(String parentID) {
		this.parentID = parentID;
	}
	public String getArticleId() {
		return articleID;
	}

	public void setArticleId(String articleID) {
		this.articleID = articleID;
	}
	
	public int getSentimentValue(){
		return sentimentValue;
	}
	
	public void setSentimentValue(int value){
		this.sentimentValue = value;
	}
	public String getSentiment(){
		return sentiment;
	}
	public void setSentiment(String sentiment){
		this.sentiment = sentiment;
	}
	
	public ArrayList<SMMDocument> getComments() {
		return comments;
	}
	public void setComments(ArrayList<SMMDocument> comments) {
		this.comments = comments;
	}
	
	public void addComment(SMMDocument comment) {
		this.comments.add(comment);
	}
	
	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getArticleID() {
		return articleID;
	}

	public void setArticleID(String articleID) {
		this.articleID = articleID;
	}

	public int getUpdatePriority() {
		return updatePriority;
	}

	public void setUpdatePriority(int updatePriority) {
		this.updatePriority = updatePriority;
	}

	public String getDateSearch() {
		return dateSearch;
	}

	public void setDateSearch(String dateSearch) {
		this.dateSearch = dateSearch;
	}

	public int getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(int numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	public String getAllComment(){
		String s = "";
		for (SMMDocument comment : comments)
			s += "<type>" + comment.getType() + "</type><link>" + comment.getLink() + "</link><category>"
			+ comment.getCategory() + "</category><date>" + comment.getDate() + "</date><formattedDate>" 
			+ comment.getFormattedDate() + "</formattedDate><lastUpdate>" + comment.getLastUpdate()
			+ "</lastUpdate><title>" + comment.getTitle() + "</title><like>" + comment.getLike()
			+ "</like><content>" + comment.getContent() + "</content><quotation>" + comment.getQuotation()
			+ "</quotation><source>" + comment.getSource() + "</source><sourceCategory>"
			+ comment.getSourceCategory() + "</sourceCategory><id>" + comment.getId() + "</id><parentID>"
			+ comment.getParentId() + "</parentID><articleID>" + comment.getArticleId()
			+ "</articleID><contentPOS>" + comment.getContentPOS() + "</contentPOS>\n";
		return s;
	}
	public String toString(){
		return "<type>" + getType() + "</type><link>" + getLink() + "</link><category>"
		+ getCategory() + "</category><date>" + getDate() + "</date><formattedDate>" 
		+ getFormattedDate() + "</formattedDate><lastUpdate>" + getLastUpdate()
		+ "</lastUpdate><title>" + getTitle() + "</title><like>" + getLike()
		+ "</like><content>" + getContent() + "</content><quotation>" + getQuotation()
		+ "</quotation><source>" + getSource() + "</source><sourceCategory>"
		+ getSourceCategory() + "</sourceCategory><id>" + getId() + "</id><parentID>"
		+ getParentId() + "</parentID><articleID>" + getArticleId()
		+ "</articleID><contentPOS>" + getContentPOS() + "</contentPOS>\n" + getAllComment();
	}
	
	public String toStringOfComments(){
		return "<type>" + getType() + "</type><link>" + getLink() + "</link><category>"
		+ getCategory() + "</category><date>"  
		+ getFormattedDate() + "</formattedDate><lastUpdate>" + getLastUpdate()
		+ "</lastUpdate><title>" + getTitle() + "</title><like>" + getLike()
		+ "</like><content>" + getContent() + "</content><quotation>" + getQuotation()
		+ "</quotation><source>" + getSource() + "</source><sourceCategory>"
		+ getSourceCategory() + "</sourceCategory><id>" + getId() + "</id><parentID>"
		+ getParentId() + "</parentID><articleID>" + getArticleId()
		+ "</articleID><contentPOS>" + getContentPOS() + "</contentPOS>"
		+ "<author>" + getAuthor() + "</author>";
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTags() {
		if (tags == null) tags = "";
		return tags;
	}

	public int compareTo(SMMDocument o) {
		return id.compareTo(o.getId());
	}

	public int hashCode() {
		return 31 * id.hashCode() + 47;
	}
	
	public String toJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
