package com.minsk.frontendpracticeservice.repository;

import com.minsk.frontendpracticeservice.domain.entity.Account;
import com.minsk.frontendpracticeservice.domain.entity.Card;
import com.minsk.frontendpracticeservice.domain.entity.ClientAccount;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;
import java.util.Optional;

public interface ClientAccountRepository extends MongoRepository<ClientAccount, String> {

    Optional<ClientAccount> findByUserId(String userId);

    Boolean existsByUserId(String userId);

    @Query("{ 'userId': ?0 }")
    @Update("{ '$addToSet' : { 'accounts' : { '$each' : ?1 } } }")
    int updateClientAccountWithAccounts(String userId, List<Account> accounts);

    @Query("{ 'userId': ?0, 'accounts.id': ?1 }")
    @Update("{ '$addToSet' : { 'accounts.$.cards' : { '$each' : ?2 } } }")
    int updateClientAccountWithCards(String userId, String accountId, List<Card> cards);

    @Aggregation(pipeline = {
            "{ '$match': { 'userId': ?0 } }",
            "{ '$unwind': '$accounts' }",
            "{ '$unwind': '$accounts.cards' }",
            "{ '$replaceRoot': { 'newRoot': '$accounts.cards' } }"
    })
    List<Card> findCardsByUserId(String userId);

}
