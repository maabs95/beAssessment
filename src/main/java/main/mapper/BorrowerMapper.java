package main.mapper;

import main.dto.BorrowBookDto;
import main.dto.BorrowerDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("main.mapper.BorrowerMapper")
public interface BorrowerMapper {

    public int insertBorrower(BorrowerDto borrowerDto);
    public BorrowerDto getBorrowerByUserId(@Param("userId") String userId);
    public int insertBorrowBook(BorrowBookDto borrowBookDto);
    public String checkBookId(@Param("id") String id);
    public String checkUserId(@Param("userId") String userId);
    public String checkBorrowedBook(@Param("bookId") String bookId);
    public int updateReturnBook(@Param("bookId") String bookId, @Param("borrowerId") String borrowerId);

}
