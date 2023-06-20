package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.TokenDao;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.infrastructure.repository.TokenRepository;
import com.behabits.gymbo.infrastructure.repository.entity.TokenEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.TokenEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaTokenDao implements TokenDao {

    private final TokenRepository tokenRepository;
    private final TokenEntityMapper tokenEntityMapper;

    @Override
    public List<Token> findAllTokensByUserId(Long userId) {
        return this.tokenRepository.findAllByPlayerId(userId).stream()
                .map(this.tokenEntityMapper::toDomain)
                .toList();
    }

    @Override
    public void saveAll(List<Token> tokens) {
        List<TokenEntity> tokenEntities = tokens.stream()
                .map(this.tokenEntityMapper::toEntity)
                .toList();
        this.tokenRepository.saveAll(tokenEntities);
    }

    @Override
    public Token createToken(Token token) {
        TokenEntity tokenEntity = this.tokenEntityMapper.toEntity(token);
        TokenEntity createdToken = this.tokenRepository.save(tokenEntity);
        return this.tokenEntityMapper.toDomain(createdToken);
    }

    @Override
    public Token findByTokenAndUserId(String token, Long userId) {
        TokenEntity tokenEntity = this.tokenRepository.findByTokenAndPlayerId(token, userId);
        if (tokenEntity == null) {
            throw new PermissionsException("Token not found for this user");
        }
        return this.tokenEntityMapper.toDomain(tokenEntity);
    }

}
