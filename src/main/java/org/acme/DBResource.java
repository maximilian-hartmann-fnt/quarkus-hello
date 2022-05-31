package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.inject.Inject;

@Path("/database")
public class DBResource {

    @Inject
    DataSource defaultDataSource;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        
        return getUserWithID(1, defaultDataSource);
    }

    

    public static void handleException(Exception e, String msg)
	{
		if (msg != null)
		{
			System.err.println(msg);
		}
		System.err.println(e.getMessage());
		e.printStackTrace(System.err);
	}

    public static String getUserWithID(int ID, DataSource defaultDataSource) {
		
		Statement stmt = null;
		ResultSet rslt = null;
		String name = null;
		String check = "SELECT first_name FROM people WHERE id = " + ID;
	    
		try {
            Connection conn = defaultDataSource.getConnection();
			stmt = conn.createStatement();
			rslt = stmt.executeQuery(check);
			
			while(rslt.next()) {
				name = rslt.getString("first_name");
				
			}
			stmt.close();
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return name;
	}
}