package main.mapper;

import main.dto.BookDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository("main.mapper.BookMapper")
public interface BookMapper {
    public ArrayList<BookDto> getAllBooks();
    public BookDto getBookByIsbn(@Param("isbn") String isbn);
    public int insertBook(BookDto bookDto);
}
