package fr.qinder.pref;

import fr.qinder.Q;

public class AccountManager {
	private static String TYPE = "fr.qinder.AccountManager";

	public static android.accounts.AccountManager getManager() {
		return android.accounts.AccountManager.get(Q.get());
	}

	public static android.accounts.Account getAccount(String name) {
		android.accounts.Account accounts[] = getManager().getAccountsByType(AccountManager.TYPE);
		if (name == null && accounts.length > 0)
			return accounts[0];
        for (android.accounts.Account account : accounts) {
            if (account.name.equalsIgnoreCase(name)) {
                return account;
            }
        }
		return null;
	}

	public static android.accounts.Account getAccount() {
		return getAccount(null);
	}

	public static String getName() {
		android.accounts.Account account = getAccount();
		if (account == null)
			return null;
		return account.name;
	}

	public static String getPassword() {
		android.accounts.Account account = getAccount();
		if (account == null)
			return null;
		return getManager().getPassword(account);
	}

	public static void setPassword(String password) {
		android.accounts.Account account = getAccount();
		if (account != null)
			getManager().setPassword(account, password);
	}

	public static void removeAll() {
		android.accounts.Account accounts[] = getManager().getAccountsByType(AccountManager.TYPE);
        for (android.accounts.Account account : accounts) {
            getManager().removeAccount(account, null, null);
        }
	}

	public static void add(String name, String password) {
		getManager().addAccountExplicitly(new android.accounts.Account(name, AccountManager.TYPE), password, null);
	}
}
