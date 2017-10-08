package com.blowmymind.libgen.dataLayer;

import com.blowmymind.libgen.mainActivity_MVP.DataCallbackInterface;
import com.blowmymind.libgen.pojo.Book;
import com.blowmymind.libgen.pojo.ScrapedItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sandeep on 06-10-2017.
 */

public class DataLayer {

    private final ScrapedItem searchItem;

    /**
     * @param searchTerm Pass encoded search term
     */
    public DataLayer(ScrapedItem searchTerm){
        this.searchItem = searchTerm;
    }

    public void initSearch(final DataCallbackInterface callbackInterface){
        FetcherThread fetcherThread = new FetcherThread(callbackInterface);
        fetcherThread.start();
    }

    private class FetcherThread extends Thread{

        private final DataCallbackInterface callbackInterface;

        FetcherThread(DataCallbackInterface callbackInterface){
            this.callbackInterface = callbackInterface;
        }

        @Override
        public void run() {
            Document doc;
            try {
                doc = Jsoup
                        .connect(URLConstants.basic_url)
                        .userAgent(URLConstants.userAgent)
                        .data("req", searchItem.getEncodedSearchTerm())
                        .timeout(4000)
                        .get();
            } catch (IOException e) {

                if(callbackInterface!=null)
                    callbackInterface.searchFailed();
                return;
            }
            Elements trs = doc.select("table").get(2).select("tr");
            trs.remove(0);
            ArrayList<Book> books = new ArrayList<>();
            for(int i=0;i<trs.size();i++){
                books.add(new Book(trs.get(i)));
            }
            if(callbackInterface!=null)
                callbackInterface.searchSuccess(books);
        }
    }
}
