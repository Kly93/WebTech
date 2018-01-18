package de.hdm.weblog.db;

import de.hdm.weblog.BlogAdministration;

public class InitTables {

	public static void main(String[] args) {

		KommentarMapper.removeTable();
		BlogeintragMapper.removeTable();
		TextbeitragMapper.removeTable();
		PersonMapper.removeTable();
		
		PersonMapper.createTable();
		TextbeitragMapper.createTable();
		BlogeintragMapper.createTable();
		KommentarMapper.createTable();
		

	}

}
