package fr.qinder.conf;

import android.app.Application;

public class Configurations extends Application {

	// *********************************************************
	// List of configurations of the project
	// *********************************************************

	// General
	public static String UserName = "Anonyme";

	// API General
	public static String Schema = "http";
	public static String Host = "nancy.agoraa.bigint.fr";

	// API Pages
	public static String UrlThemes = "/themes";
	public static String UrlSlideshows = "/actualite/alaune/slideshows";
	public static String UrlAllActu = "/actualite/allactu";
	public static String UrlMedias = "/medias/themes";
	public static String UrlParcours = "/parcours/themes";
	public static String UrlSearch = "/search";

	private static Configurations Singleton;

	public static Configurations getInstance() {
		return Singleton;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Singleton = this;
	}
}