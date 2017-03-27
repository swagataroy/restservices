package eu.got.restservices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.io.ByteStreams;
@RestController
public class ServicesRestController {

	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private static final String APPLICATION_PDF = "application/pdf";

	@RequestMapping(value = "/available")
	public String available() {
		return "Spring in Action";
	}

	@RequestMapping(value = "/download", produces = APPLICATION_PDF)
	public @ResponseBody Resource download(HttpServletResponse response) throws IOException {
		logger.info("Inside ServicesRestController download");

		// File file = getFile();
		ClassPathResource pdfFile = new ClassPathResource("guid.pdf");

		/*if (pdfFile == null) {
			System.out.println("Swagata");
		} else {
			System.out.println("Swagata 2222");
		}*/
		
		response.setContentType(APPLICATION_PDF);
		response.setContentLength((int) pdfFile.contentLength());
		logger.info("Inside ServicesRestController download 2222");
		response.addHeader("Content-Type","application/pdf");
		response.addHeader("Content-Disposition","attachment");
		response.addHeader("filename", "guide.pdf");
		//response.setHeader("Content-Disposition", "inline; filename=" + pdfFile.getFilename());
		//response.setHeader("Content-Length", String.valueOf(pdfFile.contentLength()));
		return pdfFile;
	}
	
	 @RequestMapping(value = "/docs1", method = RequestMethod.GET, produces = "application/pdf")
	    public ResponseEntity<byte[]> getDocs(@RequestParam("error") boolean error) throws IOException {
	    	System.out.println("Swagata inside docs");
	        byte[] doc;
	        HttpHeaders headers = new HttpHeaders();
	        try {
	            ClassPathResource myPdf = new ClassPathResource("guid.pdf");
	            headers.set("Content-Disposition", "attachment; filename=guide.pdf");
	            headers.setContentLength(myPdf.getFile().length());

	            doc = ByteStreams.toByteArray(myPdf.getInputStream());

	            if (error) {
	                throw new NullPointerException();
	            }

	            return new ResponseEntity<>(doc, headers, HttpStatus.OK);

	        } catch (Exception e) {
	            headers.setContentType(MediaType.APPLICATION_JSON);
	            throw new IllegalArgumentException("PDF NOT FOUND");
	        }
	    }

}
