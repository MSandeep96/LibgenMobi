package com.blowmymind.libgen.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandeep on 06-10-2017.
 */

public class Book implements Parcelable{
    String id;
    String authors = "Not given";
    String title = "Not given";
    String publisher = "Not given";
    String year = "Not given";
    String pages = "Undefined";
    String language = "Not given";
    String size;
    String extension;
    ArrayList<String> mirrors;

    public String getId() {
        return id;
    }

    public String getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getYear() {
        return year;
    }

    public String getPages() {
        return pages + " pages";
    }

    public String getLanguage() {
        return language;
    }

    public String getSize() {
        return size;
    }

    public String getExtension() {
        return extension;
    }

    public ArrayList<String> getMirrors() {
        return mirrors;
    }


    /**
     * Oh boy
     *
     * @param element Element in the website
     */
    public Book(Element element) {
        try {
            id = ((TextNode) element.childNodes().get(0).childNodes().get(0)).getWholeText();
            authors = ((TextNode) element.childNodes().get(2).childNodes().get(0).childNodes().get(0)).getWholeText();
            title = parseTitle(element);
            publisher = ((TextNode) element.childNodes().get(6).childNodes().get(0)).getWholeText();
            year = ((TextNode) element.childNodes().get(8).childNodes().get(0)).getWholeText();
            pages = ((TextNode) element.childNodes().get(10).childNodes().get(0)).getWholeText();
            language = ((TextNode) element.childNodes().get(12).childNodes().get(0)).getWholeText();
            size = ((TextNode) element.childNodes().get(14).childNodes().get(0)).getWholeText();
            extension = ((TextNode) element.childNodes().get(16).childNodes().get(0)).getWholeText();
            mirrors = new ArrayList<>();
            for (int i = 18; i < 22; i++) {
                mirrors.add(
                        element.childNodes().get(i).childNodes().get(0).attributes().get("href")
                );
            }
        }catch(IndexOutOfBoundsException e){
            //Element isn't present. Use default value.
        }
    }

    /*
     * Handle special case where Title isn't the first link but instead there is some green text.
     */
    private String parseTitle(Element element) {
        List<Node> elements = element.childNodes().get(4).childNodes();
        if(elements.size()==1){
            return ((TextNode) elements.get(0).childNodes().get(0)).getWholeText();
        }else{
            return ((TextNode) elements.get(2).childNodes().get(0)).getWholeText();
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.authors);
        dest.writeString(this.title);
        dest.writeString(this.publisher);
        dest.writeString(this.year);
        dest.writeString(this.pages);
        dest.writeString(this.language);
        dest.writeString(this.size);
        dest.writeString(this.extension);
        dest.writeStringList(this.mirrors);
    }

    protected Book(Parcel in) {
        this.id = in.readString();
        this.authors = in.readString();
        this.title = in.readString();
        this.publisher = in.readString();
        this.year = in.readString();
        this.pages = in.readString();
        this.language = in.readString();
        this.size = in.readString();
        this.extension = in.readString();
        this.mirrors = in.createStringArrayList();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
