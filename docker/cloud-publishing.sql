USE cloud_publishing;

CREATE TABLE EMPLOYEES
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    first_name      VARCHAR(100)                  NOT NULL,
    last_name       VARCHAR(100)                  NOT NULL,
    middle_name     VARCHAR(100)                  NOT NULL,
    email           VARCHAR(100)                  NOT NULL,
    password        VARCHAR(100)                  NOT NULL,
    gender          ENUM ('male', 'female')       NOT NULL,
    birth_year      YEAR                          NOT NULL,
    address         VARCHAR(100)                  NOT NULL,
    education       VARCHAR(100)                  NOT NULL,
    type            ENUM ('Journalist', 'Editor') NOT NULL,
    is_chief_editor BOOLEAN DEFAULT FALSE,
    UNIQUE KEY unique_email (email)
);

CREATE TABLE PUBLICATIONS
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(100)                   NOT NULL,
    publication_type ENUM ('Magazine', 'Newspaper') NOT NULL,
    theme            VARCHAR(100)                   NOT NULL,
    category         VARCHAR(100)                   NOT NULL
);

CREATE TABLE ARTICLES
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(100)                   NOT NULL,
    publication_type ENUM ('Magazine', 'Newspaper') NOT NULL,
    category         VARCHAR(100)                   NOT NULL,
    author_id        INT                            NOT NULL,
    publication_id   INT                            NOT NULL,
    FOREIGN KEY (author_id) REFERENCES EMPLOYEES (id),
    FOREIGN KEY (publication_id) REFERENCES PUBLICATIONS (id)
);

CREATE TABLE ARTICLE_AUTHORS
(
    article_id  INT NOT NULL,
    employee_id INT NOT NULL,
    PRIMARY KEY (article_id, employee_id),
    FOREIGN KEY (article_id) REFERENCES ARTICLES (id),
    FOREIGN KEY (employee_id) REFERENCES EMPLOYEES (id)
);