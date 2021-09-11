package application;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class aboutclass {
	public static String getDateTimeAsString(LocalDateTime localDateTime,String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return localDateTime.format(formatter);
		}
	public static LocalDateTime parseStringToDateTime(String time,String format) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.parse(time,df);
		}

	public static void setclass() {//寻找所有学生时间，对超过45分钟的人（一节课)作重置
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/test";
		String uid = "root";
		String pwd = "root";
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		try {
//			Class.forName(driver);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			conn = DriverManager.getConnection(url, uid, pwd);
			String sql = "select number,time from student";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				
				String num = rs.getString("number");
				String tim= rs.getString("time");
				LocalDateTime time2=LocalDateTime.now();
				LocalDateTime time3=parseStringToDateTime(tim,"yyyy-MM-dd HH:mm:ss");
				Duration duration = Duration.between(time3,time2);
				long minutes = duration.toMinutes();//两者时间差
				if(minutes>=45)
				{
				try {
					conn = DriverManager.getConnection(url, uid, pwd);
					String sql1 = "update student set login='否   ' where number=?";//重置处理
					pstmt = conn.prepareStatement(sql1);
					pstmt.setString(1, num);
					int count = pstmt.executeUpdate();
					if (count == 1) {
						System.out.println("更新成功！");
					} else {
						System.out.println("更新失败！");
					}
				}
				 catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
				}
				}
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
		 finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}


}
}