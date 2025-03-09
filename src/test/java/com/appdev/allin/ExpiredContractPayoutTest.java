package com.appdev.allin;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;

import com.appdev.allin.contract.Contract;
import com.appdev.allin.user.User;
import com.appdev.allin.contract.ContractRepo;
import com.appdev.allin.data.ContractFactory;
import com.appdev.allin.data.UserFactory;
import com.appdev.allin.user.UserService;

public class ExpiredContractPayoutTest {

    @Mock
    private ContractRepo contractRepo;

    @Mock
    private UserService userService;

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
        owner.setId(1); // Set an ID for testing consistency

        // Create contracts using the factory
        expiredContract = contractFactory.createRandomContract(owner);
        expiredContract.setExpirationTime(expiredContract.getCreationTime().plusDays(-10)); // Already expired
        expiredContract.setExpired(false); // Not yet marked expired
        expiredContract.setValue(200.0); // Payout value

        unexpiredContract = contractFactory.createRandomContract(owner);
        unexpiredContract.setExpirationTime(LocalDate.now().plusDays(30)); // Not expired yet
        unexpiredContract.setExpired(false);

        // Mock contract repository to return the contracts
        when(contractRepo.findAll()).thenReturn(Arrays.asList(expiredContract, unexpiredContract));
    }

    @Test
    public void testExpiredContractPayout() {
        // Mock isContractHit to return true only for the expired contract
        Application appSpy = spy(application);
        doReturn(true).when(appSpy).isContractHit(expiredContract);
        doReturn(false).when(appSpy).isContractHit(unexpiredContract);

        // Run the checkAndProcessContracts method
        appSpy.checkAndProcessContracts();

        // Verify that the expired contract was processed
        verify(userService).addToUserBalance(owner.getId(), 200.0); // Ensure payout matches contract value
        assertTrue(expiredContract.getExpired()); // Contract should be marked as expired

        // Verify that the unexpired contract was not processed
        verify(userService, times(1)).addToUserBalance(anyInt(), anyDouble()); // Ensure only one payout
        assertFalse(unexpiredContract.getExpired()); // Unexpired contract should remain unexpired
    }

}
