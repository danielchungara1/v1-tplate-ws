package com.tplate.layers.business.builders;

import com.tplate.layers.admission.dtos.role.RoleDto;
import com.tplate.layers.business.exceptions.NotImplementedException;
import com.tplate.layers.persistence.models.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleBuilder {

    public Role buildModel(RoleDto roleDto) throws NotImplementedException {

        throw new NotImplementedException();

    }

}
