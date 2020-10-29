package com.flipdeal.promotionsImpl;

import org.json.JSONArray;
import org.json.JSONObject;

import com.flipdeal.promotions.Promotion;
import com.flipdeal.services.Services;
import com.flipdeal.servicesImpl.ServicesImpl;

public class PromotionSetA implements Promotion {

	public JSONArray calcuteDiscount() {
		Services services = new ServicesImpl();
		JSONArray jsonArray = services.modifyAllCurrenciesToINR();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArray.get(i);
			String origin = (String) jsonObj.get("origin");
			Float rating = (Float) jsonObj.getFloat("rating");
			Double price = jsonObj.getDouble("price");
			String category = jsonObj.getString("category");
			Integer inventory = (Integer) jsonObj.get("inventory");

			Double discount = 0.00;
			Double maxDiscount = 0.00;
            Double percentage = 0.00;
            Double maxPercentage = 0.00;
			

		
			if (origin.equals("Africa")) {
				percentage = 7.00;
				discount = (price * percentage) / 100;
				
				if (maxDiscount < discount) {
					maxDiscount = discount;
					maxPercentage = percentage;
				}
			}
			if (rating == 2) {
				
				percentage = 4.00;
				discount = (price * percentage) / 100;
				if (maxDiscount < discount) {
					maxDiscount = discount;
					maxPercentage = percentage;
				}

			}
			if (rating < 2) {
				
				percentage = 8.00;
				discount = (price * percentage) / 100;
				if (maxDiscount < discount ) {
					maxDiscount = discount;
					maxPercentage = percentage;
				}

			}

			if (category.equals("electronics") || category.equals("furnishing")) {
				if (price >= 500) {
					discount = 100.00;
					percentage = (discount / price) * 100;
					if (maxDiscount < discount) {
						maxDiscount = discount;
						maxPercentage = percentage;
					}

				}
			}
			if (maxDiscount == 0.00) {
				if (price > 1000) {
			    maxPercentage = 2.00;
			    maxDiscount = (price * maxPercentage) / 100;

				}
			}
			if (maxDiscount != 0.00) {
				maxDiscount = (double) Math.round(maxDiscount * 100) / 100;
				maxPercentage = (double) Math.round(maxPercentage * 100) / 100;
				StringBuilder str = new StringBuilder();
				str.append("get " + maxPercentage + "% off");
				JSONObject discount1 = new JSONObject();
				discount1.put("amount", maxDiscount);
                discount1.put("discountTag", str.toString());
				jsonObj.put("discount", discount1);
			}

		}
		return jsonArray;

	}

}
