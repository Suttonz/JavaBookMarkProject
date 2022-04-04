package com.sutton.controllers;

import com.sutton.constants.Gender;
import com.sutton.constants.KidFriendlyStatus;
import com.sutton.entities.Bookmark;
import com.sutton.entities.User;
import com.sutton.mangers.BookmarkManager;

public class BookmarkController {

	private BookmarkController() {
	}

	private static BookmarkController instance = new BookmarkController();

	public static BookmarkController getInstance() {
		return instance;
	}

	private static BookmarkManager bookmarkManager = BookmarkManager.getInstance();
	public void saveUserBookmark(User user, Bookmark bookmark) {
		bookmarkManager.saveUserBookmark(user, bookmark);
	}

	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark item) {
		bookmarkManager.setKidFriendlyStatus(user, kidFriendlyStatus, item);

	}

	public void share(User user, Bookmark item) {
		bookmarkManager.share(user, item);
	}

}
