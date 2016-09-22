import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CustomersApp {
	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String s =null;
//		try {
//			PreparedStatement pstmt = con.prepareStatement("update Customers set streetaddress = ? city = ? state = ? zipcode =? ");
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		Scanner sc = new Scanner(System.in);
		int i= 0;
		int id = 0;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
                        //con = DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/oracle@localhost:1521:orcl");
            con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			stmt = con.createStatement();
		
		while(true)
		{
			
				
			
				

			try 
			{
				pstmt = con.prepareStatement("Select custid, title, firstname, lastname, streetaddress, city, state, zipcode, emailaddress, position, company from customers a inner join stateid b on a.stateid = b.stateid inner join company c on a.companyid = c.companyid inner join posid d on d.posid =a.posid where fullname = ?");
				pstmt2 = con.prepareStatement("Update customers set streetaddress = ?, city = ?, stateid = ?, zipcode = ? where fullname = ?");
				pstmt3 = con.prepareStatement("Select stateid from stateid where state=?");
				pstmt4 = con.prepareStatement("select * from (select count(distinct custid), count(distinct stateid), count(distinct companyid) from customers)") ;
			} 
			catch (SQLException e2) 
			{
				e2.printStackTrace();
			}	
				
			System.out.print("Enter option - 1 to search, 2 to update address of customer, 3 to display total and 4 to Quit      :");
			i = sc.nextInt();
			sc.nextLine();
			if(i==1)
			{
				System.out.print("Enter full name of customer  :");
				s = sc.nextLine();
				try 
				{
					pstmt.setString(1, s);
					pstmt2.setString(5, s);
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					System.out.println("Customer no\t: " + rs.getInt("custid"));
					System.out.printf("%s %s %s \n",rs.getString("title"), rs.getString("firstname"), rs.getString("lastname"));
					System.out.printf("%s\n",rs.getString("streetaddress"));
					System.out.printf("%s, %s %s \n",rs.getString("city"), rs.getString("state"), rs.getString("zipcode"));
					System.out.printf("%s at %s \n",rs.getString("position"), rs.getString("company"));
					
				}
			}
			
			if (i==2)
			{
				pstmt2.setString(5, s);
				System.out.print("Address\t:");
				s = sc.nextLine();
				pstmt2.setString(1, s);
				System.out.print("City\t:");
				s = sc.nextLine();
				pstmt2.setString(2, s);
				System.out.print("State\t:");
				while(true)
				{
					s = sc.nextLine();
					pstmt3.setString(1, s);
					rs = pstmt3.executeQuery();
					if(rs.next())
					{
						id = rs.getInt("stateid");
						pstmt2.setInt(3, id);
						break;
					}
					else System.out.print("State invalid please re-enter\t:");
				}
				
				System.out.print("Zip Code\t:");
				s = sc.nextLine();
				pstmt2.setString(4, s);
				int rec = pstmt2.executeUpdate();
				System.out.println( rec + " Recrods updated");
				
			}
			if(i==3)
			{
				rs = pstmt4.executeQuery();
				while(rs.next())
				{
					int count1 = rs.getInt(1);
					int count2 = rs.getInt(2);
					int count3 = rs.getInt(3);
					System.out.println("No of Customers\t No of States\tNo of Companies\n" + count1 + "\t\t" + count2 + "\t\t" + count3);
					break;
				}
			}	
			if(i==4)
			{
				System.out.println("Good idea lets take a break");
				break;
			}
		}
		}catch (SQLException e) 
		{
				e.printStackTrace();
		}catch (ClassNotFoundException e) 
		{
				e.printStackTrace();
		}finally 
		{
				try 
				{
					rs.close();
					stmt.close();
					con.close();
				}catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
  }


