package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import main.dto.BookDto;
import main.dto.BorrowBookDto;
import main.dto.BorrowerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllBooks() throws Exception {
        MvcResult result = mockMvc.perform( MockMvcRequestBuilders
                        .get("/v1/getAllBooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        System.out.println("Success >> " + result.getResponse().getContentAsString());
    }

    ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    @Test
    public void testInsertBook() throws Exception {
        BookDto mockBook1 = new BookDto();
        mockBook1.setIsbn("abc");
        mockBook1.setTitle("title A");
        mockBook1.setAuthor("author A");
        String requestJson=ow.writeValueAsString(mockBook1);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/insertBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk()).andReturn();
        System.out.println("Success insert >> " + result.getResponse().getContentAsString());

        mockBook1.setIsbn("");
        requestJson=ow.writeValueAsString(mockBook1);
        result = testInsertBookBadRequest(requestJson);
        System.out.println("Missing ISBN >> " + result.getResponse().getContentAsString());

        mockBook1.setIsbn("abc");
        mockBook1.setAuthor("author B");
        requestJson=ow.writeValueAsString(mockBook1);
        result = testInsertBookBadRequest(requestJson);
        System.out.println("Same ISBN different author >> " + result.getResponse().getContentAsString());

        mockBook1.setIsbn("abcd");
        mockBook1.setAuthor("");
        requestJson=ow.writeValueAsString(mockBook1);
        result = testInsertBookBadRequest(requestJson);
        System.out.println("Empty author >> " + result.getResponse().getContentAsString());
    }

    private MvcResult testInsertBookBadRequest(String requestJson) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/v1/insertBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void testInsertBorrower() throws Exception {
        BorrowerDto mockBorrower1 = new BorrowerDto();
        mockBorrower1.setUserId("userA");
        mockBorrower1.setName("Kuroko");
        mockBorrower1.setEmail("kuroko@gmail.com");
        String requestJson=ow.writeValueAsString(mockBorrower1);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/insertBorrower")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk()).andReturn();
        System.out.println("Success insert >> " + result.getResponse().getContentAsString());

        mockBorrower1.setEmail("kurokogmail.com");
        requestJson=ow.writeValueAsString(mockBorrower1);
        result = testInsertBorrowerBadRequest(requestJson);
        System.out.println("Invalid email >> " + result.getResponse().getContentAsString());

        mockBorrower1.setEmail("kuroko@gmail.com");
        requestJson=ow.writeValueAsString(mockBorrower1);
        result = testInsertBorrowerBadRequest(requestJson);
        System.out.println("Duplicate User ID >> " + result.getResponse().getContentAsString());

        mockBorrower1.setUserId("");
        requestJson=ow.writeValueAsString(mockBorrower1);
        result = testInsertBorrowerBadRequest(requestJson);
        System.out.println("Empty User ID >> " + result.getResponse().getContentAsString());
    }

    private MvcResult testInsertBorrowerBadRequest(String requestJson) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/v1/insertBorrower")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void testGetBorrowerByUserId() throws Exception {
        MvcResult result = mockMvc.perform( MockMvcRequestBuilders
                        .get("/v1/getBorrowerByUserId?userId=userA")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        System.out.println("Success >> " + result.getResponse().getContentAsString());

        result = mockMvc.perform( MockMvcRequestBuilders
                        .get("/v1/getBorrowerByUserId?userId=kdkkdkfjsd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        System.out.println("User does not exist >> " + result.getResponse().getContentAsString());

        result = mockMvc.perform( MockMvcRequestBuilders
                        .get("/v1/getBorrowerByUserId?")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println("No userId parameter >> " + result.getResponse().getContentAsString());
    }

    @Test
    public void testBorrowBook() throws Exception {
        BorrowBookDto mockBorrowBook1 = new BorrowBookDto();
        mockBorrowBook1.setBookId("1aab0637bcd74adbbbdab66ed392d099");
        mockBorrowBook1.setBorrowerId("lam");
        String requestJson=ow.writeValueAsString(mockBorrowBook1);
        MvcResult result = testBorrowBookOk(requestJson);
        System.out.println("Success insert >> " + result.getResponse().getContentAsString());

        requestJson=ow.writeValueAsString(mockBorrowBook1);
        result = testBorrowBookOk(requestJson);
        System.out.println("Book occupied >> " + result.getResponse().getContentAsString());

        mockBorrowBook1.setBookId("");
        requestJson=ow.writeValueAsString(mockBorrowBook1);
        result = testBorrowBookOk(requestJson);
        System.out.println("Book ID does not exist >> " + result.getResponse().getContentAsString());

        mockBorrowBook1.setBookId("1aab0637bcd74adbbbdab66ed392d099");
        mockBorrowBook1.setBorrowerId("");
        requestJson=ow.writeValueAsString(mockBorrowBook1);
        result = testBorrowBookOk(requestJson);
        System.out.println("User ID does not exist >> " + result.getResponse().getContentAsString());
    }

    private MvcResult testBorrowBookOk(String requestJson) throws Exception{
        return mockMvc.perform(MockMvcRequestBuilders.post("/v1/borrowBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testUpdateReturnBook() throws Exception {
        BorrowBookDto mockBorrowBook1 = new BorrowBookDto();
        mockBorrowBook1.setBookId("9cf9db036ab242eb87be5da4a0d08567");
        mockBorrowBook1.setBorrowerId("lem");
        String requestJson=ow.writeValueAsString(mockBorrowBook1);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/v1/updateReturnBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk()).andReturn();
        System.out.println("Success insert >> " + result.getResponse().getContentAsString());
    }

}
