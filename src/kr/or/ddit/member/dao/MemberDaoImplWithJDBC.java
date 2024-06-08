package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.util.JDBCUtil3;

public class MemberDaoImplWithJDBC implements IMemberDao{
	
	// 나 자신의 타입 객체 저장을 위한 객체 변수 선언... (static으로 선언한다)
	private static IMemberDao memDao = new MemberDaoImplWithJDBC();
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;

	///외부에서 접근을 막기 위해서 생성자를 private으로 만들어주겠다.
	private MemberDaoImplWithJDBC() {
		// TODO Auto-generated constructor stub
	}
	
	///이 객체를 안만들고도 사용할 수 있도록 static 메서드를 만들겠다
	/// static으로 만들지 않으면 인스턴스메서드라서 객체를 만들지 않으면 접근할 수 없고 
	///위에서 private을 해놔서 외부에서 객체를 만들 수도 없다
	public static IMemberDao getInstance() {
		return memDao; ///처음에 만든 객체를 계속 돌려쓰는거다
	}
	
	@Override
	public int insertMember(MemberVO mv) {
		int cnt =0;
		try {
			
			conn = JDBCUtil3.getConnection();
			
			String sql = " insert into mymember(mem_id, mem_name, mem_tel, mem_addr)\r\n" + 
					" values (?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mv.getMemId());
			pstmt.setString(2, mv.getMemName());
			pstmt.setString(3, mv.getMemTel() );
			pstmt.setString(4, mv.getMemAddr());

			cnt = pstmt.executeUpdate();
			
		} catch (SQLException ex) { 
			ex.printStackTrace();
		} 

		finally {

			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}

		return cnt;
	}
	
	@Override
	public int updateMember(MemberVO mv) {
		int cnt = 0;
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = " update mymember set mem_name=?, mem_tel=?, mem_addr=? \r\n" + 
					"    where mem_id=? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mv.getMemName());
			pstmt.setString(2, mv.getMemTel());
			pstmt.setString(3, mv.getMemAddr());
			pstmt.setString(4, mv.getMemId());

			cnt = pstmt.executeUpdate();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}

	@Override
	public boolean chechMember(String memId) {
		 boolean isExist = false;
			
		 try {
	
			 conn = JDBCUtil3.getConnection();
				
				String sql = " select count(*) as cnt from mymember where mem_id=? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, memId);
				
				rs = pstmt.executeQuery();
				
				int cnt = 0;
				while (rs.next()) {
					cnt = rs.getInt(1);
				}
				
				if(cnt > 0) {
					isExist = true;
				}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//자원반남
			JDBCUtil3.close(conn, stmt, pstmt, rs);
			
		}
		 return isExist;
	}

	@Override
	public int deleteMember(String memId) {
		int cnt = 0;
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = " delete from mymember where mem_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, memId);
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMember() {
		
		List<MemberVO> memList = new ArrayList<MemberVO>();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(" select * from mymember ");
			
			while (rs.next()) {
				String memId = rs.getString("mem_id");
				String memName = rs.getString("mem_name");
				String memTel = rs.getString("mem_tel");
				String memAddr = rs.getString("mem_addr");
				
				LocalDate regDt = rs.getTimestamp("reg_dt").toLocalDateTime().toLocalDate();
				
				MemberVO mv = new MemberVO();
				mv.setMemId(memId);
				mv.setMemName(memName);
				mv.setMemTel(memTel);
				mv.setMemAddr(memAddr);
				mv.setRegDt(regDt);
				
				memList.add(mv);
				
			}
			
		} catch (SQLException e) {
			
		}finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return memList;
	}


}
