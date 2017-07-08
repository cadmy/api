package ru.cadmy.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import ru.cadmy.api.model.Profile;
import ru.cadmy.api.service.ProfileService;

/**
 * Created by ssemenov on 28.06.17.
 */
@RestController
@RequestMapping("/api")
@Api(value = "/api", description = "Операции с профилем")
public class MainController {

    ProfileService profileService;

    @Autowired
    public MainController(ProfileService profileService) {
        Assert.notNull(profileService, "ProfileService cannot be null!");
        this.profileService = profileService;
    }

    /**Выводит данные о профиле*/
    @GetMapping("/getProfile")
    @ApiOperation(value = "Выводит данные о профиле")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "string", paramType = "header"),
    })
    public Profile getProfile(@RequestParam int id) {
        return profileService.getProfileById(id);
    }
}
