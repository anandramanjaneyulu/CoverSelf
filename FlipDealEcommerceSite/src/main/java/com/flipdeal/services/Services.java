package com.flipdeal.services;

import org.json.JSONArray;
import org.json.JSONObject;

public interface Services {
	public JSONArray modifyAllCurrenciesToINR();

	public JSONObject retriveRatesJSONObject();

	public JSONArray retrieveJSONArray();

	public void createOutputJSONFile(JSONArray jsonArray);
	
	

}
