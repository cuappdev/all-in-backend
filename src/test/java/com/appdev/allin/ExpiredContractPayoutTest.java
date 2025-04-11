package com.appdev.allin;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.user.User;
import com.appdev.allin.contract.ContractRepo;
import com.appdev.allin.factories.ContractFactory;
import com.appdev.allin.factories.UserFactory;
import com.appdev.allin.transaction.TransactionService;
import com.appdev.allin.user.UserService;
import com.appdev.allin.transaction.Transaction;

public class ExpiredContractPayoutTest {

@Mock
private ContractRepo contractRepo;

@Mock
private UserService userService;

@Mock
private TransactionService transactionService;


@InjectMocks
private Application application;

private ContractFactory contractFactory;
private User owner;
private Contract expiredContract;
private Contract unexpiredContract;

@BeforeEach
public void setUp() {
MockitoAnnotations.openMocks(this);

// Initialize the factory
contractFactory = new ContractFactory();

// Create a random user using UserFactory
owner = UserFactory.createRandomUser();
owner.setUid("test-user-1"); // Set an ID for testing consistency

// Create contracts using the factory
expiredContract = contractFactory.createRandomContract(owner);
expiredContract.setExpirationTime(expiredContract.getCreationTime().plusDays(-10));

// Already expired contract
expiredContract.setExpired(false); // Not yet marked expired
expiredContract.setValue(200); // Payout value

// Unexpired contract
unexpiredContract = contractFactory.createRandomContract(owner);
unexpiredContract.setExpirationTime(LocalDateTime.now().plusDays(30));
unexpiredContract.setExpired(false);

// Mock contract repository to return the contracts
when(contractRepo.findAll()).thenReturn(Arrays.asList(expiredContract,
unexpiredContract));
}

@Test
public void testExpiredContractPayout() {
    Application appSpy = spy(application);

    // Make sure contractRepo returns both contracts
    when(contractRepo.findAll()).thenReturn(Arrays.asList(expiredContract, unexpiredContract));

    // Mock isContractHit
    doReturn(true).when(appSpy).isContractHit(expiredContract);
    doReturn(false).when(appSpy).isContractHit(unexpiredContract);

    // Run logic
    appSpy.checkAndProcessContracts();

    // Verify payout was processed
    verify(userService, times(1)).addToUserBalance(owner, 200);

    // Verify transaction was created
    verify(transactionService, times(1)).createTransaction(any(Transaction.class));

    // Verify expiration flags
    assertTrue(expiredContract.getExpired());
    assertFalse(unexpiredContract.getExpired());
}
}
