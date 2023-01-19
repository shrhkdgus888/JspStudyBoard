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

//DB접근
public class BoardDao {
	private JdbcUtil ju;
	//BoardDao가 생성이 될때, ju에 JdbcUtil.getInstance()를 한다.
	public BoardDao() {
		ju = JdbcUtil.getInstance();
	}
	
	//삽입(C)
		//BoardVo값을 전달받아서 데이터를 삽입하는 기능
	public int insert(BoardVo vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		//삽입할 때, 사용할 쿼리문
		//정해진 데이터가 아닌, Vo로부터 전달받은 데이터가 들어가기 때문에 바인딩 변수처리
		String qurey = "insert into \"BOARD\" (\"NUM\", \"TITLE\",\"WRITER\",\"CONTENT\",\"REGDATE\",\"CNT\") values (\"BOARD_SEQ\".nextval, ?, ?, ?, sysdate, 0)";
		//쿼리문 보내기 실패시 값을 확인하기 위해 값 설정(쿼리가 정상적으로 작동했으면 ret = 1;)
		int ret = -1; 
		try {
			//con에는 jdbcutil로 부터 getConnection 함.
			con = ju.getConnection();
			//con으로부터 PreparedStatement(query)를 pstmt변수에 참조함.
			pstmt = con.prepareStatement(qurey);
			//첫번째 파라미터에는 vo값중 getTitle을 셋팅함.
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getContent());
			//1행(숫자값,int)의 결과값을 쿼리로 보냄.
			ret = pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			if(con !=null) {
				try {
					con.close(); //풀에 반환
					}
						catch(SQLException e) {
							e.printStackTrace();
						}
			}
			if(pstmt !=null) {
				try {
					pstmt.close(); //풀에 반환
					}
						catch(SQLException e) {
							e.printStackTrace();
						}
			}
		}
		//결과값 반환
		return ret;
	}
	//전체조회(R)
		//boardVo(DB값)를 가진 List를 반환하는 selectAll()이라는 함수
		//모든 내용을 달라는 함수(전체목록조회)이기 때문에, 파라미터값이 따로 필요하지 않음.
	public List<BoardVo> selectAll(){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		//최신게시글 순으로 정렬 → order by \"NUM\" desc
		String query = "select \"NUM\", \"TITLE\", \"WRITER\", \"CONTENT\", \"REGDATE\", \"CNT\" from \"BOARD\" order by \"NUM\" desc";
		//query의 결과는 List로 만들어져야 하기때문에 ArrayList 사용함.
		ArrayList<BoardVo> ls = new ArrayList<BoardVo>();
		try {
			con = ju.getConnection();
			stmt = con.createStatement();
			//쿼리를 실제로 전송하는 함수(stmt.executeQuery(query);)를 rs에 담는다.
			rs = stmt.executeQuery(query);
			//조회된(=rs) 레코드 수만큼 반복한다.
			while(rs.next()) {
				//BoardVo에 값을 하나씩 담는다.(BoardVo 생성자를 만든 이유 : 생성자로 아래처럼 값들을 담으려고)
				BoardVo vo = new BoardVo(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						new Date(rs.getDate(5).getTime()),
						rs.getInt(6));
				//vo값들을 ls(list)에 담아준다.
				ls.add(vo);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				if(rs !=null) {
					try {
						rs.close(); //풀에 반환
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
				if(con !=null) {
					try {
						con.close(); //풀에 반환
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
				if(stmt !=null) {
					try {
						stmt.close(); //풀에 반환
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
			}
		//결과값 반환
			return ls;
		}
	//하나만 조회(R)	
	public BoardVo selectOne(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		확인할것 String query = "select \"NUM\", \"TITLE\", \"WRITER\", \"CONTENT\", \"REGDATE\", \"CNT\" from \"BOARD\" where \"NUM\"=?";
		String query = "select * from \"BOARD\" where \"NUM\"=?";
		
		BoardVo vo = null;
		try {
			con = ju.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, num);
			//쿼리를 실제로 전송하는 함수(pstmt.executeQuery();)를 rs에 담는다.
			updateCnt(num); //조회수 증가 , 순서는 executeQuery를 보내기 전에 미리 조회수를 증가시킨다.
			rs = pstmt.executeQuery();
			//조회된(=rs)값이 있다면, vo가 생성이 된다.
			if(rs.next()) {
				//vo = 반환되는 값
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
						rs.close(); //풀에 반환
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
				if(con !=null) {
					try {
						con.close(); //풀에 반환
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
				if(pstmt !=null) {
					try {
						pstmt.close(); //풀에 반환
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
			}
		//결과값 반환
			return vo;
		}
		
	
	
	//수정(U)
	public int update(BoardVo vo) {
	Connection con = null;
	PreparedStatement pstmt = null;
	//수정할 때, 사용할 쿼리문
	//정해진 데이터가 아닌, Vo로부터 전달받은 데이터가 들어가기 때문에 바인딩 변수처리
	String qurey = "update \"BOARD\" set \"TITLE\"=?, \"CONTENT\"=? where \"NUM\"=?";
	//쿼리문 보내기 실패시 값을 확인하기 위해 값 설정(쿼리가 정상적으로 작동했으면 ret = 1;)
	int ret = -1; 
	try {
		//con에는 jdbcutil로 부터 getConnection 함.
		con = ju.getConnection();
		//con으로부터 PreparedStatement(query)를 pstmt변수에 참조함.
		pstmt = con.prepareStatement(qurey);
		//첫번째 파라미터에는 vo값중 getTitle을 셋팅함.
		pstmt.setString(1, vo.getTitle());
		pstmt.setString(2, vo.getContent());
		pstmt.setInt(3, vo.getNum());
		//1행(숫자값,int)의 결과값을 쿼리로 보냄.
		ret = pstmt.executeUpdate();
		
	}catch(SQLException e){
		e.printStackTrace();
	}finally {
		if(con !=null) {
			try {
				con.close(); //풀에 반환
				}
					catch(SQLException e) {
						e.printStackTrace();
					}
		}
		if(pstmt !=null) {
			try {
				pstmt.close(); //풀에 반환
				}
					catch(SQLException e) {
						e.printStackTrace();
					}
		}
	}
	//결과값 반환
	return ret;
		
	}
	
	//조회수 증가(U)
		public int updateCnt(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		//수정할 때, 사용할 쿼리문
		//정해진 데이터가 아닌, Vo로부터 전달받은 데이터가 들어가기 때문에 바인딩 변수처리
		//update \"BOARD\" set \"CNT\"=\"CNT\"+1 where \"NUM\"=? 조회수 증가 쿼리
		String qurey = "update \"BOARD\" set \"CNT\"=\"CNT\"+1 where \"NUM\"=?";
		//쿼리문 보내기 실패시 값을 확인하기 위해 값 설정(쿼리가 정상적으로 작동했으면 ret = 1;)
		int ret = -1; 
		try {
			//con에는 jdbcutil로 부터 getConnection 함.
			con = ju.getConnection();
			//con으로부터 PreparedStatement(query)를 pstmt변수에 참조함.
			pstmt = con.prepareStatement(qurey);
			//어떤 게시글인지를 선택하는 개념 셋팅
			pstmt.setInt(1, num);
			//1행(숫자값,int)의 결과값을 쿼리로 보냄.
			ret = pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			if(con !=null) {
				try {
					con.close(); //풀에 반환
					}
						catch(SQLException e) {
							e.printStackTrace();
						}
			}
			if(pstmt !=null) {
				try {
					pstmt.close(); //풀에 반환
					}
						catch(SQLException e) {
							e.printStackTrace();
						}
			}
		}
		//결과값 반환
		return ret;
			
		}
		
		//삭제(D)
		public int delete(int num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			//삭제할 때, 사용할 쿼리문
			//정해진 데이터가 아닌, Vo로부터 전달받은 데이터가 들어가기 때문에 바인딩 변수처리
			String qurey = "delete from \"BOARD\" where \"NUM\"=?";
			//쿼리문 보내기 실패시 값을 확인하기 위해 값 설정(쿼리가 정상적으로 작동했으면 ret = 1;)
			int ret = -1; 
			try {
				//con에는 jdbcutil로 부터 getConnection 함.
				con = ju.getConnection();
				//con으로부터 PreparedStatement(query)를 pstmt변수에 참조함.
				pstmt = con.prepareStatement(qurey);
				pstmt.setInt(1, num);
				//1행(숫자값,int)의 결과값을 쿼리로 보냄.
				ret = pstmt.executeUpdate();
				
			}catch(SQLException e){
				e.printStackTrace();
			}finally {
				if(con !=null) {
					try {
						con.close(); //풀에 반환
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
				if(pstmt !=null) {
					try {
						pstmt.close(); //풀에 반환
						}
							catch(SQLException e) {
								e.printStackTrace();
							}
				}
			}
			//결과값 반환
			return ret;
				
			}
	
	
}
	
    
	



