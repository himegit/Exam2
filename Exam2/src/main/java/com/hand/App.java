package com.hand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {

		System.out.print("请输入Customer ID:");

		Scanner sc = new Scanner(System.in);

		int id = sc.nextInt();

		ResultSet customerResult = selectCustomer(id);
		try {
			while (customerResult.next()) {
				System.out.print(customerResult.getString(3) + ".");
				System.out.println(customerResult.getString(4) + " 租用的Film->");
			}
			selectFilm(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "123456");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static ResultSet selectCustomer(int id) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("select * from customer where customer_id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void selectFilm(int id) throws SQLException {
		PreparedStatement ps = getConnection()
				.prepareStatement("select inventory_id from rental where customer_id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		
		
		while (rs.next()) {
			 PreparedStatement ps2 = getConnection()
			 .prepareStatement("select film_id from inventory where inventory_id =?");
			 ps2.setInt(1, rs.getInt(1));
			 
		}
//		System.out.println(rs.getInt(1));

//		ResultSet rss = null;
//		for (int j = 0; j < a.length; j++) {
//			PreparedStatement ps2 = getConnection()
//					.prepareStatement("select film_id from inventory where inventory_id =?");
//			ps2.setInt(1, a[j]);
//			rss = ps2.executeQuery();
//			System.out.println(rss.getInt(1));
//		}

		// List<ResultSet> rs3 = null;
		//
		// for (Integer list : film_id) {
		// PreparedStatement ps3 = getConnection().prepareStatement("select
		// title from film where film_id = ?");
		// ps3.setInt(1, list);
		// rs3.add(ps3.executeQuery());
		// }
		//
		// for (ResultSet rs5 : rs3) {
		// System.out.println(rs5.getString(2));
		// }

	}

}
