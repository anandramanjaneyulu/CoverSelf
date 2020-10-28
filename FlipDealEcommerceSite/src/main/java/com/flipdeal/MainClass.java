package com.flipdeal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;

import com.flipdeal.promotions.Promotion;
import com.flipdeal.promotionsImpl.PromotionSetA;
import com.flipdeal.promotionsImpl.PromotionSetB;
import com.flipdeal.services.Services;
import com.flipdeal.servicesImpl.ServicesImpl;

public class MainClass {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Services services = new ServicesImpl();
		System.out.println("Please type 1 to promote Set A or 2 to promote Set B");
		int num = sc.nextInt();
		if (num == 1) {
			Promotion setA = new PromotionSetA();
			JSONArray jsonArray = setA.calcuteDiscount();
			System.out.println(jsonArray);
			services.createOutputJSONFile(jsonArray);
		} else {
			Promotion setB = new PromotionSetB();
			JSONArray jsonArray = setB.calcuteDiscount();
			System.out.println(jsonArray);
			services.createOutputJSONFile(jsonArray);

		}

	}

}
