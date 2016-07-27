package com.evc.transport;

import com.evc.models.Card;
import com.evc.models.Company;
import com.evc.models.History;
import com.evc.models.User;

import java.util.List;

public interface NetworkEventListener {
	public void onUserRegistered(String message);
	public void onUserLoggedIn(String message);
	public void onUserObjectReturned(User user);
    public void onUserObjectByMail(User user);
	public void onUserCompanies(List<Company> companies);
	public void onCompanyRegistered(String message);
	public void onUserUpdated(String message);
	public void onAllUsersDownloaded(List<User> users);

	public void onUserCardsDownloaded(List<Card> cards);
	public void onCardByIdDownloaded(Card card);
	public void onUserCardAdded(String message);

	public void onHistoryAdded(String message);
	public void onSentHistoryDownloaded(List<History> histories);
	public void onReceivedHistoryDownloaded(List<History> histories);
}
