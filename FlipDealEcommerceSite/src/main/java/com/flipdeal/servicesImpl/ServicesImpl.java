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
		JSONArray jsonArray = retrieveJSONArray();
		JSONObject jsonObj = retriveRatesJSONObject();
		JSONArray jsonArray1 = new JSONArray();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObj1 = (JSONObject) jsonArray.get(i);
			if (!jsonObj1.get("currency").equals("INR")) {
				Double foreignPrice = (Double) jsonObj.get((String) jsonObj1.get("currency"));
				Double indianPrice = (Double) jsonObj.get("INR");
				Double price = (Double) jsonObj1.getDouble("price");
				Double convertedPrice;
				convertedPrice = ((indianPrice * price) / foreignPrice);
				convertedPrice = (double) (Math.round(convertedPrice * 100) / 100);
				jsonObj1.put("currency", "INR");
				jsonObj1.put("price", convertedPrice);

			}
			jsonArray1.put(i, jsonObj1);

		}
		return jsonArray1;

	}

	public JSONObject retriveRatesJSONObject() {
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

				JSONObject jsonObj = new JSONObject(response.toString());
				JSONObject jsonObj1 = jsonObj.getJSONObject("rates");
				return jsonObj1;
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

				JSONArray jsonArray = new JSONArray(json);
				return jsonArray;
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


