package me.launcher;

import next.database.maker.PackageCreator;

public class DBLauncher {
	public static void main(String[] args) throws Exception {
		PackageCreator.createTable(true, "me.model.database");
		insertTestData();
	}

	private static void insertTestData() {
		//DAO dao = new DAO();
	}
}
