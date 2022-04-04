package com.sutton.mangers;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import com.sutton.constants.BookGenre;
import com.sutton.constants.KidFriendlyStatus;
import com.sutton.constants.MovieGenre;
import com.sutton.dao.BookmarkDao;
import com.sutton.entities.Book;
import com.sutton.entities.Bookmark;
import com.sutton.entities.Movie;
import com.sutton.entities.User;
import com.sutton.entities.UserBookmark;
import com.sutton.entities.Weblink;
import com.sutton.util.HttpConnect;
import com.sutton.util.IOUtil;

public class BookmarkManager {

	private static BookmarkManager instance = new BookmarkManager();
	private static BookmarkDao dao = new BookmarkDao();

	public List<List<Bookmark>> getBookmarks() {
		return dao.getBookmarks();
	}

	public static BookmarkManager getInstance() {
		return instance;
	}

	public Movie createMovie(long id, String title,  int releaseYear, String[] cast,
							 String[] directors, MovieGenre genre, double imdbRating) {

		Movie movie = new Movie();

		movie.setId(id);
		movie.setTitle(title);
		//movie.setProfileUrl(profileUrl);
		movie.setReleaseYear(releaseYear);
		movie.setCast(cast);
		movie.setDirectors(directors);
		movie.setGenre(genre);
		movie.setImdbRating(imdbRating);

		return movie;
	}

	public Book createBook(long id, String title, int publicationYear, String publisher,
						   String[] authors, BookGenre genre, double amazonRating) {

		Book book = new Book();

		book.setId(id);
		book.setTitle(title);
		//book.setProfileUrl(profileUrl);
		book.setPublicationYear(publicationYear);
		book.setPublisher(publisher);
		book.setAuthors(authors);
		book.setGenre(genre);
		book.setAmazonRating(amazonRating);

		return book;
	}

	public Weblink createWeblink(long id, String title, String url, String host) {

		Weblink weblink = new Weblink();

		weblink.setId(id);
		weblink.setTitle(title);
		weblink.setUrl(url);
		weblink.setHost(host);

		return weblink;
	}

	public void saveUserBookmark(User user, Bookmark bookmark) {
		UserBookmark userBookmark = new UserBookmark();
		userBookmark.setUser(user);
		userBookmark.setBookmark(bookmark);

		dao.saveBookmark(userBookmark);

	}

	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark item) {

		item.setKidFriendlyMarkedBy(user);
		item.setKidFriendlyStatus(kidFriendlyStatus);

		dao.updateKidFriendlyStatus(item);
		System.out.println("Kid-friendly status: " + kidFriendlyStatus + ",  mark by "+ user.getEmail() + item);
	}

	public void share(User user, Bookmark item) {
		item.setSharedBy(user);
		System.out.println("Data Shared by: ");
		
		if(item instanceof Book) {
			System.out.println(((Book) item).getItemData());
		} else if (item instanceof Weblink) {
			System.out.println(((Weblink) item).getItemData());
		}

		dao.shareByInfo(item);
	}

	

}
