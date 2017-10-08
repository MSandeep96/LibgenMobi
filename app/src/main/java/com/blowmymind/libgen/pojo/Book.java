package com.blowmymind.libgen.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.List;

/**
 * The POJO implementation of a book from Libgen with all details.
 */
public class Book implements Parcelable {
    private String id;
    private String authors = "Not given";
    private String title = "Not given";
    private String publisher = "Not given";
    private String year = "Not given";
    private String pages = "Not given";
    private String language = "Not given";
    private String size;
    private String extension;
    private ArrayList<String> mirrors;

    public String getId() {
        return id;
    }

    public String getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getMirrors() {
        return mirrors;
    }

    public String getDetails() {
        return "Extension: " + extension + "\n" +
                "Publisher: " + publisher + "\n" +
                "Language: " + language + "\n" +
                "Pages: " + pages + "\n" +
                "Size: " + size;
    }

    /**
     * Oh boy
     *
     * @param element Element in the website
     */
    public Book(Element element) {

        try {
            id = ((TextNode) element.childNodes().get(0).childNodes().get(0)).getWholeText();
        } catch (IndexOutOfBoundsException e) {
            //Element isn't present. Use default value.
        }

        try {
            authors = ((TextNode) element.childNodes().get(2).childNodes().get(0).childNodes().get(0)).getWholeText();
        } catch (IndexOutOfBoundsException e) {
            //Element isn't present. Use default value.
        }

        try {
            title = parseTitle(element);
        } catch (IndexOutOfBoundsException e) {
            //Element isn't present. Use default value.
        }

        try {
            publisher = ((TextNode) element.childNodes().get(6).childNodes().get(0)).getWholeText();
        } catch (IndexOutOfBoundsException e) {
            //Element isn't present. Use default value.
        }

        try {
            year = ((TextNode) element.childNodes().get(8).childNodes().get(0)).getWholeText();
        } catch (IndexOutOfBoundsException e) {
            //Element isn't present. Use default value.
        }

        try {
            pages = ((TextNode) element.childNodes().get(10).childNodes().get(0)).getWholeText();
        } catch (IndexOutOfBoundsException e) {
            //Element isn't present. Use default value.
        }

        try {
            language = ((TextNode) element.childNodes().get(12).childNodes().get(0)).getWholeText();
        } catch (IndexOutOfBoundsException e) {
            //Element isn't present. Use default value.
        }
        try {
            size = ((TextNode) element.childNodes().get(14).childNodes().get(0)).getWholeText();
        } catch (IndexOutOfBoundsException e) {
            //Element isn't present. Use default value.
        }

        try {
            extension = ((TextNode) element.childNodes().get(16).childNodes().get(0)).getWholeText();
        } catch (IndexOutOfBoundsException e) {
            //Element isn't present. Use default value.
        }

        mirrors = new ArrayList<>();
        for (int i = 18; i < 22; i++) {
            try {
                mirrors.add(
                        element.childNodes().get(i).childNodes().get(0).attributes().get("href")
                );
            } catch (IndexOutOfBoundsException e) {
                //Element isn't present. Use default value.
            }
        }
    }

    /*
     * Handle special case where Title isn't the first link but instead there is some green text.
     */
    private String parseTitle(Element element) throws IndexOutOfBoundsException {
        List<Node> elements = element.childNodes().get(4).childNodes();
        if (elements.size() == 1) {
            return ((TextNode) elements.get(0).childNodes().get(0)).getWholeText();
        } else {
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

    private Book(Parcel in) {
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