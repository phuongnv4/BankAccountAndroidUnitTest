package com.qsoft.bank;

public class BankAccount {
	private BankAccountDAO bankAccountDAO;

	public BankAccountDTO openAccount(String accountNumber,Long l) {
		BankAccountDTO bAccountDto = new BankAccountDTO();
		bAccountDto.setAccountNumber(accountNumber);
		bankAccountDAO.save(bAccountDto,l);
		return bAccountDto;
	}

	public void setDao(BankAccountDAO bankAccountDAO) {

		this.bankAccountDAO = bankAccountDAO;
	}

	public void deposit(BankAccountDTO bAccountDto, int amount,
			String descripton) {
		bAccountDto.setBalance(amount + bAccountDto.getBalance());
		bankAccountDAO.save(bAccountDto);
	}

	public void deposit(BankAccountDTO bAccountDto, int i, String string, long l) {
		bankAccountDAO.save(bAccountDto, l);
	}

	public void withdraw(BankAccountDTO bAccountDto, int amount, String desc) {
		bAccountDto.setBalance(amount + bAccountDto.getBalance());
		bankAccountDAO.save(bAccountDto);
	}

	public void getTransactionsOccurred(String accountNumber) {
		bankAccountDAO.getListTransactions(accountNumber);
	}

	public void getTransactionsOccurred(String accountNumber, Long startTime,
			Long stopTime) {
		bankAccountDAO.getListTransactions(accountNumber, startTime, stopTime);
	}

	public void getNTransactions(BankAccountDTO bAccountDto, int n) {
		bankAccountDAO.getNTransactions(bAccountDto, n);
	}

}
