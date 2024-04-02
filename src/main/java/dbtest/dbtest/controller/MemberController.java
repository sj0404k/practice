package dbtest.dbtest.controller;

import dbtest.dbtest.domain.Member;
import dbtest.dbtest.repository.DbMemberRepository;
import dbtest.dbtest.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final DbMemberRepository dbMemberRepository;

    @PostMapping("/register")
    public String join(@ModelAttribute MemberDto memberDto) {
        if (memberService.join(memberDto))
            return "success";
        else return "fail";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDto memberDto, HttpSession httpSession) {
        Optional<MemberDto> login = memberService.login(memberDto);
        if (login.isPresent()) {
            httpSession.setAttribute("userId", login.get().getUserId());
            return "success";
        } else return "fail";
    }

    @GetMapping("/myprofile")
    public String viewMyprofile(HttpSession httpSession, Model model) {
        String userId = (String) httpSession.getAttribute("userId");
        Optional<Member> memberDto = memberService.findByUserId(userId);
        if (memberDto.isPresent()) {
            Member member = memberDto.get();
            model.addAttribute("user", member);
            return "myprofile";
        } else {
            model.addAttribute("errorMessage", "회원 정보를 찾을 수 없습니다.");
            return "error";
        }

    }


    @PostMapping("/myprofile")
    public String updateMyprofile(HttpSession httpSession, @ModelAttribute MemberDto updateMemberDto) {
        String userId = (String) httpSession.getAttribute("userId");
        MemberDto memberDto = memberService.updateMyProfile(userId, updateMemberDto);
        return "success";
    }

    //    @PostMapping("/delete")
//    public String deleteUser(@RequestParam("userId") Long id) {
//        memberService.deleteById(id);
//        return "success";
//    }
    @PostMapping("/delete")
    public String deleteUser(HttpSession httpSession) {
        String userId = (String) httpSession.getAttribute("userId");
        Optional<Member> memberDto = memberService.findByUserId(userId);
        if (memberDto.isPresent()) {
            memberService.deleteById(memberDto.get().getId());
            return "success";
        } else {
            return "error";
        }
    }
}
