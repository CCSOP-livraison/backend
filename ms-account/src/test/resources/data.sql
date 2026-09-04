-- Privilèges
INSERT INTO privileges (id, name) VALUES (1, 'READ_PRIVILEGE');
INSERT INTO privileges (id, name) VALUES (2, 'WRITE_PRIVILEGE');
INSERT INTO privileges (id, name) VALUES (3, 'DELETE_PRIVILEGE');
INSERT INTO privileges (id, name) VALUES (4, 'MANAGE_RESTAURANT');
INSERT INTO privileges (id, name) VALUES (5, 'DELIVER_ORDER');

-- Rôles
INSERT INTO roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'MODERATION');
INSERT INTO roles (id, name) VALUES (3, 'DELIVER');
INSERT INTO roles (id, name) VALUES (4, 'CUSTOMER');

-- Rôles - Privilèges
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2, 1), (2, 2), (2, 4);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 1), (3, 2), (3, 5);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (4, 1);

-- Utilisateurs (mot de passe: admin123, moderation123, deliver123, customer123)
-- Admin: Jean Dupont
INSERT INTO users (id, lastname, firstname, address, zipcode, locate, email, phone_number, password, enabled, token_expired)
VALUES (1, 'Dupont', 'Jean', '10 Rue de la Paix', '7501', 'Paris', 'jean.dupont@example.com', '+33612345678', '$2a$12$0jGHvoJGgN2ocKpJjJWK6OvMAiuhA1ZT41C/6mcH3W9WyYqZUoXAy', true, false);

-- Moderation: Sophie Martin
INSERT INTO users (id, lastname, firstname, address, zipcode, locate, email, phone_number, password, enabled, token_expired)
VALUES (2, 'Martin', 'Sophie', '25 Avenue des Fleurs', '6902', 'Lyon', 'sophie.martin@example.com', '+33623456789', '$2a$10$7jWU8ITFjHKp0QcVVYQyh.t20JmpkoZmisHeso6HdH/F6nHmrj3SG', true, false);

-- Deliver: Lucas Bernard
INSERT INTO users (id, lastname, firstname, address, zipcode, locate, email, phone_number, password, enabled, token_expired)
VALUES (3, 'Bernard', 'Lucas', '8 Rue des Mimosas', '1301', 'Marseille', 'lucas.bernard@example.com', '+33634567890', '$2a$12$1h1depTWc/hfXto3UkVKH.M7.0bWxoJ1qOCl.u.Xk7eJ5mkaYy7OG', true, false);

-- Customer: Camille Petit
INSERT INTO users (id, lastname, firstname, address, zipcode, locate, email, phone_number, password, enabled, token_expired)
VALUES (4, 'Petit', 'Camille', '12 Boulevard Victor Hugo', '3100', 'Toulouse', 'camille.petit@example.com', '+33645678901', '$2a$12$nf.SHVc0D400hsGOasguV.MMZpO5145yrX2hHyGiEM5FbPappDQMm', true, false);

-- Customer: Thomas Moreau
INSERT INTO users (id, lastname, firstname, address, zipcode, locate, email, phone_number, password, enabled, token_expired)
VALUES (5, 'Moreau', 'Thomas', '45 Rue Nationale', '5900', 'Lille', 'thomas.moreau@example.com', '+33656789012', '$2a$12$Nmk7wM.8sBQMukau4H4au.3qxiAixlnZnnzeFVX98Dodbrul08w3e', true, false);

-- Association Utilisateurs - Rôles
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (3, 3);
INSERT INTO users_roles (user_id, role_id) VALUES (4, 4);
INSERT INTO users_roles (user_id, role_id) VALUES (5, 4);
