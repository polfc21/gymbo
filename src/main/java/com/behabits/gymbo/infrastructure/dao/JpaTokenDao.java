package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.TokenDao;
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
    public Token findByToken(String token) {
        TokenEntity entity = this.tokenRepository.findByToken(token);
        return entity != null ? this.tokenEntityMapper.toDomain(entity) : null;
    }

    @Override
    public List<Token> findAllTokensByUserId(Long userId) {
        return this.tokenRepository.findAllByPlayerId(userId).stream()
                .map(this.tokenEntityMapper::toDomain)
                .toList();
    }

    @Override
    public void saveAll(List<Token> tokens) {
        List<TokenEntity> entities = tokens.stream()
                .map(this.tokenEntityMapper::toEntity)
                .toList();
        this.tokenRepository.saveAll(entities);
    }

    @Override
    public Token createToken(Token token) {
        TokenEntity entityToSave = this.tokenEntityMapper.toEntity(token);
        TokenEntity entitySaved = this.tokenRepository.save(entityToSave);
        return this.tokenEntityMapper.toDomain(entitySaved);
    }

    @Override
    public Token findByTokenAndUserId(String token, Long userId) {
        TokenEntity entity = this.tokenRepository.findByTokenAndPlayerId(token, userId);
        return entity != null ? this.tokenEntityMapper.toDomain(entity) : null;
    }

}
