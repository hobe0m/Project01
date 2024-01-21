package org.RentalProject.RentalService.mypage.controllers;

import lombok.RequiredArgsConstructor;
import org.RentalProject.RentalService.commons.exceptions.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("mypageController")
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class mypageController implements ExceptionProcessor {

    // 주문 상세 페이지 연결 추가 S //
    @GetMapping("/productDetail")
    public String productDetail(Model model) {

        System.out.println("주문 상세 페이지(주문 목록에서 상품 클릭 시 뜨는 창)");

        return "front/mypage/productDetail";
    }
    // 주문 상세 페이지 연결 추가 E //
}