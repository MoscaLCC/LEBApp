package com.leb.app.web.rest;

import com.leb.app.domain.User;
import com.leb.app.domain.UserInfo;
import com.leb.app.repository.UserInfoRepository;
import com.leb.app.service.UserInfoQueryService;
import com.leb.app.service.UserInfoService;
import com.leb.app.service.UserService;
import com.leb.app.service.criteria.UserInfoCriteria;
import com.leb.app.service.dto.UserFullInfoDTO;
import com.leb.app.service.dto.UserInfoDTO;
import com.leb.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.leb.app.domain.UserInfo}.
 */
@RestController
@RequestMapping("/api")
public class UserInfoResource {

    private final Logger log = LoggerFactory.getLogger(UserInfoResource.class);

    private static final String ENTITY_NAME = "userInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserInfoService userInfoService;

    private final UserInfoRepository userInfoRepository;

    private final UserInfoQueryService userInfoQueryService;

    private final UserService userService;

    public UserInfoResource(
        UserInfoService userInfoService,
        UserInfoRepository userInfoRepository,
        UserInfoQueryService userInfoQueryService,
        UserService userService
    ) {
        this.userInfoService = userInfoService;
        this.userInfoRepository = userInfoRepository;
        this.userInfoQueryService = userInfoQueryService;
        this.userService = userService;
    }


    @PostMapping("/user-infos")
    public ResponseEntity<UserInfoDTO> createUserInfo(@RequestBody UserInfoDTO userInfoDTO) throws URISyntaxException {
        log.debug("REST request to save UserInfo : {}", userInfoDTO);
        if (userInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new userInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserInfoDTO result = userInfoService.save(userInfoDTO);
        return ResponseEntity
            .created(new URI("/api/user-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/user-infos/{id}")
    public ResponseEntity<UserInfoDTO> updateUserInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserInfoDTO userInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserInfo : {}, {}", id, userInfoDTO);
        if (userInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserInfoDTO result = userInfoService.save(userInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userInfoDTO.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/user-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserInfoDTO> partialUpdateUserInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserInfoDTO userInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserInfo partially : {}, {}", id, userInfoDTO);
        if (userInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserInfoDTO> result = userInfoService.partialUpdate(userInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userInfoDTO.getId().toString())
        );
    }


    @GetMapping("/user-infos")
    public ResponseEntity<List<UserInfoDTO>> getAllUserInfos(UserInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserInfos by criteria: {}", criteria);
        Page<UserInfoDTO> page = userInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/user-infos/count")
    public ResponseEntity<Long> countUserInfos(UserInfoCriteria criteria) {
        log.debug("REST request to count UserInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(userInfoQueryService.countByCriteria(criteria));
    }

    @GetMapping("/user-infos/{id}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable Long id) {
        log.debug("REST request to get UserInfo : {}", id);
        Optional<UserInfoDTO> userInfoDTO = userInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userInfoDTO);
    }

    @GetMapping("/user-infos/user/{id}")
    public ResponseEntity<UserFullInfoDTO> getUserInfoUser(@PathVariable Long id) {
        log.debug("REST request to get UserInfo : {}", id);
        Optional<User> user = userService.getUser(id);
        Optional<UserInfo> userInfo = userInfoService.findOneByUserId(id);
        
        if(user.isPresent() && userInfo.isPresent()){
            UserFullInfoDTO dto = new UserFullInfoDTO();
            dto.update(user.get(), userInfo.get());
            return ResponseEntity.ok().body(dto);
        } else {
            return ResponseEntity.badRequest().build();
        }
        
    }

    @DeleteMapping("/user-infos/{id}")
    public ResponseEntity<Void> deleteUserInfo(@PathVariable Long id) {
        log.debug("REST request to delete UserInfo : {}", id);
        userInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
