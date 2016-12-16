import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ImageResizeReqRes extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4802253675484906972L;
	private boolean isMultipart;
	private int maxFileSize = 50 * 1024 * 1024;
	private int maxMemSize = 4 * 1024 * 1024;

	public void init( ){

	}
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, java.io.IOException {
		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter( );
		if( !isMultipart ){
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet upload</title>");  
			out.println("</head>");
			out.println("<body>");
			out.println("<p>No file uploaded</p>"); 
			out.println("</body>");
			out.println("</html>");
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File("c:\\temp"));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax( maxFileSize );

		try{ 
			// Parse the request to get file items.
			List<FileItem> fileItems = upload.parseRequest(request);

			// Process the uploaded file items
			Iterator<FileItem> i = fileItems.iterator();


			while ( i.hasNext () ) 
			{
				FileItem fi = (FileItem)i.next();
				if ( !fi.isFormField () )	
				{
					// Get the uploaded file parameters
					String fileName = fi.getName();
					// Write the file

					File file = new File(fileName);

					fi.write( file ) ;
					resize(file);
					file.delete();
					out.println("https://s3-us-west-2.amazonaws.com/bucketresize/upload/"+fileName);
				}

			}

		}catch(Exception ex) {
			System.out.println(ex);
		}
		out.close();
	}



	private void resize(File tmpfile) throws IOException {
		// TODO Auto-generated method stub

		Image img=null;

		try
		{
			img = ImageIO.read(tmpfile);
		}
		catch(Exception e)
		{
			System.out.println("Image cannot be read");
		}

		Resize rz=new Resize(img);
		BufferedImage result=rz.resizeImage();
		File tmp = new File(tmpfile.getName());
		ImageIO.write(result, "jpg", tmp);
		System.out.println(tmp.getName());
		writeTos3(tmp);

		tmp.delete();

	}

	private void writeTos3(File file){
		try {
			S3UploadDownload s3 = new S3UploadDownload();
			s3.uploadToS3(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, java.io.IOException {

		throw new ServletException("GET method used with " +
				getClass( ).getName( )+": POST method required.");
	} 
}
