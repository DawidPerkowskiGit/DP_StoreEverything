package com.dmrl.storeverything.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller for handling HTTP errors
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Error endpoint, users will be redirected here by accessing unavailable endpoints
     *
     * @param request Http request
     * @return Error page view
     */
    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        String errorPage = "error"; // default

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // handle HTTP 404 Not Found error
                errorPage = "error/404";

            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                // handle HTTP 403 Forbidden error
                errorPage = "error/403";

            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // handle HTTP 500 Internal Server error
                errorPage = "error/500";

            }
        }

        return errorPage;
    }

    /**
     * Method which redirects to "/error" path
     *
     * @return Error view page
     */
    public String getErrorPath() {
        return "/error";
    }
}
