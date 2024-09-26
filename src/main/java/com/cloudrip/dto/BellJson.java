package com.cloudrip.dto;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BellJson {
	int id=111;
	String document = "강진호 자바 빨리";
	JSONObject jsonObject = new JSONObject();
	public BellJson() {
		try {
			jsonObject.put("id",id);
			jsonObject.put("document",document);
		}catch(JSONException e) {
			e.printStackTrace();
		}
	}
	
	public JSONArray bellToJsonArray(ArrayList<BellJson> bellJsons) {
		JSONArray jsonArray = new JSONArray();
		for(BellJson bellJson : bellJsons) {
			jsonArray.put(bellJson.jsonObject);
		}
		return jsonArray;
	}
	public JSONObject bellToJsonObject(BellJson bellJson) {
		JSONObject jsonObject = bellJson.jsonObject;
		return jsonObject;
	}
}
