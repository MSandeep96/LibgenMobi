package com.blowmymind.libgen.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Contains the searchTerm, encodedSearchTerm, list of books and the current page we're on.
 */

public class ScrapedItem implements Parcelable{
    private String searchTerm;
    private String encodedSearchTerm;
    private ArrayList<Book> books = new ArrayList<>();
    private int currentPageNo;
    private boolean hasMoreItems;

    public ScrapedItem(String currentSearchTerm, String encodedSearchTerm) {
        searchTerm = currentSearchTerm;
        this.encodedSearchTerm = encodedSearchTerm;
        currentPageNo = 0;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public String getEncodedSearchTerm() {
        return encodedSearchTerm;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public boolean hasMoreItems() {
        return hasMoreItems;
    }

    public int getNextPage() {
        return ++currentPageNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setHasMoreItems(boolean hasMoreItems) {
        this.hasMoreItems = hasMoreItems;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.searchTerm);
        dest.writeString(this.encodedSearchTerm);
        dest.writeTypedList(this.books);
        dest.writeInt(this.currentPageNo);
    }

    private ScrapedItem(Parcel in) {
        this.searchTerm = in.readString();
        this.encodedSearchTerm = in.readString();
        this.books = in.createTypedArrayList(Book.CREATOR);
        this.currentPageNo = in.readInt();
    }

    public static final Creator<ScrapedItem> CREATOR = new Creator<ScrapedItem>() {
        @Override
        public ScrapedItem createFromParcel(Parcel source) {
            return new ScrapedItem(source);
        }

        @Override
        public ScrapedItem[] newArray(int size) {
            return new ScrapedItem[size];
        }
    };
}
