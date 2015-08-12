package com.hand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSeparatorUI;

/**
 * Hello world!
 *
 */
public class App {

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

	public static void main(String[] args) {
		System.out.print("请输入城市ID：");
		Scanner sc = new Scanner(System.in);

		int id = sc.nextInt();
		ResultSet countryResult = selectCountry(id);
		ResultSet cityResult = null;
		String city = null;

		try {
			while (countryResult.next()) {
				System.out.println("Country " + countryResult.getString(2) + "的城市");
			}
			System.out.println("城市ID|城市名称");

			cityResult = selectCity(id);

			while (cityResult.next()) {
				System.out.print(cityResult.getInt(1) + "   |");
				System.out.print(cityResult.getString(2));
				System.out.println();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static ResultSet selectCountry(int id) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("select * from country where country_id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static ResultSet selectCity(int id) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("select * from city where country_id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

}
