-- passwor: admin
INSERT INTO "_USER" (email, firstname, lastname, password, role) VALUES ('admin-test@mail.com', 'Admin', 'Admin', '$2a$10$BUFE1wEYqmffEW2XS86D6O04YWnM7WDDoW/yxfOsJB5CxSSPIbfum', 'ADMIN');
-- passwor: manager
INSERT INTO "_USER" (email, firstname, lastname, password, role) VALUES ('manager-test@mail.com', 'Manager', 'Manager', '$2a$10$Yzwko0OK9DqfRZocI6rc1.ahpomze8V81ppSQ67scpzHq4SS/ol22', 'MANAGER');
-- passwor: user
INSERT INTO "_USER" (email, firstname, lastname, password, role) VALUES ('user-test@mail.com', 'User', 'User', '$2y$10$mm.Ocu3cnrPFQibK2F3dzeXqqZvi004wngI4FEM4IXA9oZ2qynymi', 'USER');
-- user for change password test - passwor: user
INSERT INTO "_USER" (email, firstname, lastname, password, role) VALUES ('user-changepassword-test@mail.com', 'User', 'User', '$2y$10$mm.Ocu3cnrPFQibK2F3dzeXqqZvi004wngI4FEM4IXA9oZ2qynymi', 'USER');