package com.tplate.layers.b.business.builders;

import com.tplate.layers.a.rest.dtos.role.RoleDto;
import com.tplate.layers.b.business.exceptions.NotImplementedException;
import com.tplate.layers.c.persistence.models.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleBuilder {

    public Role buildModel(RoleDto roleDto) throws NotImplementedException {

        throw new NotImplementedException();

    }

}
