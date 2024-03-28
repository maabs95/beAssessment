INSERT INTO book(id,isbn,title,author) VALUES( '9cf9db036ab242eb87be5da4a0d08567','AA','Book A','Ali');
INSERT INTO book(id,isbn,title,author) VALUES( '1aab0637bcd74adbbbdab66ed392d099','AA','Book A','Ali');
INSERT INTO book(id,isbn,title,author) VALUES( '044bd8785390475091f746ea5a7c3c71','BB','Book B','Amir');
INSERT INTO borrower(userId,name,email) VALUES('lem','Lemur','lemur@gmail.com');
INSERT INTO borrower(userId,name,email) VALUES('lam','Lampard','lampard@gmail.com');
INSERT INTO borrower(userId,name,email) VALUES('haji','Hajime','hajime@gmail.com');
INSERT INTO borrowedBook(id,bookId,borrowerId,returned) VALUES('07f1082133ba4422a3dc162d6f40480f','9cf9db036ab242eb87be5da4a0d08567','lem','N');