DROP TABLE IF EXISTS "contact_details";

DROP TABLE IF EXISTS "payment";

DROP TABLE IF EXISTS "guest";

DROP TABLE IF EXISTS "booking";

CREATE TABLE "booking"("booking_reference" VARCHAR(10) PRIMARY KEY, "creation_date" TIMESTAMP, "amount" NUMERIC(6, 2), "currency" VARCHAR(3), "hotel_name" VARCHAR(255), "check_in" VARCHAR(10), "check_out" VARCHAR(10), "room_description" VARCHAR(255));

CREATE TABLE "guest"("id" INT PRIMARY KEY AUTO_INCREMENT, "booking_reference" VARCHAR(10), "title" VARCHAR(4), "name" VARCHAR(255), "surname" VARCHAR(255), "age" INT, foreign key ("booking_reference") references "booking"("booking_reference"));

CREATE TABLE "contact_details"("id" INT PRIMARY KEY AUTO_INCREMENT, "booking_reference" VARCHAR(10), "title" VARCHAR(4), "name" VARCHAR(255), "surname" VARCHAR(255), "address" VARCHAR(255), "phone" VARCHAR(15), "email" VARCHAR(50), foreign key ("booking_reference") references "booking"("booking_reference"));

CREATE TABLE "payment"("id" INT PRIMARY KEY AUTO_INCREMENT, "booking_reference" VARCHAR(10), "card_number" VARCHAR(19), "cvv" INT, "expiry_date" VARCHAR(5), "cardholder_name" VARCHAR(255), foreign key ("booking_reference") references "booking"("booking_reference"));