/*
 * Copyright (C) 2014 Maigret Aurelien / Colin Julien
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.qinder.pref;

import fr.qinder.Q;

/**
 * Access to the AccountManager Android more earlier. This class required the
 * class fr.qinder.Q initialized. TODO: Comments this class.
 * 
 * @author Maigret Aurelien
 * @author Colin Julien
 */
public final class AccountManager {

	private static final String ACCOUNT_TYPE = "fr.qinder.AccountManager";

	/**
	 * Constructor, not called, because this is an Utility Class.
	 */
	private AccountManager() {
	}

	public static android.accounts.AccountManager getManager() {
		return android.accounts.AccountManager.get(Q.get());
	}

	public static android.accounts.Account getAccount(String name) {
		android.accounts.Account accounts[] = getManager().getAccountsByType(AccountManager.ACCOUNT_TYPE);
		if (name == null && accounts.length > 0) {
			return accounts[0];
		}
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
		if (account == null) {
			return null;
		}
		return account.name;
	}

	public static String getPassword() {
		android.accounts.Account account = getAccount();
		if (account == null) {
			return null;
		}
		return getManager().getPassword(account);
	}

	public static void setPassword(String password) {
		android.accounts.Account account = getAccount();
		if (account != null) {
			getManager().setPassword(account, password);
		}
	}

	public static void removeAll() {
		android.accounts.Account accounts[] = getManager().getAccountsByType(AccountManager.ACCOUNT_TYPE);
		for (android.accounts.Account account : accounts) {
			getManager().removeAccount(account, null, null);
		}
	}

	public static void add(String name, String password) {
		getManager().addAccountExplicitly(new android.accounts.Account(name, AccountManager.ACCOUNT_TYPE), password, null);
	}

}
