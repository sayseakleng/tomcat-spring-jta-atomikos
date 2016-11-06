package com.mcnc.mbanking.auth.repository;

import org.springframework.stereotype.Repository;

import com.mcnc.mbanking.auth.domain.User;

@Repository
public interface UserRepository {
	int save(User user);
}
