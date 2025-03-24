CREATE TABLE calender(
                         id             BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         title          VARCHAR(100)      NOT NULL,
                         todoist        VARCHAR(200)      NOT NULL,
                         createdAt      DATETIME          NOT NULL DEFAULT now(),
                         updatedAt      DATETIME          NOT NULL DEFAULT now() ON UPDATE now(),
                         writer         VARCHAR(100)      NOT NULL,
                         password       VARCHAR(40)       NOT NULL
);
