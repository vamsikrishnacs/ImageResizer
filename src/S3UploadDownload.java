import java.io.File;
import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3UploadDownload {
	private static String bucketName     = "bucketresize";
	private static String keyName = null;
	private AmazonS3 s3client = null;
	public S3UploadDownload() throws IOException {
		s3client = new AmazonS3Client(new ProfileCredentialsProvider());
	}

	public void uploadToS3(File file) {

		try {
			System.out.println("Uploading a new object to S3 from a file\n");
			keyName = "upload/" + file.getName();
			s3client.putObject(new PutObjectRequest(
					bucketName, keyName, file).withCannedAcl(CannedAccessControlList.PublicRead));

		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which " +
					"means your request made it " +
					"to Amazon S3, but was rejected with an error response" +
					" for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which " +
					"means the client encountered " +
					"an internal error while trying to " +
					"communicate with S3, " +
					"such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
	}

	public String downloadFromS3(){

		String resultString = "";
		try {
			System.out.println("Listing objects");
			final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withPrefix("upload");
			ListObjectsV2Result result;
			do {               
				result = s3client.listObjectsV2(req);

				for (S3ObjectSummary objectSummary : 
					result.getObjectSummaries()) {
					resultString += s3client.getUrl(bucketName, objectSummary.getKey()).toString() + ","; 
				}
				req.setContinuationToken(result.getNextContinuationToken());
			} while(result.isTruncated() == true ); 

			return resultString;
		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, " +
					"which means your request made it " +
					"to Amazon S3, but was rejected with an error response " +
					"for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, " +
					"which means the client encountered " +
					"an internal error while trying to communicate" +
					" with S3, " +
					"such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}

		return null;
	}
}
