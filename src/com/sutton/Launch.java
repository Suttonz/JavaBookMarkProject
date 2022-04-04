package com.sutton;

import com.sutton.bgJobs.WebpageDownloaderTask;
import com.sutton.entities.Bookmark;
import com.sutton.entities.User;
import com.sutton.mangers.BookmarkManager;
import com.sutton.mangers.UserManager;
import java.util.List;

public class Launch {
   private static List<User> users;
   private static List<List<Bookmark>> bookmarks;
   
	public static void main(String[] args) {
		
		loadData();
		start();

		//Background jobs
		runDownloaderJob();
	}

	private static void runDownloaderJob() {
		WebpageDownloaderTask task = new WebpageDownloaderTask(true);
		(new Thread(task)).start();
	}


	private static void loadData() {
		System.out.println("Loading Data...");
		DataStore.loadData();
		
		users = UserManager.getInstance().getUsers();
		bookmarks = BookmarkManager.getInstance().getBookmarks();
		
	}

	
	private static void start() {

		for (User user : users) {
			View.browse(user, bookmarks);
		}

	}

	
	private static void printData() {
		System.out.println("Users:" + users);
		System.out.println("Bookmarks:" + bookmarks);
	}
	
}
