INSERT INTO vets (first_name, last_name)
SELECT 'James', 'Carter'
WHERE NOT EXISTS (SELECT 1 FROM vets WHERE first_name = 'James' AND last_name = 'Carter');
INSERT INTO vets (first_name, last_name)
SELECT 'Helen', 'Leary'
WHERE NOT EXISTS (SELECT 1 FROM vets WHERE first_name = 'Helen' AND last_name = 'Leary');
INSERT INTO vets (first_name, last_name)
SELECT 'Linda', 'Douglas'
WHERE NOT EXISTS (SELECT 1 FROM vets WHERE first_name = 'Linda' AND last_name = 'Douglas');
INSERT INTO vets (first_name, last_name)
SELECT 'Rafael', 'Ortega'
WHERE NOT EXISTS (SELECT 1 FROM vets WHERE first_name = 'Rafael' AND last_name = 'Ortega');
INSERT INTO vets (first_name, last_name)
SELECT 'Henry', 'Stevens'
WHERE NOT EXISTS (SELECT 1 FROM vets WHERE first_name = 'Henry' AND last_name = 'Stevens');
INSERT INTO vets (first_name, last_name)
SELECT 'Sharon', 'Jenkins'
WHERE NOT EXISTS (SELECT 1 FROM vets WHERE first_name = 'Sharon' AND last_name = 'Jenkins');

INSERT INTO specialties (name)
SELECT 'radiology'
WHERE NOT EXISTS (SELECT 1 FROM specialties WHERE name = 'radiology');
INSERT INTO specialties (name)
SELECT 'surgery'
WHERE NOT EXISTS (SELECT 1 FROM specialties WHERE name = 'surgery');
INSERT INTO specialties (name)
SELECT 'dentistry'
WHERE NOT EXISTS (SELECT 1 FROM specialties WHERE name = 'dentistry');

INSERT INTO vet_specialties (vet_id, specialty_id)
VALUES (2, 1)
ON CONFLICT (vet_id, specialty_id) DO NOTHING;
INSERT INTO vet_specialties (vet_id, specialty_id)
VALUES (3, 2)
ON CONFLICT (vet_id, specialty_id) DO NOTHING;
INSERT INTO vet_specialties (vet_id, specialty_id)
VALUES (3, 3)
ON CONFLICT (vet_id, specialty_id) DO NOTHING;
INSERT INTO vet_specialties (vet_id, specialty_id)
VALUES (4, 2)
ON CONFLICT (vet_id, specialty_id) DO NOTHING;
INSERT INTO vet_specialties (vet_id, specialty_id)
VALUES (5, 1)
ON CONFLICT (vet_id, specialty_id) DO NOTHING;

INSERT INTO types (name)
SELECT 'cat'
WHERE NOT EXISTS (SELECT 1 FROM types WHERE name = 'cat');
INSERT INTO types (name)
SELECT 'dog'
WHERE NOT EXISTS (SELECT 1 FROM types WHERE name = 'dog');
INSERT INTO types (name)
SELECT 'lizard'
WHERE NOT EXISTS (SELECT 1 FROM types WHERE name = 'lizard');
INSERT INTO types (name)
SELECT 'snake'
WHERE NOT EXISTS (SELECT 1 FROM types WHERE name = 'snake');
INSERT INTO types (name)
SELECT 'bird'
WHERE NOT EXISTS (SELECT 1 FROM types WHERE name = 'bird');
INSERT INTO types (name)
SELECT 'hamster'
WHERE NOT EXISTS (SELECT 1 FROM types WHERE name = 'hamster');

INSERT INTO owners (first_name, last_name, address, city, telephone, email)
SELECT 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'george.franklin@example.com'
WHERE NOT EXISTS (SELECT 1 FROM owners WHERE first_name = 'George' AND last_name = 'Franklin');
INSERT INTO owners (first_name, last_name, address, city, telephone, email)
SELECT 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'betty.davis@example.com'
WHERE NOT EXISTS (SELECT 1 FROM owners WHERE first_name = 'Betty' AND last_name = 'Davis');
INSERT INTO owners (first_name, last_name, address, city, telephone, email)
SELECT 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'eduardo.rodriquez@example.com'
WHERE NOT EXISTS (SELECT 1 FROM owners WHERE first_name = 'Eduardo' AND last_name = 'Rodriquez');
INSERT INTO owners (first_name, last_name, address, city, telephone, email)
SELECT 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'harold.davis@example.com'
WHERE NOT EXISTS (SELECT 1 FROM owners WHERE first_name = 'Harold' AND last_name = 'Davis');
INSERT INTO owners (first_name, last_name, address, city, telephone, email)
SELECT 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'peter.mctavish@example.com'
WHERE NOT EXISTS (SELECT 1 FROM owners WHERE first_name = 'Peter' AND last_name = 'McTavish');
INSERT INTO owners (first_name, last_name, address, city, telephone, email)
SELECT 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'jean.coleman@example.com'
WHERE NOT EXISTS (SELECT 1 FROM owners WHERE first_name = 'Jean' AND last_name = 'Coleman');
INSERT INTO owners (first_name, last_name, address, city, telephone, email)
SELECT 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'jeff.black@example.com'
WHERE NOT EXISTS (SELECT 1 FROM owners WHERE first_name = 'Jeff' AND last_name = 'Black');
INSERT INTO owners (first_name, last_name, address, city, telephone, email)
SELECT 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'maria.escobito@example.com'
WHERE NOT EXISTS (SELECT 1 FROM owners WHERE first_name = 'Maria' AND last_name = 'Escobito');
INSERT INTO owners (first_name, last_name, address, city, telephone, email)
SELECT 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'david.schroeder@example.com'
WHERE NOT EXISTS (SELECT 1 FROM owners WHERE first_name = 'David' AND last_name = 'Schroeder');
INSERT INTO owners (first_name, last_name, address, city, telephone, email)
SELECT 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'carlos.estaban@example.com'
WHERE NOT EXISTS (SELECT 1 FROM owners WHERE first_name = 'Carlos' AND last_name = 'Estaban');

INSERT INTO pets (name, birth_date, type_id, owner_id)
SELECT 'Leo', '2010-09-07', 1, 1
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE name = 'Leo' AND owner_id = 1);
INSERT INTO pets (name, birth_date, type_id, owner_id)
SELECT 'Basil', '2012-08-06', 6, 2
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE name = 'Basil' AND owner_id = 2);
INSERT INTO pets (name, birth_date, type_id, owner_id)
SELECT 'Rosy', '2011-04-17', 2, 3
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE name = 'Rosy' AND owner_id = 3);
INSERT INTO pets (name, birth_date, type_id, owner_id)
SELECT 'Jewel', '2010-03-07', 2, 3
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE name = 'Jewel' AND owner_id = 3);
INSERT INTO pets (name, birth_date, type_id, owner_id)
SELECT 'Iggy', '2010-11-30', 3, 4
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE name = 'Iggy' AND owner_id = 4);
INSERT INTO pets (name, birth_date, type_id, owner_id)
SELECT 'George', '2010-01-20', 4, 5
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE name = 'George' AND owner_id = 5);
INSERT INTO pets (name, birth_date, type_id, owner_id)
SELECT 'Samantha', '2012-09-04', 1, 6
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE name = 'Samantha' AND owner_id = 6);
INSERT INTO pets (name, birth_date, type_id, owner_id)
SELECT 'Max', '2012-09-04', 1, 6
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE name = 'Max' AND owner_id = 6);
INSERT INTO pets (name, birth_date, type_id, owner_id)
SELECT 'Lucky', '2011-08-06', 5, 7
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE name = 'Lucky' AND owner_id = 7);
INSERT INTO pets (name, birth_date, type_id, owner_id)
SELECT 'Mulligan', '2007-02-24', 2, 8
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE name = 'Mulligan' AND owner_id = 8);
INSERT INTO pets (name, birth_date, type_id, owner_id)
SELECT 'Freddy', '2010-03-09', 5, 9
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE name = 'Freddy' AND owner_id = 9);
INSERT INTO pets (name, birth_date, type_id, owner_id)
SELECT 'Lucky', '2010-06-24', 2, 10
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE name = 'Lucky' AND owner_id = 10);
INSERT INTO pets (name, birth_date, type_id, owner_id)
SELECT 'Sly', '2012-06-08', 1, 10
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE name = 'Sly' AND owner_id = 10);

INSERT INTO visits (pet_id, visit_date, description)
SELECT 7, '2013-01-01', 'rabies shot'
WHERE NOT EXISTS (SELECT 1 FROM visits WHERE pet_id = 7 AND visit_date = '2013-01-01' AND description = 'rabies shot');
INSERT INTO visits (pet_id, visit_date, description)
SELECT 8, '2013-01-02', 'rabies shot'
WHERE NOT EXISTS (SELECT 1 FROM visits WHERE pet_id = 8 AND visit_date = '2013-01-02' AND description = 'rabies shot');
INSERT INTO visits (pet_id, visit_date, description)
SELECT 8, '2013-01-03', 'neutered'
WHERE NOT EXISTS (SELECT 1 FROM visits WHERE pet_id = 8 AND visit_date = '2013-01-03' AND description = 'neutered');
INSERT INTO visits (pet_id, visit_date, description)
SELECT 7, '2013-01-04', 'spayed'
WHERE NOT EXISTS (SELECT 1 FROM visits WHERE pet_id = 7 AND visit_date = '2013-01-04' AND description = 'spayed');

INSERT INTO users (username, password, enabled)
SELECT 'admin', '$2a$10$ymaklWBnpBKlgdMgkjWVF.GMGyvH8aDuTK.glFOaKw712LHtRRymS', TRUE
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO roles (name, description)
SELECT 'ROLE_OWNER_ADMIN', 'Owner management administrator'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_OWNER_ADMIN');
INSERT INTO roles (name, description)
SELECT 'ROLE_VET_ADMIN', 'Veterinary management administrator'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_VET_ADMIN');
INSERT INTO roles (name, description)
SELECT 'ROLE_ADMIN', 'Super administrator'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN');

INSERT INTO user_roles (username, role_id)
SELECT 'admin', id FROM roles
WHERE name = 'ROLE_OWNER_ADMIN'
  AND NOT EXISTS (SELECT 1 FROM user_roles WHERE username = 'admin' AND role_id = roles.id);
INSERT INTO user_roles (username, role_id)
SELECT 'admin', id FROM roles
WHERE name = 'ROLE_VET_ADMIN'
  AND NOT EXISTS (SELECT 1 FROM user_roles WHERE username = 'admin' AND role_id = roles.id);
INSERT INTO user_roles (username, role_id)
SELECT 'admin', id FROM roles
WHERE name = 'ROLE_ADMIN'
  AND NOT EXISTS (SELECT 1 FROM user_roles WHERE username = 'admin' AND role_id = roles.id);
