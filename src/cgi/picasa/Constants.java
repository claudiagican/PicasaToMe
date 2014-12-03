package cgi.picasa;

import java.util.HashMap;

/**
 * Class for various constants, including HTML templates.
 * */
public class Constants {

	static final String INI_FILE = "./PicasaToMe.ini";
	static final String PICASA_URL_BASE = "https://picasaweb.google.com/data/feed/api/user/";
	
	static final String DIV_CLEAR = "<div style=\"clear:both\"></div>";

	//0 : + link + title + alt
	static final String TEMPLATE_TYPE_0 = "<p style=\"text-align: center;\"><em>__TEXT__</em><img class=\"aligncenter\" title=\"__TEXT__\" src=\"__LINK__\" alt=\"__TEXT__\" width=\"__SIZE__\" /></p>";
	//1 : + caption - link + title + alt
	static final String TEMPLATE_TYPE_1 = "[caption align=\"aligncenter\" width=\"__SIZE__\" caption=\"__TEXT__\"] <img title=\"__TEXT__\" src=\"__LINK__\" alt=\"__TEXT__\" width=\"__SIZE__\" />[/caption]";
	//2 : + caption + link + title + alt
	static final String TEMPLATE_TYPE_2 = "[caption align=\"aligncenter\" width=\"__SIZE__\" caption=\"__TEXT__\"]<a href=\"__LINK_MAX__\" target=\"_blank\"><img title=\"__TEXT__\" src=\"__LINK__\" alt=\"__TEXT__\" width=\"__SIZE__\" /></a>[/caption]";
	//3 : + text + type 1 
	static final String TEMPLATE_TYPE_3 = "<p style=\"text-align: justify;\"><strong>__TEXT__</strong></p>[caption align=\"aligncenter\" width=\"__SIZE__\" caption=\"__TEXT__\"] <img title=\"__TEXT__\" src=\"__LINK__\" alt=\"__TEXT__\" width=\"__SIZE__\" />[/caption]";
	//4 : + text + type 2
	static final String TEMPLATE_TYPE_4 = "<p style=\"text-align: justify;\"><strong>__TEXT__</strong></p>[caption align=\"aligncenter\" width=\"__SIZE__\" caption=\"__TEXT__\"]<a href=\"__ALBUMLINK__\" target=\"_blank\"><img title=\"__TEXT__\" src=\"__LINK__\" alt=\"__TEXT__\" width=\"__SIZE__\" /></a>[/caption]";
	//5 : + caption + link - title - alt
	static final String TEMPLATE_TYPE_5 = "[caption align=\"aligncenter\" width=\"__SIZE__\" caption=\"__TEXT__\"]<a href=\"__LINK_MAX__\" target=\"_blank\"><img title=\"SUN\" src=\"__LINK__\" alt=\"SUN\" width=\"__SIZE__\" /></a>[/caption]";
	//6 : + caption + link - title - alt + start index + max results
	static final String TEMPLATE_TYPE_6 = TEMPLATE_TYPE_5; 
	//7 : text + link + title + alt
	static final String TEMPLATE_TYPE_7 =
			"<strong>__TEXT__</strong>" +
			"<a href=\"__LINK_MAX__\" " +
				"target=\"_blank\">" +
				"<img class=\"aligncenter\" " +
					"title=\"__TEXT__\" " +
					"src=\"__LINK__\" " +
					"alt=\"__TEXT__\" " +
					"width=\"__SIZE__\" />" +
			"</a>";
	//8 : table with cells of type 7
	static final String TEMPLATE_TYPE_8 = "";
	//9 : link + title + alt + text-dummy
	static final String TEMPLATE_TYPE_9 = "<p style=\"text-align: center;\"><img class=\"aligncenter\" title=\"__TEXT__\" src=\"__LINK__\" alt=\"__TEXT__\" width=\"__SIZE__\" /><em>__TEXTDUMMY__</em></p>";


	static final String TABLE_TEMPLATE_I 		= "<table style=\"table-layout: fixed;\"><colgroup><col width=\"50%\"></col><col width=\"50%\"></col></colgroup><tbody>";
	static final String TABLE_TEMPLATE_ROW_I 	= "\n<tr valign=\"top\" align=\"left\">";
	static final String TABLE_TEMPLATE_COL_I 	= "\n<td>";
	static final String TABLE_TEMPLATE_COL_II 	= "\n</td>";
	static final String TABLE_TEMPLATE_ROW_II 	= "\n</tr>";	
	static final String TABLE_TEMPLATE_II 		= "\n</tbody></table>";
	
	static final String TEMPLATE_TYPE_10 = 

		"\n<td id=\"__IMG_NAME__\">" +
			"\n<a href=\"__LINK_MAX__\" " + "target=\"_blank\">" + 
				"<img class=\"alignleft\" " +
					"src=\"__LINK__\" " +
					"alt=\"__TEXT__\" " +
					"title=\"__TEXT__\" " +			
					"width=\"__SIZE__\" />" +
			"</a>" + 
			"\n<a href=\"#__IMG_NAME__\">__COUNT__</a>.Denumire științifică: <strong>__TEXT__</strong>" +
			"\nPopular: -" + 
			"\nInfo: -" +
		"\n</td>";
	
	//21 : linkMax + rel + imgLinkMin + title + alt (+ alignLeft, rel="lightbox[x]") 
	static final String TEMPLATE_TYPE_21 =
			"<a href=\"__LINK_MAX__\" " +
				"rel=\"lightbox[__REL__]\" " +
				"target=\"_blank\"> " +
				"<img class=\"alignleft\" " +
					"title=\"__TEXT__\" " +
					"src=\"__LINK__\" " +
					"alt=\"__TEXT__\" " +
					"width=\"__SIZE__\" />" +
			"</a>";
	
	static final HashMap<Integer, String> templates = new HashMap<Integer, String>();
	
	static {
		templates.put(0, TEMPLATE_TYPE_0);
		templates.put(1, TEMPLATE_TYPE_1);
		templates.put(2, TEMPLATE_TYPE_2);
		templates.put(3, TEMPLATE_TYPE_3);
		templates.put(4, TEMPLATE_TYPE_4);
		templates.put(5, TEMPLATE_TYPE_5);
		templates.put(6, TEMPLATE_TYPE_6);
		templates.put(7, TEMPLATE_TYPE_7);
		templates.put(8, TEMPLATE_TYPE_8);
		templates.put(9, TEMPLATE_TYPE_9);
		templates.put(10, TEMPLATE_TYPE_10);
		templates.put(21, TEMPLATE_TYPE_21);
    }
	
}
