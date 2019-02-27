package com.akondi.booksnippets.di.component;

import com.akondi.booksnippets.di.module.BooksModule;
import com.akondi.booksnippets.di.scope.PerActivity;
import com.akondi.booksnippets.modules.books.BooksActivity;

import dagger.Component;


@PerActivity
@Component(modules = BooksModule.class, dependencies = AppComponent.class)
public interface BooksComponent {

    void inject(BooksActivity booksActivity);
}
