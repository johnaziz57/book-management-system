-- Table: public.person

DROP TABLE IF EXISTS public.person;

CREATE TABLE IF NOT EXISTS public.person
(
    borrowed_books_count integer NOT NULL,
    role                 smallint,
    id                   bigint  NOT NULL,
    name                 character varying(255) COLLATE pg_catalog."default",
    password             character varying(255) COLLATE pg_catalog."default",
    user_name            character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT person_pkey PRIMARY KEY (id),
    CONSTRAINT person_user_name_key UNIQUE (user_name),
    CONSTRAINT person_role_check CHECK (role >= 0 AND role <= 1)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.person
    OWNER to "user";

-- Table: public.author

DROP TABLE IF EXISTS public.author;

CREATE TABLE IF NOT EXISTS public.author
(
    id   bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT author_pkey PRIMARY KEY (id)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.author
    OWNER to "user";

-- Table: public.book

DROP TABLE IF EXISTS public.book;

CREATE TABLE IF NOT EXISTS public.book
(
    available_copies integer NOT NULL,
    id               bigint  NOT NULL,
    isbn             character varying(255) COLLATE pg_catalog."default",
    title            character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT book_pkey PRIMARY KEY (id),
    CONSTRAINT book_isbn_key UNIQUE (isbn)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.book
    OWNER to "user";

-- Table: public.book_author

DROP TABLE IF EXISTS public.book_author;

CREATE TABLE IF NOT EXISTS public.book_author
(
    author_id bigint NOT NULL,
    book_id   bigint NOT NULL,
    CONSTRAINT book_author_pkey PRIMARY KEY (author_id, book_id),
    CONSTRAINT fkbjqhp85wjv8vpr0beygh6jsgo FOREIGN KEY (author_id)
        REFERENCES public.author (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkhwgu59n9o80xv75plf9ggj7xn FOREIGN KEY (book_id)
        REFERENCES public.book (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.book_author
    OWNER to "user";

-- Table: public.borrow_log

DROP TABLE IF EXISTS public.borrow_log;

CREATE TABLE IF NOT EXISTS public.borrow_log
(
    book_id       bigint,
    borrowed_date timestamp(6) without time zone,
    id            bigint NOT NULL,
    returned_date timestamp(6) without time zone,
    user_id       bigint,
    CONSTRAINT borrow_log_pkey PRIMARY KEY (id),
    CONSTRAINT fkf2n7fa3xi2e99n7pbw40okqdr FOREIGN KEY (book_id)
        REFERENCES public.book (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fks8dyuxxparxtt5uqjgsog0peq FOREIGN KEY (user_id)
        REFERENCES public.person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.borrow_log
    OWNER to "user";