package com.libraryService.repo;

import com.libraryService.dto.BookIssuedDTO;
import com.libraryService.model.BookIssued;
import com.libraryService.repository.BookIssuedRepo;
import com.libraryService.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IssuedReleasedBooksRepoTest {

    @Autowired
    private BookIssuedRepo bookIssuedRepo;

    @Test
    public void saveAndDeletedBooksTest(){
        final BookIssuedDTO testIssuedDTO = TestUtil.getTestIssuedDTO();
        BookIssued saveBook = bookIssuedRepo.save( TestUtil.toIssuedEntity( testIssuedDTO ) );
        bookIssuedRepo.deleteById( saveBook.getId() );
    }


}
