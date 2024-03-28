package main.controller;

import jakarta.annotation.Resource;
import main.dto.BookDto;
import main.dto.ResponseDto;
import main.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class BookController {

    @Resource(name = "main.service.BookService")
    BookService bookService;

    @RequestMapping(value = "/getAllBooks",method = RequestMethod.GET)
    public ResponseEntity<Object> getAllBooks() {
        return new ResponseEntity<>(
                bookService.getAllBooks(), HttpStatus.OK);
    }

    @RequestMapping(value = "/insertBook",method = RequestMethod.POST)
    public ResponseEntity<Object> insertBook(@RequestBody BookDto bookDto) {
        ResponseDto responseDto = bookService.insertBook(bookDto);
        return new ResponseEntity<>(responseDto,
                responseDto.getSuccess()? HttpStatus.OK: HttpStatus.BAD_REQUEST);
    }

}
