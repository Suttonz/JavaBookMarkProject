package com.sutton;

import com.sutton.entities.Bookmark;
import com.sutton.entities.User;
import com.sutton.partners.Shareable;
import com.sutton.constants.KidFriendlyStatus;
import com.sutton.constants.UserType;
import com.sutton.controllers.*;

import java.util.List;

public class View {

	public static void browse(User user, List<List<Bookmark>> bookmarks) {

		System.out.println("\n" + user.getEmail() + " is browsing items");
		//int userBookmarkCount = 0;

		for (List<Bookmark> eachType : bookmarks) {

			for (Bookmark item : eachType) {

				//if (userBookmarkCount < DataStore.USER_BOOKMARK_LIMIT) {
					boolean wantBookMark = makeBookmarkDecision(item);

					if (wantBookMark) {
						//userBookmarkCount++;
						BookmarkController.getInstance().saveUserBookmark(user, item);
						System.out.println("New item bookmarked -- " + item);

					}
				//}

				if (user.getUserType().equals(UserType.CHIEF_EDITOR) || user.getUserType().equals(UserType.EDITOR)) {

					// kidsfriendly
					if (item.isKidFriendlyEligible() && item.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {

						KidFriendlyStatus kidFriendlyStatus = makeKidFriendlyStatusDecision(item);

						if (!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)) {

							BookmarkController.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, item);
						}

					}

					// share with partner
					if (item.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED) && item instanceof Shareable) {

						if(makeShareDecision(item)) {
							BookmarkController.getInstance().share(user,item);
						}
					}

				}

			}
		}

 }

	private static boolean makeShareDecision(Bookmark item) {
		return Math.random() < 0.6 ? true : false;
	}

	private static KidFriendlyStatus makeKidFriendlyStatusDecision(Bookmark bookmark) {
		double randomNum = Math.random();
		return randomNum < 0.4 ? KidFriendlyStatus.APPROVED
				: (randomNum >= 0.4 || randomNum < 0.8) ? KidFriendlyStatus.REJECTED : KidFriendlyStatus.UNKNOWN;
	}

	private static boolean makeBookmarkDecision(Bookmark item) {
		return Math.random() < 0.5 ? true : false;
	}

}
