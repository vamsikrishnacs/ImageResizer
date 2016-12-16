import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayImages extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4802253675484906972L;

	public void init( ){

	}
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, java.io.IOException {
		// Check that we have a file upload request
		PrintWriter pw = response.getWriter();
		try
		{
			S3UploadDownload s=new S3UploadDownload();
			pw.println(s.downloadFromS3());
		}

		catch(Exception ex) {
			System.out.println(ex);
		}
		pw.close();
	}

	public void doGet(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, java.io.IOException {

		throw new ServletException("GET method used with " +
				getClass( ).getName( )+": POST method required.");
	} 
}
