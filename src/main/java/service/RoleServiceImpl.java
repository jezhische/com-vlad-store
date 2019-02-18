package service;

import com.vlad.store.model.Role;
import com.vlad.store.model.roles.RoleEnum;
import com.vlad.store.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByRole(RoleEnum role) {
        return roleRepository.findByRole(role);
    }
}
