package dbtest.dbtest.controller;

import dbtest.dbtest.domain.Member;
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
        model.addAttribute("user", memberDto);
        return "myprofile";
    }


    @PostMapping("/myprofile")
    public String updateMyprofile(HttpSession httpSession, Model model, @ModelAttribute MemberDto updataMemberDto) {
        String data = (String) httpSession.getAttribute("userId");
        MemberDto memberDto = memberService.updateMyProfile(httpSession.getId(),updataMemberDto);
        if(memberDto!=null) {
            model.addAttribute("updateprofile", memberDto);
            return "myprofile";
        } else return "정보없음";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userId") Long id) {
        memberService.deleteById(id);
        return "success";
    }


}
