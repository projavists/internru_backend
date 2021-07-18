package ru.intern.security;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.intern.entity.UserEntity;
import ru.intern.security.jwt.JwtProvider;
import ru.intern.service.UserService;

/**
 * @author Kir
 * Created on 16.07.2021
 */
@Api(description = "Аутентификация и авторизация")
@RestControllerAdvice
@RequestMapping
@RestController
public class SecurityController {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserService userService;


    private final static Logger LOG = LoggerFactory.getLogger(SecurityController.class);

    @ApiOperation(value = "Регистрация нового пользователя")
    @PostMapping("/api/register")
    public LoginResponse register(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginPair loginPair) {
        if (userService.findByLogin(loginPair.getUsername()) == null) {
            UserEntity user = new UserEntity();
            user.setLogin(loginPair.getUsername());
            user.setPassword(loginPair.getPassword());
            userService.save(user);
            return new LoginResponse(HttpServletResponse.SC_OK, "user successfully saved");
        }
        throw new BadCredentialsException("login already exists");
    }


    @ApiOperation(value = "Завершение сессии пользователя")
    @GetMapping(value = "/api/logout")

    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        //TODO black list???
        return "logout";
    }


    @ApiOperation(value = "Проверка имени и пароля входа, получение сведений о ролях", response = LoginResponse.class)
    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = {"application/json"})
    public LoginResponse login(@RequestBody LoginPair login, HttpServletRequest request, HttpServletResponse response) {
        UserEntity userEntity = userService.findByLoginAndPassword(login.getUsername(), login.getPassword());
        if (login.getUsername() == null || userEntity == null) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return new LoginResponse(response.getStatus(), "Unauthorized");
        }
        String token = jwtProvider.generateToken(userEntity.getLogin());
        return new LoginResponse(HttpServletResponse.SC_OK, token);
    }


    public static class LoginResponse {
        private final int code;
        private final String token;

        LoginResponse(int code, String token) {
            this.code = code;
            this.token = token;
        }

        public int getCode() {
            return code;
        }

        public String getToken() {
            return token;
        }

    }

    public static class LoginPair {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

    }
}
