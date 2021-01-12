USE `accounting_system`;
INSERT INTO `firms` (`name`)
VALUES ('Audi'),
       ('BMW'),
       ('Bugatti'),
       ('Alfa Romeo'),
       ('Aston Martin');

INSERT INTO `contact_persons` (`firm_id`, `telephone`, `name`)
VALUES ('1', '375291883888', 'Addelyn'),
       ('1', '375291873798', 'Kinsley'),
       ('1', '375296651477', 'Jacob'),
       ('2', '375293993818', 'Kinsley'),
       ('2', '375297772144', 'Addelyn'),
       ('3', '375294773825', 'Mia'),
       ('3', '375294753366', 'Ava'),
       ('3', '375294565577', 'Emily'),
       ('4', '375292127752', 'Ava'),
       ('5', '375293246324', 'Jacob'),
       ('5', '375294776566', 'Emily');

INSERT INTO `catalogs` (`name`, `parent_catalog_id`)
VALUES ('Transport', NULL),
       ('Car', '1'),
       ('Aircraft', '1'),
       ('Ship', '1');

INSERT INTO `cars` (`catalog_id`, `firm_id`, `model`, `status`, `price`, `description`)
VALUES ('2', '1', 'e-tron', 'AVAILABLE', '149.99',
        'All-wheel drive premium SUV in the full-size category with an all-electric propulsion system.'),
       ('2', '1', 'A3', 'MISSING', '1745.50',
        'The Audi A3 is a small family car manufactured and marketed since the 1990s by the Audi subdivision of the Volkswagen Group.'),
       ('2', '2', 'X7', 'AVAILABLE', '200.00', NULL),
       ('2', '2', 'X6', 'AVAILABLE', '180.00', 'The BMW X6 is a mid-size luxury crossover by German automaker BMW.'),
       ('2', '3', 'Divo', 'AVAILABLE', '449.99',
        'The Bugatti Divo is a mid-engine track-focused sports car developed and manufactured by Bugatti Automobiles S.A.S.'),
       ('2', '4', 'Giulia', 'MISSING', '399.99',
        'The Alfa Romeo Giulia (Type 952) is a compact executive car produced by the Italian automobile manufacturer Alfa Romeo.'),
       ('2', '5', 'DB11 Coupe', 'AVAILABLE', '230.50', 'Ravishing looks, powerful V8 and V12 engines');

INSERT INTO `profiles` (`name`, `surname`, `sex`, `date_of_birth`, `date_of_registration`)
VALUES ('Jack', 'Allford', '1', '1988-10-10', '2020-11-10'),
       ('Sophia', 'Donaldson', '0', '1978-08-08', '2020-11-11'),
       ('Mike', 'Becker', '1', '1999-11-11', '2020-11-10'),
       ('Evie', 'Bishop', '0', '1979-12-10', '2020-11-11'),
       ('Jacob', 'Carey', '1', '1992-10-08', '2020-11-09');

INSERT INTO `users` (`login`, `password`, `role`, `profile_id`)
VALUES ('Jack123', '$2a$10$ComZylfJURH..YdHrYCnF.ZXL0JvymbEwMa.xZ4m8UddUBazN1T96', 'ADMIN', '1'),
       ('SophiaDonaldson', '$2a$10$ComZylfJURH..YdHrYCnF.ZXL0JvymbEwMa.xZ4m8UddUBazN1T96', 'USER', '2'),
       ('Mike1999', '$2a$10$ComZylfJURH..YdHrYCnF.ZXL0JvymbEwMa.xZ4m8UddUBazN1T96', 'USER', '3'),
       ('Evie79', '$2a$10$ComZylfJURH..YdHrYCnF.ZXL0JvymbEwMa.xZ4m8UddUBazN1T96', 'USER', '4'),
       ('JacobCarey92', '$2a$10$ComZylfJURH..YdHrYCnF.ZXL0JvymbEwMa.xZ4m8UddUBazN1T96', 'USER', '5');

INSERT INTO `requests` (`user_id`, `car_id`)
VALUES ('1', '2'),
       ('2', '2'),
       ('1', '6'),
       ('2', '6'),
       ('4', '6');

INSERT INTO `orders` (`user_id`, `car_id`, `start_date`, `end_date`, `actual_end_date`, `price`)
VALUES ('2', '1', '2020-09-20', '2020-10-1', '2020-10-2', '1200.00'),
       ('3', '2', '2020-09-20', '2020-10-1', '2020-10-2', '1200.00'),
       ('4', '5', '2020-09-20', '2020-10-1', '2020-10-2', '1200.00'),
       ('2', '1', '2020-10-20', '2020-11-1', '2020-10-25', '1000.00'),
       ('2', '1', '2020-10-20', '2020-11-1', '2020-10-25', '1000.00'),
       ('2', '1', '2020-11-11', '2020-12-23', '2020-12-22', '1000.00'),
       ('3', '2', '2020-11-12', '2020-12-2', '2020-12-1', '1200.00'),
       ('4', '5', '2020-11-17', '2020-12-2', '2020-12-1', '1400.00'),
       ('5', '7', '2020-11-18', '2020-11-28', '2020-11-27', '1300.00'),
       ('3', '2', '2020-12-2', '2020-12-10', null, '1300.00'),
       ('5', '6', '2020-12-2', '2020-12-20', null, '1300.00');

INSERT INTO `settings` (`name`, `access`)
VALUES ('requestFunctionEnabled', '1'),
       ('orderFunctionEnabled', '1'),
       ('userFunctionEnabled', '1');
COMMIT;







