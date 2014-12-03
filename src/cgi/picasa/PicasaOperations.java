package cgi.picasa;

import static cgi.picasa.Constants.*;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.ServiceException;

/**
 * After connection, a series of actions can be done on a Picasa album, listing, download,
 * generating HTML code using the content etc.
 * */
public class PicasaOperations {

	private PicasawebService picasawebService;
	private Settings settings;
	
	public PicasaOperations(PicasawebService picasawebService, Settings settings) {
		this.picasawebService = picasawebService;
		this.settings = settings;
	}


	/**
	 * List all albums of the given user
	 * */
	protected void listAlbums(){
		URL feedUrl = null;
		try {
			feedUrl = new URL(PICASA_URL_BASE + settings.getPicasaUser() + "?kind=album");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		UserFeed myUserFeed = null;
		try {
			myUserFeed = picasawebService.getFeed(feedUrl, UserFeed.class);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		for (AlbumEntry myAlbum : myUserFeed.getAlbumEntries()) {
		    System.out.println(myAlbum.getTitle().getPlainText() + "*** URL with ID:  \n" + myAlbum.getId());
		}
	}


	/**
	 * Gets the URL feed of an album.
	 * */
	protected AlbumFeed getFeedForAlbum(){
		
		URL feedUrl = null;

		try {
			
			String parameter = "?start-index=" + settings.getPhotoStartIndex() + "&max-results=" + settings.getPhotoMaxResults();
			feedUrl = new URL(PICASA_URL_BASE + settings.getPicasaUser() + "/albumid/" + settings.getPicasaAlbumId() + parameter);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		AlbumFeed feed = new AlbumFeed();
		try {
			feed = picasawebService.getFeed(feedUrl, AlbumFeed.class);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return feed;
	}


	/**
	 * Download an album for a given feed
	 * */
	public void downloadAlbum(){
		
		AlbumFeed feed = getFeedForAlbum();
		
		File folder = createFolderForAlbum(feed.getName());
		
		for(PhotoEntry photo : feed.getPhotoEntries()) {
			
			String imgUrl = photo.getMediaContents().get(0).getUrl();
			
			imgUrl = getLinkForSize(imgUrl, 0); // adding "s0" ~ maximum size
			
			String photoName = getUniqueFileName(folder, photo.getTitle().getPlainText());
			
			downloadFile(imgUrl, photoName);
		}
		
		System.out.println("Download album '" +  feed.getName() + "' completed.");
	}


	private String getUniqueFileName(File folder, String name) {
		String photoName = folder + "/" + name;
		
		int counter = 1;
		if ((new File(photoName)).exists()){
			photoName = photoName.substring(0, photoName.lastIndexOf('.')) + "-" + counter + ".jpg";
			counter++;
		}
		return photoName;
	}
	
	private File createFolderForAlbum(String title){
		
		File dir = new File(title);
		dir.mkdir();
		
		return dir;
	}

	/**
	 * Download a single photo
	 * */
    private void downloadFile(String urlStr, String file){
    	
    	FileOutputStream fis = null;
    	BufferedInputStream bis = null;
    	
    	try{
	        URL url = new URL(urlStr);
	        
	        bis = new BufferedInputStream(url.openStream());
	        
	        fis = new FileOutputStream(file);
	        
	        byte[] buffer = new byte[2048];
	        int count=0;
	        
	        while((count = bis.read(buffer, 0, 2048)) != -1)
	        {
	            fis.write(buffer, 0, count);
	        }
    	}catch(Exception e){
//    		e.printStackTrace();
    	}finally{
    		try{
		        bis.close();
		        fis.close();
    		}catch(Exception e2){
    			
    		}
    	}
    }

	/**
	 * Creates the HTML code using the given template and the photos from the album.
	 * */
	public void buildTemplateForAlbum(){
		
		AlbumFeed feed = getFeedForAlbum();
		
		StringBuffer result = new StringBuffer("");	
		String template = "";		
		
		if (settings.getHtmlType() == 8){
			template = TEMPLATE_TYPE_7;
		}else{
			template = templates.get(settings.getHtmlType());
		}
		
		String out = template; // reset after every photo
		int colsNum = settings.getTableColsNum(); 
		int columnIndex = 1;
		int counter = 0; // use for template 10
		
		if (settings.getHtmlType() == 8 || settings.getHtmlType() == 10){
			result.append(TABLE_TEMPLATE_I);// open table
		}
		
		if (settings.getHtmlType() == 21){
			// because pictures are displayed inline, the post alignment is affected
			result.append(DIV_CLEAR);
		}	
		
		for(PhotoEntry photo : feed.getPhotoEntries()) {
			
			String imgUrl = photo.getMediaContents().get(0).getUrl();
			String text = photo.getDescription().getPlainText().trim();
			String imgName = getImgNameFromUrl(imgUrl, ".jpg");
			
			if (text.equals("")){
				text = settings.getDefaultText(); // default value 
			}
			
			if (settings.getHtmlType() == 8 || settings.getHtmlType() == 10){
				if (columnIndex == 1){
					result.append(TABLE_TEMPLATE_ROW_I);// open row
				}				
			}
			
			if (settings.getHtmlType() == 8){
				result.append(TABLE_TEMPLATE_COL_I); // open cell				
			}
			
			if (settings.getHtmlType() != 21 && settings.getHtmlType() != 8){
				result.append("\n");
			}
			
			out = out.replace("__COUNT__", ++counter +"");
			out = out.replace("__TEXT__", text);
			out = out.replace("__IMG_NAME__", imgName);
			out = out.replace("__LINK_MAX__", getLinkForSize(imgUrl, settings.getLightboxLinkMax()));
			out = out.replace("__LINK__", getLinkForSize(imgUrl, settings.getPhotoDownloadSize()));
			out = out.replace("__SIZE__", settings.getPhotoDownloadSize() +"");
			
			if (settings.getHtmlType() == 21){
				out = out.replace("__REL__", settings.getLightboxRel());
				out += " "; // these images will be inline separate by space
			}
			
			//out = out.replace("\n", "");

			result.append(out);
			
			if (settings.getHtmlType() == 8){
				result.append(TABLE_TEMPLATE_COL_II); // end cell
			}
			
			if (settings.getHtmlType() == 8 || settings.getHtmlType() == 10){
				columnIndex++;
				if (columnIndex == (colsNum + 1)){
					result.append(TABLE_TEMPLATE_ROW_II);// end row
					columnIndex = 1;
				}
			}		

			out = template;
		}
		
		if (settings.getHtmlType() == 8 || settings.getHtmlType() == 10){
			
			if (columnIndex != 1){ // last row is not complete

				for (int i = columnIndex; i < colsNum; i++){
					// incomplete row case
					result.append(TABLE_TEMPLATE_COL_I); // open cell
					result.append(TABLE_TEMPLATE_COL_II); // end cell
				}
				result.append(TABLE_TEMPLATE_ROW_II);// end row
			}
						
			result.append(TABLE_TEMPLATE_II); // end table
		
		} else if (settings.getHtmlType() == 21){
			result.append(DIV_CLEAR);
		}

		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("out.txt"), StandardCharsets.UTF_16));
			writer.write(result.toString());
			writer.close();
			System.out.println("Generate file completed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String getLinkForSize(String url, int newSize){
		
		String newUrl = "";
		
		int index = url.lastIndexOf('/');
		newUrl = url.substring(0, index);
		newUrl += "/s" + newSize;
		newUrl += url.substring(index);
		
		return newUrl;		
	}
	
	private String getImgNameFromUrl(String url, String extention){
		
		String imgName = "";
		
		int index = url.lastIndexOf('/');
		imgName = url.substring(index + 1).toLowerCase();
		index = imgName.lastIndexOf(extention);
		imgName = imgName.substring(0, index);
			
		return imgName;		
	}	
	
}
