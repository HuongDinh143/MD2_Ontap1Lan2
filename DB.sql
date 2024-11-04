create database quanlythuvien1;
use quanlythuvien1;

create table booktype(
    type_id int primary key auto_increment,
    type_name varchar(50) not null unique ,
    type_description text,
    is_deleted bit
);

create table book(
    book_id int primary key auto_increment,
    book_name varchar(100) not null unique ,
    title varchar(200) not null ,
    author varchar(200) not null ,
    total_pages int not null check(total_pages>0),
    content text not null ,
    publisher varchar(100) not null ,
    price decimal(10,2) not null check(price>0),
    type_id int not null ,
    foreign key (type_id) references booktype(type_id),
    is_deleted_book bit default 0
);
DELIMITER &&
CREATE PROCEDURE find_all_book_type()
BEGIN
    SELECT *FROM booktype;
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE find_all_book_type_true()
BEGIN
    SELECT * FROM booktype where is_deleted = false;
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE find_type_book_by_id(type_book_id_in int)
BEGIN
    SELECT * FROM booktype WHERE type_id = type_book_id_in;
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE save_book_type(
    type_book_name_in varchar(50),
    type_book_des_in text,
    type_isDeleted_in bit
)
BEGIN
    insert into booktype (type_name, type_description, is_deleted)
        values (type_book_name_in, type_book_des_in, type_isDeleted_in);
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE update_book_type(
    book_type_id_in int,
    type_book_name_in varchar(50),
    type_book_des_in text,
    type_isDeleted_in bit
)
BEGIN
    UPDATE booktype set type_name = type_book_name_in,
                        type_description = type_book_des_in,
                        is_deleted = type_isDeleted_in
    WHERE type_id = book_type_id_in;
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE  delete_book_type(type_id_in int)
BEGIN
    UPDATE booktype
        set is_deleted = true
    WHERE type_id = type_id_in;
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE  find_all_book()
BEGIN
    SELECT * FROM book;
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE  find_all_book_exit()
BEGIN
    SELECT * FROM book WHERE is_deleted_book = false;
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE  save_book(
    book_name_in varchar(100),
    title_in varchar(200),
    author_in varchar(200),
    pages_in int,
    content_in text,
    pub_in varchar(100),
    price_in decimal(10,2),
    type_id_in int
)
BEGIN
    INSERT INTO book (book_name, title, author, total_pages,
                      content, publisher, price, type_id)
        values (book_name_in,title_in,author_in,pages_in,
                content_in,pub_in,price_in,type_id_in);

END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE  update_book(
    book_id_in int,
    book_name_in varchar(100),
    title_in varchar(200),
    author_in varchar(200),
    pages_in int,
    content_in text,
    pub_in varchar(100),
    price_in decimal(10,2),
    type_id_in int
)
BEGIN
    UPDATE book
        SET book_name = book_name_in,
            title = title_in,
            author = author_in,
            total_pages = pages_in,
            content = content_in,
            publisher = pub_in,
            price = price_in,
            type_id = type_id_in
    WHERE type_id = book_id_in;
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE delete_book(book_id_in int)
BEGIN
    UPDATE book SET is_deleted_book = TRUE
    WHERE type_id = book_id_in;
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE sort_book_by_pice_desc()
BEGIN
    SELECT * FROM book WHERE is_deleted_book = FALSE
    ORDER BY price DESC ;
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE  find_book_by_name_or_by_content(search_value text)
BEGIN
    SELECT * FROM book
             WHERE book_name LIKE CONCAT('%',search_value,'%') OR
                   content LIKE CONCAT('%',search_value,'%');
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE statistic_book_by_type_id(type_id_in int, out cnt_books int)
BEGIN
    SET cnt_books =(SELECT COUNT(book_id) FROM book WHERE type_id = type_id_in);
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE find_book_by_id(book_id_in int)
BEGIN
    SELECT * FROM book WHERE book_id = book_id_in;
END &&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE is_Book_Name_Duplicate(
    IN book_id_in INT,
    IN book_name_in VARCHAR(100)
)
BEGIN
    SELECT COUNT(*) AS duplicate_count
    FROM book
    WHERE book_name = book_name_in
      AND book_id != book_id_in;
END &&
DELIMITER ;


