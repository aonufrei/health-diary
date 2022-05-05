package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {

	boolean existsCredentialsByUsername(String username);

	boolean existsCredentialsByUsernameAndPassword(String username, String password);

	Credentials getFirstByUsername(String username);

}
