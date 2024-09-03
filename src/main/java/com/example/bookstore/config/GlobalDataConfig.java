package com.example.bookstore.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class GlobalDataConfig {

	@Autowired
	private GenreService genreService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@ModelAttribute("genres")
	public List<Genre> getListGenres(HttpServletRequest request) {
		System.out.println("controller advice ---- " + request.getRequestURL());
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
	public Integer getNumberItemInCart(Model model) {
		UserDto userInfo = (UserDto) model.getAttribute("loginUser");
		if (userInfo != null) {
			List<CartDto> carts = cartService.getCartOfUser(userInfo.getId());
			Integer numberOfItem = carts.size();
			if (numberOfItem > 0) {
				return numberOfItem;
			}
		}
		return null;
	}
}
