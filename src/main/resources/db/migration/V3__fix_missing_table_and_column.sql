ALTER TABLE "book"
    RENAME COLUMN published_date TO publish_date;

CREATE TABLE IF NOT EXISTS public.book_statistics
(
    average_borrow_time integer NOT NULL,
    borrow_count        integer NOT NULL,
    id                  bigint  NOT NULL,
    isbn                character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT book_statistics_pkey PRIMARY KEY (id),
    CONSTRAINT book_statistics_isbn_key UNIQUE (isbn),
    CONSTRAINT fkd40o42p7myqpsuy3wpi43qrb FOREIGN KEY (isbn)
        REFERENCES public.book (isbn) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.book_statistics
    OWNER to "user";
-- Index: idxk8i1cooessjgu87526jsg36tn

-- DROP INDEX IF EXISTS public.idxk8i1cooessjgu87526jsg36tn;

CREATE INDEX IF NOT EXISTS idxk8i1cooessjgu87526jsg36tn
    ON public.book_statistics USING btree
        (borrow_count ASC NULLS LAST)
    TABLESPACE pg_default;


create sequence author_seq start with 1 increment by 50;
create sequence book_seq start with 1 increment by 50;
create sequence book_statistics_seq start with 1 increment by 50;
create sequence borrow_log_seq start with 1 increment by 50;
create sequence person_seq start with 1 increment by 50;