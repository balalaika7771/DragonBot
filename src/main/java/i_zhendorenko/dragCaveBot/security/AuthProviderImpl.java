package i_zhendorenko.dragCaveBot.security;

import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.services.PersonDetailsService;
import i_zhendorenko.dragCaveBot.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.DataBinder;

@Component
    public class AuthProviderImpl implements AuthenticationProvider {
    private final PersonValidator personValidator;
    private final PersonDetailsService personDetailsService;


    @Autowired
    public AuthProviderImpl(PersonValidator personValidator, PersonDetailsService personDetailsService) {
        this.personValidator = personValidator;
        this.personDetailsService = personDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        final DataBinder dataBinder = new DataBinder(new Person(username,password));
        dataBinder.addValidators(personValidator);
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors())
            throw new BadCredentialsException("Incorrect password");


        UserDetails userDetails = personDetailsService.loadUserByUsername(username);

        if (!password.equals(userDetails.getPassword())){
            personDetailsService.updatePassword(userDetails,password);
        }


        CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        customAuthenticationToken.setDetails(userDetails);

        return customAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
