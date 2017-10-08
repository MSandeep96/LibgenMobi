package com.blowmymind.libgen.dataLayer;

import com.blowmymind.libgen.mainActivity_MVP.DataCallbackInterface;
import com.blowmymind.libgen.mainActivity_MVP.PaginationCallbackInterface;
import com.blowmymind.libgen.pojo.Book;
import com.blowmymind.libgen.pojo.ScrapedItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * TODO: Rework the fetcher thread to work with pagination too.
 */

public class DataLayer {

    private final ScrapedItem searchItem;

    private final int FETCHING_ITEM = 25;

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

    public void loadMore(PaginationCallbackInterface paginationCallbackInterface) {
        PaginationFetcher paginationFetcher = new PaginationFetcher(paginationCallbackInterface);
        paginationFetcher.start();
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
            if(trs.size()<25){
                searchItem.setHasMoreItems(false);
            }else{
                searchItem.setHasMoreItems(true);
            }
            for(int i=0;i<trs.size();i++){
                searchItem.getBooks().add(new Book(trs.get(i)));
            }
            if(callbackInterface!=null)
                callbackInterface.searchSuccess(searchItem);
        }
    }

    private class PaginationFetcher extends Thread{

        private final PaginationCallbackInterface callbackInterface;

        PaginationFetcher(PaginationCallbackInterface callbackInterface){
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
                        .data("page",String.valueOf(searchItem.getNextPage()))
                        .timeout(4000)
                        .get();
            } catch (IOException e) {

                if(callbackInterface!=null)
                    callbackInterface.failed();
                return;
            }
            Elements trs = doc.select("table").get(2).select("tr");
            trs.remove(0);
            int startIndex = searchItem.getBooks().size();
            if(trs.size()<25){
                searchItem.setHasMoreItems(false);
            }else{
                searchItem.setHasMoreItems(true);
            }
            for(int i=0;i<trs.size();i++){
                searchItem.getBooks().add(new Book(trs.get(i)));
            }
            if(callbackInterface!=null)
                callbackInterface.success(startIndex,searchItem.getBooks().size()-startIndex);
        }
    }
}