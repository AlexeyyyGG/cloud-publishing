USE cloud_publishing;

CREATE TABLE categories
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE employees
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

CREATE TABLE publications
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(100)                   NOT NULL,
    publication_type ENUM ('Magazine', 'Newspaper') NOT NULL,
    theme            VARCHAR(100)                   NOT NULL,
    category_id      INT                            NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE articles
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(100)                   NOT NULL,
    publication_type ENUM ('Magazine', 'Newspaper') NOT NULL,
    content          TEXT                           NOT NULL,
    category_id      INT                            NOT NULL,
    author_id        INT                            NOT NULL,
    publication_id   INT                            NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories (id),
    FOREIGN KEY (author_id) REFERENCES employees (id),
    FOREIGN KEY (publication_id) REFERENCES publications (id)
);

CREATE TABLE article_authors
(
    article_id  INT NOT NULL,
    employee_id INT NOT NULL,
    PRIMARY KEY (article_id, employee_id),
    FOREIGN KEY (article_id) REFERENCES articles (id),
    FOREIGN KEY (employee_id) REFERENCES employees (id)
);

CREATE TABLE reviews
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    article_id INT NOT NULL,
    author INT NOT NULL,
    review_text TEXT NOT NULL,
    publish_flag BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (article_id) REFERENCES articles(id),
    FOREIGN KEY (author) REFERENCES employees(id)
);