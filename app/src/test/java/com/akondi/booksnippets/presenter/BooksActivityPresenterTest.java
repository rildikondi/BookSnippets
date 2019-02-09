package com.akondi.booksnippets.presenter;

import com.akondi.booksnippets.application.BooksSnippetsApplication;
import com.akondi.booksnippets.model.Book;
import com.akondi.booksnippets.repositories.BooksRepository;
import com.akondi.booksnippets.view.BooksActivityView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.ref.PhantomReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

//@RunWith(MockitoJUnitRunner.class)
public class BooksActivityPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule(); // allows different runners

    @Mock
    BooksRepository booksRepository;
    @Mock
    BooksActivityView view;

    private BooksActivityPresenter presenter;

    private final List<Book> MANY_BOOKS = Arrays.asList(new Book(), new Book(), new Book());

    @Before
    public void setUp() {
        //Schedulers.trampoline() refers to current thread(the same that runs the test)
        presenter = new BooksActivityPresenter(booksRepository, Schedulers.trampoline());
        presenter.setView(view);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        //reset for next test because they are static methods and they will have old values
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassBooksToView() {
        Mockito.when(booksRepository.getBooks()).thenReturn(Single.just(MANY_BOOKS));

        presenter.loadBooks();

        Mockito.verify(view).displayBooks(MANY_BOOKS);
    }

    @Test
    public void shouldHandleBooksFound() {
        Mockito.when(booksRepository.getBooks()).thenReturn(Single.<List<Book>>just(Collections.EMPTY_LIST));

        presenter.loadBooks();

        Mockito.verify(view).displayNoBooks();
    }

    @Test
    public void shouldHandleError() {
        Mockito.when(booksRepository.getBooks()).thenReturn(Single.<List<Book>>error(new Throwable("boom")));

        presenter.loadBooks();

        Mockito.verify(view).displayError();
    }
}