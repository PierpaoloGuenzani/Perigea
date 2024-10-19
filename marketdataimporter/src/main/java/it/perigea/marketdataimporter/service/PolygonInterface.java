package it.perigea.marketdataimporter.service;

/**
 * An interface to store all polygon constant
 */
public interface PolygonInterface
{
	public static final String POLYGON_SCHEME = "https";
	public static final String POLYGON_HOST = "api.polygon.io";
	public static final String POLYGON_DAILY_PATH = "/v2/aggs/grouped/locale/global/market/fx/{date}";
	public static final String POLYGON_DAILY_URL 
	= "https://api.polygon.io/v2/aggs/grouped/locale/global/market/fx/{date}";
}
