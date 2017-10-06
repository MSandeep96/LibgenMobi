package com.blowmymind.libgen;

import com.blowmymind.libgen.DataLayer.DataLayer;
import com.blowmymind.libgen.MainActivity_MVP.DataCallbackInterface;

import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DataLayerTest {

    int testMethod = -1;

    @Test
    public void scraping_isDone(){
        DataLayer dataLayer = new DataLayer("dan+brown");
        dataLayer.initSearch(new DataCallbackInterface() {

            @Override
            public void searchFailed() {
                testMethod = 0;
            }

            @Override
            public void searchSuccess(Document doc) {
                testMethod = 1;
            }
        });
        await().until(gotDocument());
    }

    private Callable<Boolean> gotDocument(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return testMethod==1;
            }
        };
    }

}