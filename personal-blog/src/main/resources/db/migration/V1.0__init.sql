CREATE TABLE articles (
    id UUID NOT NULL,
    version BIGINT NOT NULL,
    title VARCHAR NOT NULL,
    publish_date DATE NOT NULL,
    content TEXT NOT NULL,
    PRIMARY KEY (id)
);
