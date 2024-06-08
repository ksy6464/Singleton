package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.member.dao.IMemberDao;
import kr.or.ddit.member.dao.MemberDaoImplWithJDBC;
import kr.or.ddit.member.vo.MemberVO;

public class MemberServiceImpl implements IMemberService {
	
	private static IMemberService memService = new MemberServiceImpl();
	

	public static IMemberService getInstance() {
		return memService;
	}
	
	private IMemberDao memDao;
	
	private MemberServiceImpl() {
		memDao = MemberDaoImplWithJDBC.getInstance();
	}

	@Override
	public int registerMember(MemberVO mv) {
		int cnt = memDao.insertMember(mv);
		
		// 회원에게 가입완료 메일 발송하기...
		
		return cnt;
		
	}

	@Override
	public int modifyMember(MemberVO mv) {
		int cnt = memDao.updateMember(mv);
		return cnt;
	}

	@Override
	public boolean chechMember(String memId) {
		return memDao.chechMember(memId);
	}

	@Override
	public int removeMember(String memId) {
		int cnt = memDao.deleteMember(memId);
		return cnt;
	}

	@Override
	public List<MemberVO> getTotalMember() {
		List<MemberVO> memList = memDao.getAllMember();
		return memList;
	}

}
