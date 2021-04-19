package com.tplate.layers.a.rest.controllers;

import com.tplate.layers.a.rest.controllers.shared.PathConstant;
import com.tplate.layers.a.rest.dtos.ResponseDto;
import com.tplate.layers.b.business.exceptions.NotImplementedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathConstant.BASE_PATH + "/roles")
public class RoleController {


    @GetMapping(value = "")
    public ResponseDto getRoles() throws NotImplementedException {
        throw new NotImplementedException();
    }

}
