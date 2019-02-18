package service;

import com.vlad.store.model.Role;
import com.vlad.store.model.roles.RoleEnum;

public interface RoleService {

    Role findByRole(RoleEnum role);
}
