package com.example.car.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.car.model.UserToken;

public interface JwtTokenRepository extends JpaRepository<UserToken, Long> {

	Optional<UserToken> findByToken(String jwtToken);

	void deleteByToken(String token);

	@Query("from UserToken as ut where ut.tokenType = :tokenTypeId" + " and ut.user.id=:userId")
	List<UserToken> findAllByUserIdAndTokenType(@Param("tokenTypeId") long userId, @Param("userId") int tokenTypeId);
}
