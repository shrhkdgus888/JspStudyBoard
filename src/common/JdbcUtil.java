package common;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
//26:51
public class JdbcUtil {
	private static JdbcUtil instance = new JdbcUtil();
	//context.xml���� ������ DataSource ��ü�� ����ϱ� ���� ��ü ����
	private static DataSource ds;
	
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("����̹��ε�����!");
			InitialContext ctx = new InitialContext();
			//Context������ JNDI��ο��� jdbc/TestDB�� �̸��� ���� ������ ã�ƿͼ� ds(DataSource)�� �����ϵ��� ��
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/myOracle");
			System.out.println("Connection Pool ����");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}catch(NamingException e) {
			e.printStackTrace();
			}
		}

	//51:34
	private JdbcUtil() {}
	public static JdbcUtil getInstance() {
		return instance;
	}
	public Connection getConnection() throws SQLException{
		//ds�� ���� Con�� �ʿ��Ҷ� ��ȯ��.
		return ds.getConnection();//Pool�� Ŀ�ؼ��� ��ȯ��.
	}
}
