package org.it_sakerhet_java23_karl_kowal_uppgift1.repo;

import org.it_sakerhet_java23_karl_kowal_uppgift1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmail(String username);
}
