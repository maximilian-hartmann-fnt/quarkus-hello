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

@Path("/racebase")
public class DBResource {

    @Inject
    DataSource defaultDataSource;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        setTables(defaultDataSource);
        return getUserWithID(1, defaultDataSource);
    }

    public static void setTables(DataSource defaultDataSource){
        Connection conn = null;
		Statement stmt = null;
		ResultSet rslt = null;

		try
		{
			conn = defaultDataSource.getConnection();
			stmt = conn.createStatement();

			String table_people = "CREATE TABLE IF NOT EXISTS people(ID INT PRIMARY KEY, LAST_NAME VARCHAR(50), FIRST_NAME VARCHAR(50), AGE INT, GENDER CHAR(1), EMAIL VARCHAR(155));";
			
            stmt.executeUpdate(table_people);
            
            
		}
		catch (SQLException e)
		{
			handleException(e, null);
		}
		finally
		{
			try
		    {
                if (rslt != null)
                {
                    rslt.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
                if (conn != null)
                {
                    conn.close();
                }
		    }
            catch (SQLException e)
            {
                handleException(e, "Error while closing connection");
            }
		}

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