package ru.skillbox.diplom.group40.social.network.impl.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group40.social.network.api.dto.notification.SettingUpdateDTO;
import ru.skillbox.diplom.group40.social.network.api.dto.notification.Type;
import ru.skillbox.diplom.group40.social.network.domain.notification.Settings;
import ru.skillbox.diplom.group40.social.network.impl.repository.notification.SettingsRepository;
import ru.skillbox.diplom.group40.social.network.impl.utils.auth.AuthUtil;
import ru.skillbox.diplom.group40.social.network.impl.utils.technikalUser.TechnicalUserConfig;

import java.util.UUID;

import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith({MockitoExtension.class})
//@SpringBootTest
@AutoConfigureMockMvc
public class NotificationSettingsServiceTest {
    @Mock
    SettingsRepository settingsRepository;
    @Mock
    AuthUtil authUtil;
    @InjectMocks
    private NotificationSettingsService notificationSettingsService;
    @Autowired
    JwtEncoder jwtEncoder;
    @Autowired
    TechnicalUserConfig technicalUser;
    @Autowired
    JwtEncoder accessTokenEncoder;
    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() {
        notificationSettingsService = new NotificationSettingsService(
                settingsRepository);
    }

//    @Test
    @DisplayName("Update settings")
    @Transactional
    void updateSettings() {
        technicalUser.executeByTechnicalUser(() -> 2);
        UUID randomUUID = UUID.randomUUID();

        SettingUpdateDTO settingUpdateDTO = new SettingUpdateDTO();
        settingUpdateDTO.setNotificationType(Type.POST);
        settingUpdateDTO.setEnable(false);

        Settings settings = new Settings();
        settings.setAccountId(randomUUID);

        when(settingsRepository.findByAccountId(randomUUID)).thenReturn(settings);
        when(authUtil.getUserId()).thenReturn(randomUUID);
        when(settingsRepository.save(settings)).thenReturn(settings);

        notificationSettingsService.setSetting(settingUpdateDTO);

    }

}
