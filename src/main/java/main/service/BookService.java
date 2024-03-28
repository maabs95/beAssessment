package main.service;

import jakarta.annotation.Resource;
import main.dto.BookDto;
import main.dto.ResponseDto;
import main.mapper.BookMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service("main.service.BookService")
public class BookService {

    @Resource(name="main.mapper.BookMapper")
    BookMapper bookMapper;

    public ResponseDto getAllBooks(){
        ArrayList<BookDto> bookDtos = new ArrayList<>();
        String message = "";
        try{
            bookDtos = bookMapper.getAllBooks();
        } catch (Exception e){
            message = e.getMessage();
        }
        return new ResponseDto(message.isEmpty(),bookDtos,message,null);
    }

    public ResponseDto insertBook(BookDto bookDto){
        String message = "";
        try{
            BookDto isbn = bookMapper.getBookByIsbn(bookDto.getIsbn());
            if(bookDto.getIsbn() == null || bookDto.getIsbn().isEmpty()){
                message = "ISBN cannot be empty";
            } else if (isbn != null && isbn.getIsbn().equals(bookDto.getIsbn())
                    && (!isbn.getAuthor().equals(bookDto.getAuthor()) || !isbn.getTitle().equals(bookDto.getTitle()))){
                message = "Books with existing ISBN number must have the same title and same author";
            } else {
                bookDto.setId(UUID.randomUUID().toString().replace("-",""));
                bookMapper.insertBook(bookDto);
            }
        } catch (DataIntegrityViolationException | EmptyResultDataAccessException e){
            message = "Please check your input or empty fields";
        } catch(Exception e) {
            message = e.getMessage();
        }
        return new ResponseDto(message.isEmpty(),"",message,null);
    }

}
