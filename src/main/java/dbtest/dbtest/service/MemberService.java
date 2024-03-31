package dbtest.dbtest.service;

import dbtest.dbtest.controller.MemberDto;
import dbtest.dbtest.domain.Member;
import dbtest.dbtest.repository.DbMemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final DbMemberRepository dbMemberRepository;
    private final Logger log = LoggerFactory.getLogger(MemberService.class);
    //회원가입
    public boolean join(MemberDto memberDto) {
        if (memberDto.getUserId()==null || memberDto.getUserPwd()==null || memberDto.getUserEmail()==null|| memberDto.getUserName()==null) {
            log.error("값이 비어있음");
            return false;
        }
        if (dbMemberRepository.findByUserId(memberDto.getUserId()).isPresent()) {
            log.error("아이디 중복");
            return false;
        }
        Member member = Member.builder()
                .userEmail(memberDto.getUserEmail())
                .userId(memberDto.getUserId())
                .userPwd(memberDto.getUserPwd())
                .userName(memberDto.getUserName())
                .build();

        dbMemberRepository.save(member);
        return true;
    }

    //로그인
    public Optional<MemberDto> login(MemberDto memberDto) {
        Optional<Member> memberId = dbMemberRepository.findByUserId(memberDto.getUserId());
        if(memberId.isPresent()){
            Member member = memberId.get();
            if (member.getUserPwd().equals(memberDto.getUserPwd())){

                return Optional.of(MemberDto.entityToDto(member));
            }
            else{log.error("비번 틀림"); return Optional.empty();}
        }
        else {
            log.error("아이디 없음");
            return Optional.empty();
        }
    }

    //회원정보 찾기
//    public DbMemberRepository getMyInfo(Long id){
//
//    }
    public Optional<Member> findByUserId(String userId) {
        return dbMemberRepository.findByUserId(userId);
    }
    //정보 수정
    public MemberDto updateMyProfile(String memberId, MemberDto updateMemberDto){
        Optional<Member> data = dbMemberRepository.findByUserId(memberId);
        if (data.isPresent()){
            Member member = data.get();
            member.setUserEmail(updateMemberDto.getUserEmail());
            member.setUserName(updateMemberDto.getUserName());
            member.setUserPwd(updateMemberDto.getUserPwd());
            dbMemberRepository.save(member);

            //Member member = Member.builder()
            //                .userEmail(memberDto.getUserEmail())
            //                .userId(memberDto.getUserId())
            //                .userPwd(memberDto.getUserPwd())
            //                .userName(memberDto.getUserName())
            //                .build();
            return MemberDto.entityToDto(member);
        }
        else {
            log.error("id 찾을수  없음");
            return null;
        }
    }


    //정보 삭제
    public void deleteById(Long id) {
        dbMemberRepository.deleteById(id);
    }


}