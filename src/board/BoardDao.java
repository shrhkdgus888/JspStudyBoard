package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.JdbcUtil;

//DB����
public class BoardDao {
	private JdbcUtil ju;
	//BoardDao�� ������ �ɶ�, ju�� JdbcUtil.getInstance()�� �Ѵ�.
	public BoardDao() {
		ju = JdbcUtil.getInstance();
	}
	
	//����(C)
		//BoardVo���� ���޹޾Ƽ� �����͸� �����ϴ� ���
	public int insert(BoardVo vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		//������ ��, ����� ������
		//������ �����Ͱ� �ƴ�, Vo�κ��� ���޹��� �����Ͱ� ���� ������ ���ε� ����ó��
		String qurey = "insert into \"BOARD\" (\"NUM\", \"TITLE\",\"WRITER\",\"CONTENT\",\"REGDATE\",\"CNT\") values (\"BOARD_SEQ\".nextval, ?, ?, ?, sysdate, 0)";
		//������ ������ ���н� ���� Ȯ���ϱ� ���� �� ����(������ ���������� �۵������� ret = 1;)
		int ret = -1; 
		try {
			//con���� jdbcutil�� ���� getConnection ��.
			con = ju.getConnection();
			//con���κ��� PreparedStatement(query)�� pstmt������ ������.
			pstmt = con.prepareStatement(qurey);
			//ù��° �Ķ���Ϳ��� vo���� getTitle�� ������.
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getContent());
			//1��(���ڰ�,int)�� ������� ������ ����.
			ret = pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			if(con !=null) {
				try {
					con.close(); //Ǯ�� ��ȯ
					}
						catch(SQLException e) {
							e.printStackTrace();
						}
			}
			if(pstmt !=null) {
				try {
					pstmt.close(); //Ǯ�� ��ȯ
					}
						catch(SQLException e) {
							e.printStackTrace();
						}
			}
		}
		//����� ��ȯ
		return ret;
	}
	//��ü��ȸ(R)
		//boardVo(DB��)�� ���� List�� ��ȯ�ϴ� selectAll()�̶�� �Լ�
		//��� ������ �޶�� �Լ�(��ü�����ȸ)�̱� ������, �Ķ���Ͱ��� ���� �ʿ����� ����.
	public List<BoardVo> selectAll(){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		//�ֽŰԽñ� ������ ���� �� order by \"NUM\" desc
		String query = "select \"NUM\", \"TITLE\", \"WRITER\", \"CONTENT\", \"REGDATE\", \"CNT\" from \"BOARD\" order by \"NUM\" desc";
		//query�� ����� List�� ��������� �ϱ⶧���� ArrayList �����.
		ArrayList<BoardVo> ls = new ArrayList<BoardVo>();
		try {
			con = ju.getConnection();
			stmt = con.createStatement();
			//������ ������ �����ϴ� �Լ�(stmt.executeQuery(query);)�� rs�� ��´�.
			rs = stmt.executeQuery(query);
			//��ȸ��(=rs) ���ڵ� ����ŭ �ݺ��Ѵ�.
			while(rs.next()) {
				//BoardVo�� ���� �ϳ��� ��´�.(BoardVo �����ڸ� ���� ���� : �����ڷ� �Ʒ�ó�� ������ ��������)
				BoardVo vo = new BoardVo(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						new Date(rs.getDate(5).getTime()),
						rs.getInt(6));
				//vo������ ls(list)�� ����ش�.
				ls.add(vo);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				if(rs !=null) {
					try {
						rs.close(); //Ǯ�� ��ȯ
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
				if(con !=null) {
					try {
						con.close(); //Ǯ�� ��ȯ
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
				if(stmt !=null) {
					try {
						stmt.close(); //Ǯ�� ��ȯ
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
			}
		//����� ��ȯ
			return ls;
		}
	//�ϳ��� ��ȸ(R)	
	public BoardVo selectOne(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		Ȯ���Ұ� String query = "select \"NUM\", \"TITLE\", \"WRITER\", \"CONTENT\", \"REGDATE\", \"CNT\" from \"BOARD\" where \"NUM\"=?";
		String query = "select * from \"BOARD\" where \"NUM\"=?";
		
		BoardVo vo = null;
		try {
			con = ju.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, num);
			//������ ������ �����ϴ� �Լ�(pstmt.executeQuery();)�� rs�� ��´�.
			updateCnt(num); //��ȸ�� ���� , ������ executeQuery�� ������ ���� �̸� ��ȸ���� ������Ų��.
			rs = pstmt.executeQuery();
			//��ȸ��(=rs)���� �ִٸ�, vo�� ������ �ȴ�.
			if(rs.next()) {
				//vo = ��ȯ�Ǵ� ��
				vo = new BoardVo(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						new Date(rs.getDate(5).getTime()),
						rs.getInt(6));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				if(rs !=null) {
					try {
						rs.close(); //Ǯ�� ��ȯ
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
				if(con !=null) {
					try {
						con.close(); //Ǯ�� ��ȯ
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
				if(pstmt !=null) {
					try {
						pstmt.close(); //Ǯ�� ��ȯ
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
			}
		//����� ��ȯ
			return vo;
		}
		
	
	
	//����(U)
	public int update(BoardVo vo) {
	Connection con = null;
	PreparedStatement pstmt = null;
	//������ ��, ����� ������
	//������ �����Ͱ� �ƴ�, Vo�κ��� ���޹��� �����Ͱ� ���� ������ ���ε� ����ó��
	String qurey = "update \"BOARD\" set \"TITLE\"=?, \"CONTENT\"=? where \"NUM\"=?";
	//������ ������ ���н� ���� Ȯ���ϱ� ���� �� ����(������ ���������� �۵������� ret = 1;)
	int ret = -1; 
	try {
		//con���� jdbcutil�� ���� getConnection ��.
		con = ju.getConnection();
		//con���κ��� PreparedStatement(query)�� pstmt������ ������.
		pstmt = con.prepareStatement(qurey);
		//ù��° �Ķ���Ϳ��� vo���� getTitle�� ������.
		pstmt.setString(1, vo.getTitle());
		pstmt.setString(2, vo.getContent());
		pstmt.setInt(3, vo.getNum());
		//1��(���ڰ�,int)�� ������� ������ ����.
		ret = pstmt.executeUpdate();
		
	}catch(SQLException e){
		e.printStackTrace();
	}finally {
		if(con !=null) {
			try {
				con.close(); //Ǯ�� ��ȯ
				}
					catch(SQLException e) {
						e.printStackTrace();
					}
		}
		if(pstmt !=null) {
			try {
				pstmt.close(); //Ǯ�� ��ȯ
				}
					catch(SQLException e) {
						e.printStackTrace();
					}
		}
	}
	//����� ��ȯ
	return ret;
		
	}
	
	//��ȸ�� ����(U)
		public int updateCnt(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		//������ ��, ����� ������
		//������ �����Ͱ� �ƴ�, Vo�κ��� ���޹��� �����Ͱ� ���� ������ ���ε� ����ó��
		//update \"BOARD\" set \"CNT\"=\"CNT\"+1 where \"NUM\"=? ��ȸ�� ���� ����
		String qurey = "update \"BOARD\" set \"CNT\"=\"CNT\"+1 where \"NUM\"=?";
		//������ ������ ���н� ���� Ȯ���ϱ� ���� �� ����(������ ���������� �۵������� ret = 1;)
		int ret = -1; 
		try {
			//con���� jdbcutil�� ���� getConnection ��.
			con = ju.getConnection();
			//con���κ��� PreparedStatement(query)�� pstmt������ ������.
			pstmt = con.prepareStatement(qurey);
			//� �Խñ������� �����ϴ� ���� ����
			pstmt.setInt(1, num);
			//1��(���ڰ�,int)�� ������� ������ ����.
			ret = pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			if(con !=null) {
				try {
					con.close(); //Ǯ�� ��ȯ
					}
						catch(SQLException e) {
							e.printStackTrace();
						}
			}
			if(pstmt !=null) {
				try {
					pstmt.close(); //Ǯ�� ��ȯ
					}
						catch(SQLException e) {
							e.printStackTrace();
						}
			}
		}
		//����� ��ȯ
		return ret;
			
		}
		
		//����(D)
		public int delete(int num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			//������ ��, ����� ������
			//������ �����Ͱ� �ƴ�, Vo�κ��� ���޹��� �����Ͱ� ���� ������ ���ε� ����ó��
			String qurey = "delete from \"BOARD\" where \"NUM\"=?";
			//������ ������ ���н� ���� Ȯ���ϱ� ���� �� ����(������ ���������� �۵������� ret = 1;)
			int ret = -1; 
			try {
				//con���� jdbcutil�� ���� getConnection ��.
				con = ju.getConnection();
				//con���κ��� PreparedStatement(query)�� pstmt������ ������.
				pstmt = con.prepareStatement(qurey);
				pstmt.setInt(1, num);
				//1��(���ڰ�,int)�� ������� ������ ����.
				ret = pstmt.executeUpdate();
				
			}catch(SQLException e){
				e.printStackTrace();
			}finally {
				if(con !=null) {
					try {
						con.close(); //Ǯ�� ��ȯ
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
				if(pstmt !=null) {
					try {
						pstmt.close(); //Ǯ�� ��ȯ
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
			}
			//����� ��ȯ
			return ret;
				
			}
	
	
}
	
    
	



