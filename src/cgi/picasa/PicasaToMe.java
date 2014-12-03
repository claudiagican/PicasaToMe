package cgi.picasa;

import jaco.proxy.ProxyUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.util.AuthenticationException;

/**
 * Main class that initialise the connection to Picasa album and start reading data.
 * */
public class PicasaToMe {

	public static void main(String[] args) {
		
		Settings settings = readSettingsFromINIFile();
		
		PicasawebService picasaService = createPicasaService(settings);
		
		PicasaOperations operations = new PicasaOperations(picasaService, settings);
		operations.downloadAlbum();
		//operations.buildTemplateForAlbum();
		//operations.listAlbums();		
	}
	
	protected static PicasawebService createPicasaService(Settings bo){
		
		PicasawebService picasawebService = new PicasawebService("Access albums");
		try {
			picasawebService.setUserCredentials(bo.getPicasaUser(), bo.getPicasaPassword());
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		return picasawebService;
	}

	private static Settings readSettingsFromINIFile() {
		Settings settings = new Settings();
	
		 try{
			  FileInputStream fstream = new FileInputStream(Constants.INI_FILE);
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  Map<String, String> map = new HashMap<String, String>();
			  String strLine;
			  
			  
			  while ((strLine = br.readLine()) != null)   {
				  
				  if (strLine.startsWith("#")); // ignore comments
				  else if (strLine.trim().equals("")); // ignore empty lines
				  else{
					  int indexOfEqual = strLine.indexOf("=");
					  if (indexOfEqual == -1) break; // ignore lines without = 
					  String property = strLine.substring(0, indexOfEqual).trim();
					  String value =  strLine.substring(indexOfEqual+ 1, strLine.length()).trim();
					  map.put(property, value);
				  }
			  }
			  in.close();
			  
			  for (Map.Entry<String, String> entry : map.entrySet()){
				
				  String key = entry.getKey();
				  String value = entry.getValue();
				  if (key.equalsIgnoreCase("IS_PROXY")){
					 if (value.equalsIgnoreCase("Y"))
						 settings.setProxy(true);
					 else 
						 settings.setProxy(false);
				  }else if (key.equalsIgnoreCase("PROXY_HOST")){
					  settings.setProxyHost(value);
				  }else if (key.equalsIgnoreCase("PROXY_PORT")){
					  settings.setProxyPort(value);
				  }else if (key.equalsIgnoreCase("PROXY_USER")){
					  settings.setProxyUser(value);
				  }else if (key.equalsIgnoreCase("PROXY_PASSWORD")){
					  settings.setProxyPassword(value);
				  }else if (key.equalsIgnoreCase("PICASA_USER")){
					  settings.setPicasaUser(value);
				  }else if (key.equalsIgnoreCase("PICASA_PASSWORD")){
					  settings.setPicasaPassword(value);
				  }else if (key.equalsIgnoreCase("PICASA_ALBUM_ID")){
					  settings.setPicasaAlbumId(value);
				  }else if (key.equalsIgnoreCase("PHOTO_DOWNLOAD_SIZE")){
					  settings.setPhotoDownloadSize(Integer.parseInt(value));
				  }else if (key.equalsIgnoreCase("PHOTO_START_INDEX")){
					  settings.setPhotoStartIndex(Integer.parseInt(value));
				  }else if (key.equalsIgnoreCase("PHOTO_MAX_RESULTS")){
					  settings.setPhotoMaxResults(Integer.parseInt(value));
				  }else if (key.equalsIgnoreCase("LIGHTBOX_REL")){
					  settings.setLightboxRel(value);
				  }else if (key.equalsIgnoreCase("LIGHTBOX_LinkMax")){
					  settings.setLightboxLinkMax(Integer.parseInt(value));
				  }else if (key.equalsIgnoreCase("DEFAULT_TEXT")){
					  settings.setDefaultText(value);
				  }else if (key.equalsIgnoreCase("TABLE_COLS_NUM")){
					  settings.setTableColsNum(Integer.parseInt(value));
				  }else if (key.equalsIgnoreCase("TEMPLATE_TYPE")){
					  settings.setHtmlType(Integer.parseInt(value));
				  }
			  }
			  
			if (settings.isProxy()){
				//runSettingsForProxy(bo);
				ProxyUtils.setProxy(settings.getProxyHost(), new Integer(settings.getProxyPort()).intValue(), settings.getProxyUser(), settings.getProxyPassword());
			}
			  
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		
		return settings;
	}

	private static void runSettingsForProxy(Settings bo) {
		final String host = bo.getProxyHost();
		final String port = bo.getProxyPort();
		final String username = bo.getProxyUser();
		final String password = bo.getProxyPassword();

		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", host);
		System.getProperties().put("proxyPort", port);

		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password.toCharArray());
			}
		});	
	}
	
}
