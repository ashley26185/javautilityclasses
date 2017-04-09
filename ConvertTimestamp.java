import java.time.*;
import java.sql.*;

public class ConvertTimestamp{

	public static void main(String... args){

		long ts;
		try{
		 ts = Long.parseLong(args[0]);

		Timestamp t = new Timestamp(ts*1000);
		
		LocalDateTime ldt2 = t.toLocalDateTime();
		System.out.println(ldt2);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}

