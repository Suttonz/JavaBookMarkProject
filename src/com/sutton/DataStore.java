package com.sutton;

import com.sutton.constants.BookGenre;
import com.sutton.constants.Gender;
import com.sutton.constants.MovieGenre;
import com.sutton.constants.UserType;
import com.sutton.entities.Bookmark;
import com.sutton.entities.User;
import com.sutton.entities.UserBookmark;
import com.sutton.mangers.BookmarkManager;
import com.sutton.mangers.UserManager;
import com.sutton.util.IOUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private static List<User> users = new ArrayList<>();
    private static List<List<Bookmark>> bookmarks = new ArrayList<>();
    private static List<UserBookmark> userBookmarks = new ArrayList<>();

    public static List<User> getUsers() {
        return users;
    }

    public static List<List<Bookmark>> getBookmarks() {
        return bookmarks;
    }

    public static void loadData() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/BookmarkDB";
        String user = "root";
        String password = "su90871228su";

        try (Connection connection = DriverManager.getConnection(url, user, password); Statement statement = connection.createStatement();) {

            loadUsers(statement);
            loadWebLinks(statement);
            loadMovies(statement);
            loadBooks(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void loadUsers(Statement statement) throws SQLException {

        UserManager userManager = UserManager.getInstance();
        String getUserQuery = "Select * from User";
        ResultSet resultSet = statement.executeQuery(getUserQuery);

        User user;
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Gender gender = Gender.values()[resultSet.getInt("gender_id")];
            UserType userType = UserType.values()[resultSet.getInt("user_type_id")];

            user = userManager.createUser(id, email, password, firstName, lastName, gender, userType);
            users.add(user);
        }

    }


    private static void loadWebLinks(Statement statement) throws SQLException {

        BookmarkManager bookmarkManager = BookmarkManager.getInstance();
        List<Bookmark> webLinkList = new ArrayList<>();

        String getWeblinksQuery = "Select * from WebLink";
        ResultSet resultSet = statement.executeQuery(getWeblinksQuery);

        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            String url = resultSet.getString("url");
            String host = resultSet.getString("host");
            Bookmark weblink = bookmarkManager.createWeblink(id, title, url, host);
            webLinkList.add(weblink);
        }

        bookmarks.add(webLinkList);
    }

    private static void loadMovies(Statement statement) throws SQLException {

        BookmarkManager bookmarkManager = BookmarkManager.getInstance();
        List<Bookmark> moviesList = new ArrayList<>();
        String getMovieQuery = "Select m.id, title, release_year, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors, movie_genre_id, imdb_rating" + " from Movie m, Actor a, Movie_Actor ma, Director d, Movie_Director md " + "where m.id = ma.movie_id and ma.actor_id = a.id and " + "m.id = md.movie_id and md.director_id = d.id group by m.id";

        ResultSet movies = statement.executeQuery(getMovieQuery);
        while (movies.next()) {
            long id = movies.getLong("id");
            String title = movies.getString("title");
            int releaseYear = movies.getInt("release_year");
            String[] cast = movies.getString("cast").split(",");
            String[] directors = movies.getString("directors").split(",");
            int genre_id = movies.getInt("movie_genre_id");
            MovieGenre genre = MovieGenre.values()[genre_id];
            double imdbRating = movies.getDouble("imdb_rating");
            Bookmark movie = bookmarkManager.createMovie(id, title, releaseYear, cast, directors, genre, imdbRating);
            moviesList.add(movie);
        }
        bookmarks.add(moviesList);
    }

    private static void loadBooks(Statement statement) throws SQLException {


        BookmarkManager bookmarkManager = BookmarkManager.getInstance();
        String getBookDataQuery = "Select b.id, b.title, b.publication_year, p.name, " +
                "GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, amazon_rating, created_date" +
                " from Book b, Publisher p, Author a, Book_Author ba " +
                "where b.publisher_id = p.id and b.id = ba.book_id and ba.author_id = a.id " +
                "group by b.id";

        ResultSet resultSet = statement.executeQuery(getBookDataQuery);
        List<Bookmark> booksList = new ArrayList<>();

        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            int publicationYear = resultSet.getInt("publication_year");
            String publisher = resultSet.getString("name");
            String[] authors = resultSet.getString("authors").split(",");
            int genre_id = resultSet.getInt("book_genre_id");
            BookGenre genre = BookGenre.values()[genre_id];
            double amazonRating = resultSet.getDouble("amazon_rating");

            Date createdDate = resultSet.getDate("created_date");
            System.out.println("createdDate: " + createdDate);
            Timestamp timeStamp = resultSet.getTimestamp(8);
            System.out.println("timeStamp: " + timeStamp);
            System.out.println("localDateTime: " + timeStamp.toLocalDateTime());

            Bookmark book = bookmarkManager.createBook(id, title, publicationYear, publisher, authors, genre, amazonRating);
            booksList.add(book);
        }

        bookmarks.add(booksList);
    }

    public static void add(UserBookmark userBookmark) {
        userBookmarks.add(userBookmark);
    }


}
