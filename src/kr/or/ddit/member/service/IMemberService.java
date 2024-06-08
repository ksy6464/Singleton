package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.member.vo.MemberVO;

public interface IMemberService {
	
	/**
	 * MemberVO에 담겨진 회원정보를 등록하기 위한 메서드
	 * @param mv 회원정보를 담은 MemberVO 객체
	 * @return DB작업이 성공하면 1, 실패하면 0 반환됨.
	 */
	public int registerMember(MemberVO mv);
	
	/**
	 * MemberVO에 담겨진 회원정보를 수정하기 위한 메서드
	 * @param mv 회원정보를 담은 MemberVO 객체
	 * @return DB작업이 성공하면 1, 실패하면 0 반환됨.
	 */
	public int modifyMember(MemberVO mv);
	
	/**
	 * 해당 회원이 존재여부를 확인하기 위한 메서드
	 * @param memId 존재여부 확인한기위한 회원ID
	 * @return 해당 회원이 존재한다면 true, 존재하지 않으면 false 리턴함.
	 */
	public boolean chechMember(String memId);
	
	/**
	 * 해당 회원정보를 삭제하기 위한 메서드
	 * @param memId 삭제하고자 하는 회원ID
	 * @return 삭제처리가 성공하면 1, 실패하면 0 반환됨.
	 */
	public int removeMember(String memId);
	
	/**
	 * 모든 회원정보를 가져오기 위한 메서드
	 * @return 모든 회원정보를 담은 List객체
	 */
	public List<MemberVO> getTotalMember();
	
}
