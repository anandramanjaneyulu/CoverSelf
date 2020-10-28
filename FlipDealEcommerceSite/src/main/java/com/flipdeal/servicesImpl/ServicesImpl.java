package com.flipdeal.servicesImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.flipdeal.services.Services;

public class ServicesImpl implements Services {

	public JSONArray modifyAllCurrenciesToINR() {
		JSONArray objJSONArray = retrieveJSONArray();
		JSONObject objJSON = retriveJSONObject();
		JSONArray jsonObjArray = new JSONArray();
		for (int i = 0; i < objJSONArray.length(); i++) {
			JSONObject objJSON1 = (JSONObject) objJSONArray.get(i);
			if (!objJSON1.get("currency").equals("INR")) {
				Double foreignPrice = (Double) objJSON.get((String) objJSON1.get("currency"));
				Double indianPrice = (Double) objJSON.get("INR");
				Double price = (Double) objJSON1.getDouble("price");
				Double convertedPrice;
				convertedPrice = ((indianPrice * price) / foreignPrice);
				convertedPrice = (double) (Math.round(convertedPrice * 100) / 100);
				objJSON1.put("currency", "INR");
				objJSON1.put("price", convertedPrice);

			}
			jsonObjArray.put(i, objJSON1);

		}
		return jsonObjArray;

	}

	public JSONObject retriveJSONObject() {
		try {
			URL obj = new URL("https://api.exchangeratesapi.io/latest");
			HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.addRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = httpConnection.getResponseCode();

			if (responseCode == 200) {

				BufferedReader responseReader = new BufferedReader(
						new InputStreamReader(httpConnection.getInputStream()));

				String responseLine;
				StringBuffer response = new StringBuffer();

				while ((responseLine = responseReader.readLine()) != null) {
					response.append(responseLine + "\n");
				}
				responseReader.close();

				// print result

				String json = response.toString();

				JSONObject objJSON = new JSONObject(response.toString());
				JSONObject objJSON1 = objJSON.getJSONObject("rates");
				return objJSON1;
			} else {
				System.out.println("Get Not Worked");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public JSONArray retrieveJSONArray() {
		try {
			URL obj = new URL("https://api.jsonbin.io/b/5d31a1c4536bb970455172ca/latest");
			HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();

			httpConnection.setRequestMethod("GET");
			httpConnection.addRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = httpConnection.getResponseCode();

			if (responseCode == 200) {

				BufferedReader responseReader = new BufferedReader(
						new InputStreamReader(httpConnection.getInputStream()));

				String responseLine;
				StringBuffer response = new StringBuffer();

				while ((responseLine = responseReader.readLine()) != null) {
					response.append(responseLine + "\n");
				}
				responseReader.close();

				// print result

				String json = response.toString();

				JSONArray objJSONArray = new JSONArray(json);
				return objJSONArray;
			} else {

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createOutputJSONFile(JSONArray jsonArray) {
		try {

			// Writing to a file
			File file = new File("target/output.json");
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);

			fileWriter.write(jsonArray.toString());
			fileWriter.flush();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
