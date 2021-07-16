package ru.intern.security;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.intern.repository.UserRepository;

/**
 * @author Kir
 * Created on 16.07.2021
 */
@Api(description = "Аутентификация и авторизация")
@RestControllerAdvice
@RequestMapping
public class SecurityController {
    @Autowired
    private UserRepository userRepository;

    private final static Logger LOG = LoggerFactory.getLogger(SecurityController.class);

    @ApiOperation(value = "Завершение сессии пользователя")
    @GetMapping(value = "/api/logout")
    public String login(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        System.out.println(userRepository.findUserByLogin("admin"));
        return "logout";
    }


    @ApiOperation(value = "Проверка имени и пароля входа, получение сведений о ролях и подразделении пользователя", response = LoginResponse.class)
    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = {"application/json"})
    public LoginResponse login(@RequestBody LoginPair login, HttpServletRequest request, HttpServletResponse response) {
        if (login == null || login.getUsername() == null) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return new LoginResponse(response.getStatus(), "Unauthorized");
        }
        System.out.println(userRepository.findUserByLogin(login.getUsername()));
        return new LoginResponse(HttpServletResponse.SC_OK, "Authorized");
    }


    public static class LoginResponse {
        private final int code;
        private final String message;

        LoginResponse(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

    }

    public static class LoginPair {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
