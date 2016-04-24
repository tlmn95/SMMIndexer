package smm.indexer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

@Path("/file")
public class RESTUpload {
	private String UploadURL = "D://uploaded/";
	
	@SuppressWarnings("finally")
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	/*
	public Response uploadFile(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String uploadedFileLocation = UploadURL + fileDetail.getFileName();

		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);

		String output = "File uploaded to : " + uploadedFileLocation;

		return Response.status(200).entity(output).build();

	}
	*/
	public Response uploadMultiple(@FormDataParam("file") FormDataBodyPart body){
		String output = "";
		try {
			for(BodyPart part : body.getParent().getBodyParts()){
				InputStream is = part.getEntityAs(InputStream.class);
				ContentDisposition meta = part.getContentDisposition();
				String Position = UploadURL + meta.getFileName();
				writeToFile(is, Position);
				output += "Upload " + meta.getFileName() + " successful\n";
			}
		}
		catch ( Exception e ) {
			output += "Upload the rest failed\n";
			return Response.status( 404 ).entity( output ).build();
		}
		finally {
			return Response.status( 404 ).entity( output ).build();
		}
	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
		String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}