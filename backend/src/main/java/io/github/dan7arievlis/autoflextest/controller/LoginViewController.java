package io.github.dan7arievlis.autoflextest.controller;

import io.github.dan7arievlis.autoflextest.security.CustomAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Tag(name = "Páginas")
public class LoginViewController {
    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    @Operation(summary = "Home page", description = "Página padrão para assegurar login do usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário loggado.")
    })
    public String homePage(Authentication authentication) {
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.user());
        }
        return "Hello " + authentication.getName();
    }

    @GetMapping("/authorized")
    @ResponseBody
    @Operation(summary = "Autorizar", description = "Autoriza um usuário cadastrado pelo código de parâmetro.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário autorizado."),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado.")
    })
    public String getAuthorizationCode(@RequestParam("code") String code) {
        return "Your authorization code is: " + code;
    }
}
