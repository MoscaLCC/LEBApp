package com.leb.app.service;

import com.leb.app.domain.UserInfo;
import com.leb.app.service.dto.UserInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.leb.app.domain.UserInfo}.
 */
public interface UserInfoService {

    UserInfoDTO save(UserInfoDTO userInfoDTO);

    Optional<UserInfoDTO> partialUpdate(UserInfoDTO userInfoDTO);

    Page<UserInfoDTO> findAll(Pageable pageable);

    Optional<UserInfoDTO> findOne(Long id);

    void delete(Long id);

    Optional<UserInfo> findOneByUserId(Long id);

    void loadMoney(Long id, Double value);
}
