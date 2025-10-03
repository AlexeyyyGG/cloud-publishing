USE cloud_publishing;

CREATE TABLE EMPLOYEES (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          middle_name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          gender ENUM('male', 'female') NOT NULL,
                          birth_year YEAR NOT NULL,
                          address VARCHAR(255) NOT NULL,
                          education VARCHAR(255) NOT NULL,
                          type ENUM('Journalist', 'Editor') NOT NULL,
                          is_chief_editor BOOLEAN DEFAULT FALSE,
                          UNIQUE KEY unique_email (email)
);