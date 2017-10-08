package com.blowmymind.libgen.mainActivity_MVP;

import com.blowmymind.libgen.pojo.ScrapedItem;

/**
 * Created by Sandeep on 06-10-2017.
 */

public interface MainCallbackInterface {
    void newSearchTerm(ScrapedItem currentSearchTerm);
}
