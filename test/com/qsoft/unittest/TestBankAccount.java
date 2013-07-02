package com.qsoft.unittest;

import java.util.List;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import junit.framework.TestCase;
import org.mockito.ArgumentCaptor;
import com.qsoft.bank.BankAccount;
import com.qsoft.bank.BankAccountDAO;
import com.qsoft.bank.BankAccountDTO;

public class TestBankAccount extends TestCase {
	BankAccount bAccount;
	BankAccountDAO bankAccountDAO;
	BankAccountDTO bAccountDto;

	protected void setUp() {
		bAccount = new BankAccount();
		bankAccountDAO = mock(BankAccountDAO.class);
		bAccount.setDao(bankAccountDAO);
		bAccountDto = bAccount.openAccount("123456789", 2L);
	}

	// 1
	public void testOpenAccount() {

		assertEquals(0, bAccountDto.getBalance(), 0.001);
		assertEquals("123456789", bAccountDto.getAccountNumber());
	}

	// 2
	public void testGetAccount() {

		ArgumentCaptor<BankAccountDTO> argumentDTO = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		ArgumentCaptor<Long> argumentTimeStamp = ArgumentCaptor
				.forClass(Long.class);
		verify(bankAccountDAO).save(argumentDTO.capture(),
				argumentTimeStamp.capture());
		assertEquals(argumentDTO.getAllValues().get(0).getBalance(), 0.0, 0.01);
		assertEquals(argumentDTO.getAllValues().get(0).getAccountNumber(),
				"123456789");

	}

	// 3
	public void testDeposit() {
		ArgumentCaptor<BankAccountDTO> argumentDTO = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		bAccount.deposit(bAccountDto, 10, "phuongnv save money");
		verify(bankAccountDAO).save(argumentDTO.capture());

		List<BankAccountDTO> savedAccountRecords = argumentDTO.getAllValues();

		assertEquals(savedAccountRecords.get(0).getBalance(), 10, 0.001);

		assertEquals(savedAccountRecords.get(0).getAccountNumber(),
				bAccountDto.getAccountNumber());

	}

	// 4
	public void testDepositWithTimeStamp() {
		ArgumentCaptor<BankAccountDTO> argumentDTO = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		ArgumentCaptor<Long> argumentTimeStamp = ArgumentCaptor
				.forClass(Long.class);

		bAccount.deposit(bAccountDto, 10, "phuongnv save money", 1L);
		verify(bankAccountDAO, times(2)).save(argumentDTO.capture(),
				argumentTimeStamp.capture());

		assertEquals(1L, argumentTimeStamp.getAllValues().get(1).longValue());
	}

	// 5
	public void testWithDraw() {
		ArgumentCaptor<BankAccountDTO> argumentDTO = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		// deposit
		bAccount.deposit(bAccountDto, 60, "phuongnv save money");
		verify(bankAccountDAO).save(argumentDTO.capture());
		List<BankAccountDTO> savedAccountRecords = argumentDTO.getAllValues();
		assertEquals(savedAccountRecords.get(0).getBalance(), 60, 0.001);

		// withdraw
		bAccount.withdraw(bAccountDto, -50, "Phuongnv rut tien");
		verify(bankAccountDAO, times(2)).save(argumentDTO.capture());
		List<BankAccountDTO> withDraw = argumentDTO.getAllValues();
		assertEquals(withDraw.get(1).getBalance(), 10, 0.001);
	}

	// 7
	public void testGetTransactionsOccurred() {
		//
		bAccount.getTransactionsOccurred(bAccountDto.getAccountNumber());
		verify(bankAccountDAO).getListTransactions(
				bAccountDto.getAccountNumber());
	}

	// 8
	public void testGetTransactionsOccurred2() {

		bAccount.getTransactionsOccurred(bAccountDto.getAccountNumber(), 1L, 5L);
		ArgumentCaptor<String> accountNumber = ArgumentCaptor
				.forClass(String.class);
		ArgumentCaptor<Long> argumentStartTime = ArgumentCaptor
				.forClass(Long.class);
		ArgumentCaptor<Long> argumentStopTime = ArgumentCaptor
				.forClass(Long.class);

		verify(bankAccountDAO).getListTransactions(accountNumber.capture(),
				argumentStartTime.capture(), argumentStopTime.capture());

		assertEquals(1L, argumentStartTime.getValue().longValue());
		assertEquals(5L, argumentStopTime.getValue().longValue());
	}

	// 9
	public void testGetNTransaction() {
		ArgumentCaptor<BankAccountDTO> argumentDTO = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		ArgumentCaptor<Integer> n = ArgumentCaptor.forClass(Integer.class);
		bAccount.getNTransactions(bAccountDto, 20);

		verify(bankAccountDAO).getNTransactions(argumentDTO.capture(),
				n.capture());
		assertEquals(20, n.getValue().intValue());
	}
	// 10 em refactor code theo test 10.nhung ko biet da dung chua a
}
