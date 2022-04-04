package com.sutton.entities;

import java.util.Arrays;

import com.sutton.constants.BookGenre;
import com.sutton.partners.Shareable;

public class Book extends Bookmark implements Shareable {

	private int publicationYear;
	private String publisher;
	private String[] authors;
	private BookGenre genre;
	private double amazonRating;

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public BookGenre getGenre() {
		return genre;
	}

	public void setGenre(BookGenre genre) {
		this.genre = genre;
	}

	public double getAmazonRating() {
		return amazonRating;
	}

	public void setAmazonRating(double amazonRating) {
		this.amazonRating = amazonRating;
	}

	@Override
	public String toString() {
		return "Book [publicationYear=" + publicationYear + ", publisher=" + publisher + ", authors="
				+ Arrays.toString(authors) + ", genre=" + genre + ", amazonRating=" + amazonRating + "]";
	}

	@Override
	public boolean isKidFriendlyEligible() {
		if (genre.equals(BookGenre.SELF_HELP) || genre.equals(BookGenre.PHILOSOPHY)) {
			return false;
		}
		return true;
	}

	@Override
	public String getItemData() {
		
     StringBuilder itemData = new StringBuilder();
     
     itemData.append("<item>");
     itemData.append("<type>Book</type>");
     itemData.append("<title>").append(getTitle()).append("</title>");
     itemData.append("<authors>").append(combineAuthors(authors)).append("</authors>");
     itemData.append("<publisher>").append(publisher).append("</publisher>");
     itemData.append("<publicationYear>").append(publicationYear).append("</publicationYear>");
     itemData.append("<genre>").append(genre).append("</genre>");
     itemData.append("<amazonRating>").append(amazonRating).append("</amazonRating>");
     itemData.append("</item>");
     
     return itemData.toString();
	}

	private String combineAuthors(String[] authors) {
		StringBuilder result = new StringBuilder();
		
		for(String author: authors) {
			result.append(author + ",");
		}
		
		return result.toString();
	}
	
	
}
