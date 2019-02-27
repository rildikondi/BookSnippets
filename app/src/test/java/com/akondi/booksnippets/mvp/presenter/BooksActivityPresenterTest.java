package com.akondi.booksnippets.mvp.presenter;

import com.akondi.booksnippets.base.BaseSinglePresenter;
import com.akondi.booksnippets.mvp.model.Book;
import com.akondi.booksnippets.mvp.presenter.BooksActivityPresenter;
import com.akondi.booksnippets.repositories.BooksRepository;
import com.akondi.booksnippets.mvp.view.BooksActivityView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.multibindings.ElementsIntoSet;
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

    BooksActivityPresenter presenter;

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
        //given
        //BooksActivityView view = new MockView();
        //BooksRepository booksRepository = new MockBooksRepository(true);
        //Mockito.when(booksRepository.getBooks()).thenReturn(MANY_BOOKS);
        Mockito.when(booksRepository.getBooks()).thenReturn(Single.just(MANY_BOOKS));

        // when
        presenter.loadBooks();

        //then
        //Assert.assertEquals(true, ((MockView) view).displayBooksWithBooksCalled);
        //Mockito.verify(view).displayBooks(MANY_BOOKS);
        Mockito.verify(presenter.getView()).displayBooks(MANY_BOOKS);
    }

    @Test
    public void shouldHandleBooksFound() {
        //given
        //BooksActivityView view = new MockView();
        //BooksRepository booksRepository = new MockBooksRepository(false);
        //Mockito.when(booksRepository.getBooks()).thenReturn(Collections.EMPTY_LIST);
        Mockito.when(booksRepository.getBooks()).thenReturn(Single.<List<Book>>just(Collections.EMPTY_LIST));
        //Mockito.when(booksRepository.getBooks()).thenReturn(DisposableSingleObserver.<List<Book>>(Collections.EMPTY_LIST));


        // when
        //BooksActivityPresenter presenter = new BooksActivityPresenter(view, booksRepository);
        presenter.loadBooks();

        //then
        //Assert.assertEquals(true, ((MockView) view).displayBooksWithNoBooksCalled);
        Mockito.verify(presenter.getView()).displayNoBooks();
    }

    @Test
    public void shouldHandleError() {
        //Mockito.when(booksRepository.getBooks()).thenThrow(new RuntimeException("boom"));
        //Mockito.when(booksRepository.getBooksReactively()).thenThrow(new RuntimeException("boom"));
        Mockito.when(booksRepository.getBooks()).thenReturn(Single.<List<Book>>error(new Throwable("boom")));

        presenter.loadBooks();

        Mockito.verify(presenter.getView()).displayError();
    }

//    private class MockView implements BooksActivityView {
//
//        boolean displayBooksWithBooksCalled;
//        boolean displayBooksWithNoBooksCalled;
//
//        @Override
//        public void displayBooks(List<Book> bookList) {
//
//            if (bookList.size() == 3) displayBooksWithBooksCalled = true;
//        }
//
//        @Override
//        public void displayNoBooks() {
//            displayBooksWithNoBooksCalled = true;
//        }
//    }

//    private class MockBooksRepository implements BooksRepository {
//
//        private boolean returnSomeBooks;
//
//        public MockBooksRepository(boolean returnSomeBooks) {
//            this.returnSomeBooks = returnSomeBooks;
//        }
//
//        @Override
//        public List<Book> getBooks() {
//
//            if (returnSomeBooks) {
//                return Arrays.asList(new Book(), new Book(), new Book());
//            } else {
//                return Collections.emptyList();
//            }
//        }
//    }
}