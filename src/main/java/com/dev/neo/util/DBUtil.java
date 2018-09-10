package com.dev.neo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class DBUtil {

	private Connection connection = null;
	Properties prop=null;

	public DBUtil(Properties properties) throws Exception{
		this.prop=properties;
		this.connection=loadDriver();
	}

	public Connection loadDriver() throws Exception{
		Connection con=null;
		try
		{ 
			Class.forName(prop.getProperty("driverName")); 
			con= DriverManager.getConnection(prop.getProperty("dbUrl"),prop.getProperty("UserName"),prop.getProperty("password"));
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;

		}
		return con; 
	}

	public  boolean insertRecords(String computername, String ipaddress, String currentUser,long physicalMemory,long freephysicalMemory,double freephysicalMemoryPercentage,long cdrivetotalspaceingb,double cdrivefreespaceingb,long ddrivetotalspaceingb,double ddrivefreespaceingb,long edrivetotalspaceingb,double edrivefreespaceingb, float cpuPercent,double cputemp,long date) throws Exception
	{
		boolean isInsert = false;
		PreparedStatement pstmt=null;
	
		try{
			String sql = "INSERT INTO Performance_Details(computername,ipaddress,currentUser,physicalMemory,freephysicalMemory,freephysicalMemoryPercentage,cdriveSpace,cdriveFreeSpace,ddriveSpace,ddriveFreeSpace,edriveSpace,edriveFreeSpace,cpuUsage,cpuTemp,date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, computername);
			pstmt.setString(2, ipaddress);
			pstmt.setString(3, currentUser);
			pstmt.setLong(4, physicalMemory);
			pstmt.setLong(5, freephysicalMemory);
			pstmt.setDouble(6, freephysicalMemoryPercentage);
			pstmt.setLong(7, cdrivetotalspaceingb);
			pstmt.setDouble(8, cdrivefreespaceingb);
			pstmt.setLong(9, ddrivetotalspaceingb);
			pstmt.setDouble(10, ddrivefreespaceingb);
			pstmt.setLong(11, edrivetotalspaceingb);
			pstmt.setDouble(12, edrivefreespaceingb);
			pstmt.setFloat(13, cpuPercent);
			pstmt.setDouble(14, cputemp);
			pstmt.setLong(15, date);

			int b=pstmt.executeUpdate();
			if(b==1){
				System.out.println("Data Inserted");
			}

		}catch(Exception ex){ 			                                
			ex.printStackTrace();
			throw ex;
		}
		finally {
			if(pstmt!=null)
			pstmt.close();
		}
		return isInsert;
	}
	
	
	public  boolean insertMailDetails(String computername, String diskdrive, long cdrivetotalspaceingb,double cdrivefreespaceingb) throws Exception
	{
		boolean isInsert = false;
		PreparedStatement pstmt=null;
	
		try{
			String sql = "INSERT INTO alert_mail_details(computername,diskdrive,cdrive_space,cdrive_free_space) VALUES(?,?,?,?)";
			pstmt = connection.prepareStatement(sql);	
			pstmt.setString(1, computername);
			pstmt.setString(2, diskdrive);
			pstmt.setLong(3, cdrivetotalspaceingb);
			pstmt.setDouble(4, cdrivefreespaceingb);
			int b=pstmt.executeUpdate();
			if(b==1){
				System.out.println("Details for Alert Mail Inserted");
			}
		}catch(Exception ex){ 			                                
			ex.printStackTrace();
			throw ex;
		}
		finally {
			if(pstmt!=null)
			pstmt.close();
		}
		return isInsert;
	}
}
