package com.flipdeal.promotionsImpl;

import org.json.JSONArray;
import org.json.JSONObject;

import com.flipdeal.promotions.Promotion;
import com.flipdeal.servicesImpl.ServicesImpl;

public class PromotionSetB implements Promotion {
	public JSONArray calcuteDiscount() {
		ServicesImpl services = new ServicesImpl();
		JSONArray jsonObjArray = services.modifyAllCurrenciesToINR();
		for (int i = 0; i < jsonObjArray.length(); i++) {
			JSONObject jsonObj = (JSONObject) jsonObjArray.get(i);
			Double price = (Double) jsonObj.getDouble("price");
			Integer inventory = (Integer) jsonObj.get("inventory");
			  
	        Double discount = 0.00;
			Double maxDiscount = 0.00;
			Double percentage = 0.00;
			Double maxPercentage = 0.00;
      
	        if(i == 4)
	        {
	        String  arrival = jsonObj.getString("arrival") ;
	        if(arrival.equals("NEW"))
			{
				discount = (price * 7.00) / 100;
				percentage = 7.00;
				if (maxDiscount < discount) {
					maxDiscount = discount;
					maxPercentage = percentage;
				}
				
			 }
	       }
			if (inventory > 20) {
				discount = (price * 12) / 100;
				percentage = 12.00;
				if (maxDiscount < discount) {
					maxDiscount = discount;
					maxPercentage = percentage;
				}

			}
			
			if (maxDiscount == 0.00) {
				if (price > 1000) {
					maxDiscount = (price * 2) / 100;
					maxPercentage = 2.00;
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
		return jsonObjArray;

	}
}
