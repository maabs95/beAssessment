package main.controller;

import jakarta.annotation.Resource;
import main.dto.BorrowBookDto;
import main.dto.BorrowerDto;
import main.dto.ResponseDto;
import main.service.BorrowerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class BorrowerController {

    @Resource(name = "main.service.BorrowerService")
    BorrowerService borrowerService;

    @RequestMapping(value = "/insertBorrower",method = RequestMethod.POST)
    public ResponseEntity<Object> insertBorrower(@RequestBody BorrowerDto borrowerDto) {
        ResponseDto result = borrowerService.insertBorrower(borrowerDto);
        return new ResponseEntity<>(result,
                result.getSuccess()? HttpStatus.OK: HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/getBorrowerByUserId",method = RequestMethod.GET)
    public ResponseEntity<Object> getBorrowerByUserId(@RequestParam String userId) {
        return new ResponseEntity<>(borrowerService.getBorrowerByUserId(userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/borrowBook",method = RequestMethod.POST)
    public ResponseEntity<Object> borrowBook(@RequestBody BorrowBookDto borrowBookDto) {
        ResponseDto responseDto = borrowerService.insertBorrowBook(borrowBookDto);
        return new ResponseEntity<>(responseDto,responseDto.getHttpStatus());
    }

    @RequestMapping(value = "/updateReturnBook",method = RequestMethod.PUT)
    public ResponseEntity<Object> updateReturnBook(@RequestBody BorrowBookDto borrowBookDto) {
        ResponseDto responseDto = borrowerService.updateReturnBook(borrowBookDto);
        return new ResponseEntity<>(responseDto,
                responseDto.getSuccess()? HttpStatus.OK: HttpStatus.BAD_REQUEST);
    }

}
