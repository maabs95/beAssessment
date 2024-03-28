package main.service;

import jakarta.annotation.Resource;
import main.dto.BorrowBookDto;
import main.dto.BorrowerDto;
import main.dto.ResponseDto;
import main.mapper.BorrowerMapper;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("main.service.BorrowerService")
public class BorrowerService {

    @Resource(name="main.mapper.BorrowerMapper")
    BorrowerMapper borrowerMapper;

    public ResponseDto insertBorrower(BorrowerDto borrowerDto){
        String message = "";
        try{
            Pattern emailAddressRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = emailAddressRegex.matcher(borrowerDto.getEmail());
            if(matcher.matches()) {
                borrowerMapper.insertBorrower(borrowerDto);
            } else {
                message = "Please enter a valid email";
            }
        } catch(DuplicateKeyException e) {
            message = "User ID already exist";
        } catch (DataIntegrityViolationException e){
            message = "Please check your input or empty fields";
        } catch (Exception e){
            message = e.getMessage();
        }
        return new ResponseDto(message.isEmpty(),"",message,null);
    }

    public ResponseDto getBorrowerByUserId(String userId){
        BorrowerDto borrowerDto = null;
        String message = "";
        boolean success = true;
        try {
            borrowerDto = borrowerMapper.getBorrowerByUserId(userId);
            if (borrowerDto == null || borrowerDto.getUserId().isEmpty()) {
                message = "User ID does not exist";
            }
        } catch (Exception e){
            success = false;
            message = e.getMessage();
        }
        return new ResponseDto(success,borrowerDto == null? "": borrowerDto,message,null);
    }

    public ResponseDto insertBorrowBook(BorrowBookDto borrowBookDto){
        String message = "";
        boolean success = false;
        borrowBookDto.setId(UUID.randomUUID().toString().replace("-",""));
        borrowBookDto.setReturned('N');
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            if(isNullOrEmpty(borrowerMapper.checkBookId(borrowBookDto.getBookId()))){
                message = "BookId does not exist";
            } else if(isNullOrEmpty(borrowerMapper.checkUserId(borrowBookDto.getBorrowerId()))){
                message = "UserId does not exist";
            }

            String borrowBook = borrowerMapper.checkBorrowedBook(borrowBookDto.getBookId());
            if(isNotNullAndNotEmpty(borrowBook)){
                message = "This book is being borrowed by " + borrowBook;
            }
            if(message.isEmpty()){
                success = true;
                borrowerMapper.insertBorrowBook(borrowBookDto);
            }
        } catch (DataIntegrityViolationException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            message = "Please check your input";
        } catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            message = e.getMessage();
        }
        return new ResponseDto(success,"",message,httpStatus);
    }

    public ResponseDto updateReturnBook(BorrowBookDto borrowBookDto){
        String message = "";
        try{
            borrowerMapper.updateReturnBook(borrowBookDto.getBookId(), borrowBookDto.getBorrowerId());
        } catch(Exception e) {
            message = e.getMessage();
        }
        return new ResponseDto(message.isEmpty(),"",message,null);
    }

    public boolean isNullOrEmpty(String a){
        return a == null || a.isEmpty();
    }

    public boolean isNotNullAndNotEmpty(String a){
        return a != null && !a.isEmpty();
    }
}
