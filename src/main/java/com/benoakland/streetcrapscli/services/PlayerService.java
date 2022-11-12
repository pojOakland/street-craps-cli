package com.benoakland.streetcrapscli.services;
import com.benoakland.streetcrapscli.Player;
import com.benoakland.streetcrapscli.dto.PlayerAuthenticationDto;
import com.benoakland.streetcrapscli.dto.PlayerRegistrationDto;
import com.benoakland.streetcrapscli.security.PasswordHasher;
import com.benoakland.streetcrapscli.util.BasicLogger;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class PlayerService {

    private static final String API_BASE_URL = "http://localhost:8080/players";
    private final RestTemplate restTemplate = new RestTemplate();

    public Player createPlayer() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PlayerRegistrationDto> entity = new HttpEntity<>(addNewPlayer(), headers);
        Player returnedPlayer = null;

        try {
            returnedPlayer = restTemplate.postForObject(API_BASE_URL + "/create", entity, Player.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return returnedPlayer;
    }

    public PlayerAuthenticationDto authenticatePlayer(String displayName) {
        return restTemplate.getForObject(API_BASE_URL + "/authenticate/" + displayName,
                PlayerAuthenticationDto.class);
    }

    private PlayerRegistrationDto addNewPlayer() {
        ConsoleService consoleService = new ConsoleService();
        PasswordHasher passwordHasher = new PasswordHasher();
        consoleService.displayString("Enter the following information for a new player: ");
        String displayName = consoleService.promptForString("Display Name: ");
        String password = consoleService.promptForString("Password: ");
        byte[] salt = passwordHasher.generateRandomSalt();
        String hashedPassword = passwordHasher.computeHash(password, salt);
        String saltString = new String(Base64.encode(salt));
        PlayerRegistrationDto newPlayer = new PlayerRegistrationDto(displayName, hashedPassword, saltString);
        return newPlayer;
    }


}
