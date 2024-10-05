package com.si642.billmanagementsi642projectbackend.iam.interfaces.rest;



import com.si642.billmanagementsi642projectbackend.iam.domain.services.UserCommandService;
import com.si642.billmanagementsi642projectbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.resources.AuthenticatedUserResource;

import com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.resources.SignInResource;
import com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.resources.SignUpResource;
import com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.resources.UserResource;
import com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * AuthenticationController
 * <p>
 *     This controller is responsible for handling authentication requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/auth/sign-in</li>
 *         <li>POST /api/v1/auth/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {
    private final UserCommandService userCommandService;
    private final UserRepository userRepository;

    public AuthenticationController(UserCommandService userCommandService, UserRepository userRepository) {
        this.userCommandService = userCommandService;
        this.userRepository = userRepository;
    }

    /**
     * Handles the sign-in request.
     * @param signInResource the sign-in request body.
     * @return the authenticated user resource.
     */
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource signInResource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var authenticatedUser = userCommandService.handle(signInCommand);
        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
        return ResponseEntity.ok(authenticatedUserResource);
    }

    /**
     * Handles the sign-up request.
     * @param signUpResource the sign-up request body.
     * @return the created user resource.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var user = userCommandService.handle(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        // Recuperar el usuario con los roles
        var userWithRoles = userRepository.findById(user.get().getId());
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(userWithRoles.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);

    }
}
