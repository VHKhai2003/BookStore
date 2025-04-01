package com.example.bookstore.config;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.bookstore.dto.CartDto;
import com.example.bookstore.dto.UserDto;
import com.example.bookstore.model.Genre;
import com.example.bookstore.service.CartService;
import com.example.bookstore.service.GenreService;
import com.example.bookstore.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalDataConfig {

	private final GenreService genreService;
	private final UserService userService;
	private final CartService cartService;

	@ModelAttribute("genres")
	public List<Genre> getListGenres(HttpServletRequest request) {
		log.info("Request coming: {}", request.getRequestURL());
		return genreService.getListGenres();
	}

	@ModelAttribute("loginUser")
	public UserDto getAuthenticatedUserInfo(Authentication authentication) {
		UserDto userInfo = null;
		if (authentication != null && authentication.isAuthenticated()) {
			userInfo = userService.getUserInfoByUsername(authentication.getName());
		}
		return userInfo;
	}

	@ModelAttribute("numCart")
	public Integer getNumberItemInCart(Model model, HttpServletRequest request) {
		
//		the number of admins is small, so don't need to check
		if(!request.getRequestURI().startsWith("/admin")) {
			UserDto userInfo = (UserDto) model.getAttribute("loginUser");
			if (userInfo != null) {
				List<CartDto> carts = cartService.getCartOfUser(userInfo.getId());
				Integer numberOfItem = carts.size();
				if (numberOfItem > 0) {
					return numberOfItem;
				}
			}
		}
		return null;
	}
}
