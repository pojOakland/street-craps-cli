package com.benoakland.streetcrapscli.services;
import com.benoakland.streetcrapscli.Player;
import com.benoakland.streetcrapscli.dto.PlayerAuthenticationDto;
import com.benoakland.streetcrapscli.dto.PlayerRegistrationDto;
import com.benoakland.streetcrapscli.dto.PlayerUpdateDto;
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

    private static final String API_BASE_URL = "http://localhost:8080";
    private final RestTemplate restTemplate = new RestTemplate();

    private final PasswordHasher passwordHasher = new PasswordHasher();
    private final ConsoleService consoleService = new ConsoleService();

    public Player createPlayer() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PlayerRegistrationDto> entity = new HttpEntity<>(addNewPlayer(), headers);
        Player returnedPlayer = null;

        try {
            returnedPlayer = restTemplate.postForObject(API_BASE_URL + "/player/create", entity, Player.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return returnedPlayer;
    }

    public Player getPlayer(String displayName) {
        return restTemplate.getForObject(API_BASE_URL + "/player/" + displayName, Player.class);
    }

    public PlayerAuthenticationDto authenticatePlayer(String displayName) {
        return restTemplate.getForObject(API_BASE_URL + "/player/authenticate/" + displayName,
                PlayerAuthenticationDto.class);
    }

    public Boolean updatePlayer(PlayerUpdateDto playerUpdateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PlayerUpdateDto> entity = new HttpEntity<>(playerUpdateDto, headers);

        boolean isSuccessful = false;
        try {
            restTemplate.put(API_BASE_URL + "/player/update", entity);
            isSuccessful = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getLocalizedMessage());
        }
        return isSuccessful;
    }

    private PlayerRegistrationDto addNewPlayer() {
        ConsoleService consoleService = new ConsoleService();
        PasswordHasher passwordHasher = new PasswordHasher();
        consoleService.printString("Enter the following information for a new player: ");
        String displayName = consoleService.promptForString("Display Name: ");
        String password = consoleService.promptForString("Password: ");
        byte[] salt = passwordHasher.generateRandomSalt();
        String hashedPassword = passwordHasher.computeHash(password, salt);
        String saltString = new String(Base64.encode(salt));
        PlayerRegistrationDto newPlayer = new PlayerRegistrationDto(displayName, hashedPassword, saltString);
        return newPlayer;
    }

    public Player login() {
        PlayerAuthenticationDto playerAuthenticationDto = null;
        boolean isAuthenticated = false;

        while (playerAuthenticationDto == null) {
            try {
                String displayName = consoleService.promptForString("Enter Display Name: ");
                PlayerAuthenticationDto returnedPlayerAuthenticationDto = authenticatePlayer(displayName);
                if (returnedPlayerAuthenticationDto.getDisplayName() != null &&
                        returnedPlayerAuthenticationDto.getSalt() != null &&
                        returnedPlayerAuthenticationDto.getHashedPassword() != null) {
                    playerAuthenticationDto = returnedPlayerAuthenticationDto;
                }
            } catch (Exception e) {
                BasicLogger.log(e.getLocalizedMessage());
            }
        }

        String storedSalt = playerAuthenticationDto.getSalt();
        String storedPassword = playerAuthenticationDto.getHashedPassword();

        while (!isAuthenticated) {
            String password = consoleService.promptForString("Enter Password: ");
            String hashedPassword = passwordHasher.computeHash(password, Base64.decode(storedSalt));
            isAuthenticated = storedPassword.equals(hashedPassword);
        }

        return getPlayer(playerAuthenticationDto.getDisplayName());
    }
}
