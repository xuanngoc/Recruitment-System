package ezjob.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HandlerErrorController implements ErrorController {

	@GetMapping("/error")
    public String errorHandler(HttpServletRequest request, Model model) {
        // Get status code to determine which view should be returned
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        //ModelAndView mav = new ModelAndView("error/error_default");
        model.addAttribute("code", statusCode.toString());
        return "error/error_default";
    }
	
	@Override
	public String getErrorPath() {
		return "/error";
	}

	
}
