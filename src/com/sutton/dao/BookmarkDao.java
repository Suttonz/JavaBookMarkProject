package com.sutton.dao;

import com.sutton.DataStore;
import com.sutton.entities.*;

import java.sql.*;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class BookmarkDao {

	public List<List<Bookmark>> getBookmarks() {
		return DataStore.getBookmarks();
	}

	public void saveBookmark(UserBookmark userBookmark) {

		String url = "jdbc:mysql://localhost:3306/BookmarkDB";
		String user = "root";
		String password = "su90871228su";

		try (Connection connection = DriverManager.getConnection(url, user, password);
			 Statement statement = connection.createStatement();) {

			 saveUserBookMark(userBookmark,statement);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void saveUserBookMark(UserBookmark userBookmark, Statement statement) throws SQLException {
		Bookmark bookmark = userBookmark.getBookmark();
		String bookMarkType = "";

		// construct the insert query based on the bookmark type
		if(bookmark instanceof Book) {
			bookMarkType = "Book";
		} else if (bookmark instanceof Movie){
			bookMarkType = "Movie";
		} else  {
			bookMarkType = "WebLink";
		}

		// equals - insert into user_book(user_id, book_id) values(user_id,book_id), book can be replaced
		StringBuilder query = new StringBuilder();
		String queryP1 = String.format("INSERT INTO User_%s(user_id,%s_id) VALUES(",
				bookMarkType,bookMarkType.toLowerCase());
		Long userID = userBookmark.getUser().getId();
		String queryP2 = ",";
		Long bookmarkID =  userBookmark.getBookmark().getId();
		String queryP3 = ")";

		query.append(queryP1).append(userID).append(queryP2).append(bookmarkID).append(queryP3);

		statement.executeUpdate(query.toString());
	}




	//In real world, we use SQL or hibernate to get weblink from database

	public List<Weblink> getAllWebLinks(){
		List<Weblink> result;
		List<Bookmark> allWeblinks =  DataStore.getBookmarks().get(0);
		result = allWeblinks.stream().map(bookmark -> (Weblink)bookmark).collect(Collectors.toList());
		return result;
	}

	public List<Weblink> getWebLinks(Weblink.DownloadStatus downloadStatus){
		List<Weblink> result;
		List<Weblink> allWeblinks =  getAllWebLinks();
		result = allWeblinks.stream()
				.filter(weblink -> weblink.getDownloadStatus().equals(downloadStatus))
				.collect(Collectors.toList());
		return result;
	}

	public void updateKidFriendlyStatus(Bookmark item) {
		int kidFriendlyStatusOrdinal = item.getKidFriendlyStatus().ordinal();
		long markUserID = item.getKidFriendlyMarkedBy().getId();

		var tableToUpdate = "";
		if(item instanceof Book){
			tableToUpdate = "Book";
		} else if(item instanceof Movie){
			tableToUpdate = "Movie";
		} else {
			tableToUpdate = "WebLink";
		}

		String url = "jdbc:mysql://localhost:3306/BookmarkDB";
		String user = "root";
		String password = "su90871228su";

		try (Connection connection = DriverManager.getConnection(url,user,password);
			 Statement statement = connection.createStatement()) {

			String updateKidsFriendlyStatusQuery = "UPDATE " + tableToUpdate
					+ " SET kid_friendly_status = " + kidFriendlyStatusOrdinal
					+ ", kid_friendly_marked_by = " + markUserID
					+ " WHERE id = " + item.getId();
			statement.executeUpdate(updateKidsFriendlyStatusQuery);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void shareByInfo(Bookmark item) {
		long shareUserID = item.getSharedBy().getId();

		var tableToUpdate = "";
		if(item instanceof Book){
			tableToUpdate = "Book";
		} else if(item instanceof Movie){
			tableToUpdate = "Movie";
		} else {
			tableToUpdate = "WebLink";
		}
		String url = "jdbc:mysql://localhost:3306/BookmarkDB";
		String user = "root";
		String password = "su90871228su";

		try (Connection connection = DriverManager.getConnection(url,user,password);
			 Statement statement = connection.createStatement()) {

			String updateKidsFriendlyStatusQuery = "UPDATE " + tableToUpdate
					+ " SET shared_by = " + shareUserID
					+ " WHERE id = " + item.getId();
			statement.executeUpdate(updateKidsFriendlyStatusQuery);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
