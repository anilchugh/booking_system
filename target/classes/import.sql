--
-- Setup rooms
--
-- =================================================================================================

insert into room(id, room_number, price, room_type) VALUES (1, 101, 100.00, 0);
insert into room(id, room_number, price, room_type) VALUES (2, 102, 100.00, 0);
insert into room(id, room_number, price, room_type) VALUES (3, 201, 200.00, 1);
insert into room(id, room_number, price, room_type) VALUES (4, 202, 200.00, 1);
insert into room(id, room_number, price, room_type) VALUES (5, 203, 200.00, 1);

insert into customer(id, forename, surname, address) VALUES (1, 'Scott', 'Tiger', '16/2 Stenhouse Cottages, Edinburgh EH11 3JQ');

insert into booking(id, check_in_date, check_out_date, room_id, customer_id) VALUES (1, '2017-01-01', '2017-01-05', 1, 1);
insert into booking(id, check_in_date, check_out_date, room_id, customer_id) VALUES (2, '2017-01-01', '2017-01-05', 3, 1);