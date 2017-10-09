package com.blowmymind.libgen.mainActivity_MVP;

import com.blowmymind.libgen.pojo.Book;

import java.util.ArrayList;

/**
 * Created by Sandeep on 08-10-2017.
 */

public interface PaginationCallbackInterface {
    void failed();

    void success(ArrayList<Book> newBooks);
}
